import os
import subprocess
import re
import javalang
import pandas as pd
import random
import string
import numpy as np
import time


class SPF:
    
    def compile_check(self,file_content):
        class_name,method,params=self.make_AST(file_content)
        print(class_name)
        file_content=self.make_file(class_name,file_content,params)
        self.build_classfile(class_name, file_content)
        value=self.compiling_spf()
        if value==True:
            print("Compilation successful")
        else:
            print("Compilation failed")
        self.delete_files(class_name)
        base_dir = r'SPF\jpf-symbc\src\examples\demo'
        os.makedirs(base_dir, exist_ok=True)
        return value

    def driverspf(self,file_content):
        
        class_name,method,params=self.make_AST(file_content)
        print(class_name)
        file_content=self.make_file(class_name,file_content,params)
        self.build_classfile(class_name, file_content)
        self.compiling_spf()
        self.jpf_file_content = self.generate_jpf_file(class_name, params)
        #print("JPF file generated successfully")
        result,total_time,er1,er2=self.generate_spf_test(class_name)
        
        print(f"Spf Results {result}")
        print(f"Total time taken for SPF: {total_time} seconds")
        self.delete_files(class_name)
        base_dir = r'SPF\jpf-symbc\src\examples\demo'
        os.makedirs(base_dir, exist_ok=True)
        return result,total_time,er1,er2
     
    def delete_files(self, file_name):
        # Delete the file from the demo folder
        base_dir = r'SPF\jpf-symbc\src\examples\demo'
        file_name = file_name[0].upper() + file_name[1:]
        # Ensure file has .java extension
        if not file_name.endswith('.java'):
            file_name = file_name + '.java'
        # Create full path
        path = os.path.join(base_dir, file_name)
        os.remove(path)
        print(f"Deleted {file_name}")
        #delete jpf file
        jpf_file = file_name[:-5] + '.jpf'
        path = os.path.join(base_dir, jpf_file)
        if os.path.exists(path):
            os.remove(path)
            print(f"Deleted {jpf_file}")
        else:
            print(f"JPF file {jpf_file} not found, skipping deletion")
    

    def build_classfile(self, file_name, file_content):
        # save the file content to a file in the demo folder
        base_dir = r'SPF\jpf-symbc\src\examples\demo'
        
        # Create directory if it doesn't exist
        os.makedirs(base_dir, exist_ok=True)
        file_name = file_name[0].upper() + file_name[1:]
        # Ensure file has .java extension
        if not file_name.endswith('.java'):
            file_name = file_name + '.java'
            
        # Create full path
        path = os.path.join(base_dir, file_name)
        
        
        # Write content to file
        with open(path, 'w') as f:
            f.write(file_content)
            
        return path
    def compiling_spf(self):
        # Print the current working directory
        path=os.getcwd()
        newpath = os.path.join(os.getcwd(), 'SPF')
        os.chdir(newpath)
        #print("compiling")
        command = 'gradle :jpf-symbc:compile'
        result = subprocess.run(command, shell=True, capture_output=True, text=True)
        #print(result.stdout)
        #print(result.stderr)
        os.chdir(path)
        #if result.stderr or stout has build successful, then return true
        if "BUILD SUCCESSFUL" in result.stdout or "BUILD SUCCESSFUL" in result.stderr:
            print("Build successful")
            return True
        else:
            print("Build failed")
            return False

    def generate_spf_test(self, file_name):
        # Save the original working directory
        orig_dir = os.getcwd()
        
        # Build an absolute path to the jpf-symbc directory
        newpath = os.path.join(orig_dir, r'SPF\jpf-symbc')
        os.chdir(newpath)
        #print('current dir is: ', os.getcwd())
        
        # Build an absolute path to the JPF jar file
        jar_path = os.path.join(orig_dir, "SPF", "jpf-core", "build", "RunJPF.jar")
        
        command = f'java -Xmx1024m -ea -jar "{jar_path}"  ./src/examples/demo/{file_name}.jpf'
        starttime=time.time()
        print(starttime)
        result = subprocess.run(command, shell=True, capture_output=True, text=True)
        endtime=time.time()
        total_time = endtime - starttime
        print(f"Execution time: {total_time} seconds")
        #print(result.stdout)
        #print(result.stderr)
        
        outcome = self.extract_specific_part(result.stdout + result.stderr)
        
        # Restore the original directory after executing the command
        os.chdir(orig_dir)
        return outcome, total_time,result.stderr,result.stdout

    def extract_specific_part(self, output):
        lines = output.splitlines()
        block_lines = []
        processing = False

        start_marker = "====================================================== Method Summaries"

        for line in lines:
            line_stripped = line.strip()
            # Start collecting when we find the start marker
            if start_marker in line_stripped:
                processing = True
                continue

            # Stop collecting if we hit either a new section marker (that isn't Method Summaries)
            # or an HTML tag line.
            if processing and (line_stripped.startswith("======================================================") and "Method Summaries" not in line_stripped or line_stripped.startswith("<")):
                break

            # If processing, process and add the line
            if processing:
                # Check for "No path conditions" case
                if "No path conditions for" in line_stripped:
                    return "No path condition"
                
                # Extract simplified format from method calls
                # Pattern: demo.ClassName.methodName(params) --> Return Value: X
                if "-->" in line_stripped and "Return Value:" in line_stripped:
                    # Extract everything from the opening parenthesis to the end
                    if "(" in line_stripped:
                        # Find the position of the opening parenthesis
                        paren_start = line_stripped.find("(")
                        # Extract from the parenthesis onwards
                        simplified_line = line_stripped[paren_start:]
                        block_lines.append(simplified_line)
                else:
                    # For other lines, add as is
                    block_lines.append(line_stripped)
                
        return "\n".join(block_lines)

    def generate_jpf_file(self, file_name, params):
        params= [f'sym' for param in params]

        params_str = '#'.join(params)
        file_name = file_name[0].upper() + file_name[1:]
        file_content = f'''target=demo.{file_name}
classpath=${{jpf-symbc}}/build/examples
sourcepath=${{jpf-symbc}}/src/examples
symbolic.method = demo.{file_name}.{file_name}({params_str})
search.class = .search.heuristic.BFSHeuristic
symbolic.dp=z3bitvector
listener = .symbc.SymbolicListener
search.depth_limit=5
search.multiple_errors=true

'''
        write_file_name = os.path.join(os.getcwd(), f'SPF\\jpf-symbc\\src\\examples\\demo\\{file_name}.jpf')
        
        with open(write_file_name, 'w') as file:
            file.write(file_content)
        return file_content
    
    def make_file(self, file_name, file_content, params):
        main_debug = ""
        passing_values = []
    
        # Remove any trailing whitespace/newlines
        file_content = file_content.rstrip()
        # Remove a single trailing "}" (with any trailing whitespace)
        file_content = re.sub(r'\}\s*$', '', file_content)
        # Insert "static" after "public" only for methods (not class declarations)
        file_content = re.sub(r'public\s+(?!static\b)(?!class\b)', 'public static ', file_content)
        file_name = file_name[0].lower() + file_name[1:]
        new_file = "package demo; import gov.nasa.jpf.symbc.Debug;" + file_content
        for i, param in enumerate(params):
            if param[1] == "int":
                main_debug += f"int x{i} = Debug.makeSymbolicInteger(\"x{i}\");\n"
                passing_values.append(f"x{i}")
            elif param[1] == "long":
                main_debug += f"long x{i} = Debug.makeSymbolicLong(\"x{i}\");\n"
                passing_values.append(f"x{i}")
            elif param[1] == "short":
                main_debug += f"short x{i} = Debug.makeSymbolicShort(\"x{i}\");\n"
                passing_values.append(f"x{i}")
            elif param[1] == "double":
                main_debug += f"double x{i} = Debug.makeSymbolicReal(\"x{i}\");\n"
                passing_values.append(f"x{i}")
            elif param[1] == "float":
                main_debug += f"float x{i} = (float)Debug.makeSymbolicReal(\"x{i}\");\n"
                passing_values.append(f"x{i}")
            elif param[1] == "boolean":
                main_debug += f"boolean x{i} = Debug.makeSymbolicBoolean(\"x{i}\");\n"
                passing_values.append(f"x{i}")
            elif param[1]=="char":
                main_debug += f"char x{i} = Debug.makeSymbolicChar(\"x{i}\");\n"
                passing_values.append(f"x{i}")
        main_content = "public static void main(String[] args)" + "{\n"
        main_content += main_debug + f"{file_name}({','.join(passing_values)});" + '\n}'
        new_file += main_content + "}"
        return new_file

    def make_AST(self, function_code):
        class_name = ""
        first_method_name = ""
        first_method_parameters = []

        try:
            tokens = list(javalang.tokenizer.tokenize(function_code))
            parser = javalang.parser.Parser(tokens)
            tree = parser.parse()

            # Traverse the parsed tree
            for _, class_node in tree.filter(javalang.tree.ClassDeclaration):
                class_name = class_node.name

                if class_node.methods:  # Check if the class has methods
                    first_method = next(iter(class_node.methods))  # Get the first method
                    first_method_name = first_method.name
                    first_method_parameters = [
                        (param.name, self.get_full_type(param.type)) for param in first_method.parameters
                    ]
                    break  # Exit after finding the first method
                                
            return class_name, first_method_name, first_method_parameters

        except javalang.parser.JavaSyntaxError as e:
            print(f"Syntax error: {e.description} position {e.at}")
        except javalang.tokenizer.LexerError as e:
            print(f"Tokenization error: {str(e)}")
        except Exception as e:
            print(f"Unexpected error: {str(e)}")
    
    def get_full_type(self, type_node):
            base_type = type_node.name
            dimensions = '[]' * len(type_node.dimensions)
            return base_type + dimensions



