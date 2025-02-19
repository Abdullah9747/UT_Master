import os
import subprocess
import re
import javalang
import pandas as pd
import random
import string
import numpy as np



class SPF:
    
    def driverspf(self,file_content):

        #time_limit = 5
        class_name,method,params=self.make_AST(file_content)
        file_content=self.make_file(class_name,file_content,params)
        self.build_classfile(class_name, file_content)
        self.compiling_spf()
        self.jpf_file_content = self.generate_jpf_file(class_name, params)
        print("JPF file generated successfully")
        result=self.generate_spf_test(class_name)
        print(f"Spf Results {result}")
    

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

        command = 'gradle :jpf-symbc:compile'
        result = subprocess.run(command, shell=True, capture_output=True, text=True)
        os.chdir(path)

    def generate_spf_test(self, file_name):
        # Print the current working directory
        newpath = os.path.join(os.getcwd(), r'SPF\jpf-symbc')
        os.chdir(newpath)
        print('current dir is: ', os.getcwd())
        command = f'java -Xmx1024m -ea -jar ../jpf-core/build/RunJPF.jar  ./src/examples/demo/{file_name}.jpf'
        result = subprocess.run(command, shell=True, capture_output=True, text=True)
        
        print(result.stdout)
        print(result.stderr)
        return self.extract_specific_part(result.stdout + result.stderr)

    def extract_specific_part(self, output):
        pattern = r"Method Summaries(.*?)====="
        match = re.search(pattern, output, re.DOTALL)
        if match:
            print(match.group(1).strip())
            return match.group(1).strip()

    def generate_jpf_file(self, file_name, params):
        params= [f'sym' for param in params]

        params_str = '#'.join(params)
        file_name = file_name[0].upper() + file_name[1:]
        file_content = f'''target=demo.{file_name}
classpath=${{jpf-symbc}}/build/examples
sourcepath=${{jpf-symbc}}/src/examples
symbolic.method = demo.{file_name}.{file_name}({params_str})

#symbolic.dp=z3
listener = .symbc.SymbolicListener

search.multiple_errors=true

'''
        write_file_name = os.path.join(os.getcwd(), f'SPF\\jpf-symbc\\src\\examples\\demo\\{file_name}.jpf')
        
        with open(write_file_name, 'w') as file:
            file.write(file_content)
        return file_content
    
    def make_file(self,file_name,file_content,params):
        main_debug=""
        passing_values=[]
        file_content=file_content[:-1]
        
        file_name = file_name[0].lower() + file_name[1:]
        new_file="package demo; import gov.nasa.jpf.symbc.Debug;"+file_content
        for i,param in enumerate(params):
            
            if param[1]=="int":
                main_debug+=f"int x{i} = Debug.makeSymbolicInteger(\"x{i}\");\n"
                passing_values.append(f"x{i}")
            elif param[1]=="double":
                main_debug+=f"double x{i} = Debug.makeSymbolicReal(\"x{i}\");\n"
                passing_values.append(f"x{i}")
            elif param[1]=="boolean":
                main_debug+=f"boolean x{i} = Debug.makeSymbolicBoolean(\"x{i}\");\n"
                passing_values.append(f"x{i}")
        main_content="public static void main(String[] args)"+ '{\n'
        main_content+=main_debug+f"{file_name}({','.join(passing_values)});"+'\n}'
        new_file+=main_content+"}"
        print(new_file)
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



