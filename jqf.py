import os
import subprocess
import re
import javalang
import pandas as pd
import random
import string
import numpy as np




class JQF:
    def __init__(self):
        self.maindir=os.getcwd()
    def prepare_JQf(self,function_code):
        class_name,method,params=self.make_AST(function_code)
        self.make_JQF_file(class_name,function_code)
        self.generate_test_file(class_name,params,method)
        return class_name


    def driverjqf(self,function_code):
        file_name= self.prepare_JQf(function_code)
        jqf_file_name = f"{file_name}Test"
        self.run_jqf(jqf_file_name)
        valid_inputs,failure_inputs=self.compile_results(file_name)
        return valid_inputs,failure_inputs


    def provide_main(self, file_content):
        temp = file_content[:-1]
        class_name, method, params = self.make_AST(file_content)
        main_content = "public static void main(String[] args) {\n"
        for i in range(len(params)):
            param_type = params[i][1]
            if "[]" in params[i][1]:
                array_dim = params[i][1].count("[]")    
                size = (array_dim,) * array_dim
                
                if "int" in params[i][1]:
                    array= np.random.randint(1, 100, size=size)
                    tempo=f"{params[i][1]} {params[i][0]} = "+ "{"
                    for i in range(array_dim):
                        tempo+="{"
                        for j, val in enumerate(array[i]):
                            tempo+=str(val)
                            if j!=len(array)-1:
                                tempo+=","
                        tempo+="}"
                        if i!=len(array)-1:
                            tempo+=","
                    tempo+="};"
                    main_content+=tempo
                    
                elif "boolean" in params[i][1]:
                    array= np.random.choice([True, False], size=size)
                    tempo=f"{params[i][1]} {params[i][0]} = "+ "{"
                    for i in range(array_dim):
                        tempo+="{"
                        for j, val in enumerate(array[i]):
                            tempo+=str(val)
                            if j!=len(array)-1:
                                tempo+=","
                        tempo+="}"
                        if i!=len(array)-1:
                            tempo+=","
                    tempo+="};"
                    main_content+=tempo
                elif "double" in params[i][1]:
                    array= np.random.uniform(1.0, 100.0, size=size)
                    tempo=f"{params[i][1]} {params[i][0]} = "+ "{"
                    for i in range(array_dim):
                        tempo+="{"
                        for j, val in enumerate(array[i]):
                            tempo+=str(val)
                            if j!=len(array)-1:
                                tempo+=","
                        tempo+="}"
                        if i!=len(array)-1:
                            tempo+=","
                    tempo+="};"
                    main_content+=tempo
                elif "float" in params[i][1]:
                    array= np.random.uniform(1.0, 100.0, size=size)
                    tempo=f"{params[i][1]} {params[i][0]} = "+ "{"
                    for i in range(array_dim):
                        tempo+="{"
                        for j, val in enumerate(array[i]):
                            tempo+=str(val)
                            if j!=len(array)-1:
                                tempo+=","
                        tempo+="}"
                        if i!=len(array)-1:
                            tempo+=","
                    tempo+="};"
                    main_content+=tempo
                elif "long" in params[i][1]:
                    array= np.random.randint(1, 1000000, size=size)
                    tempo=f"{params[i][1]} {params[i][0]} = "+ "{"
                    for i in range(array_dim):
                        tempo+="{"
                        for j, val in enumerate(array[i]):
                            tempo+=str(val)
                            if j!=len(array)-1:
                                tempo+=","
                        tempo+="}"
                        if i!=len(array)-1:
                            tempo+=","
                    tempo+="};"
                    main_content+=tempo
                elif "char" in params[i][1]:
                    array= np.random.choice(list(string.ascii_letters),size=size)
                    tempo=f"{params[i][1]} {params[i][0]} = "+ "{"
                    for i in range(array_dim):
                        tempo+="{"
                        for j, val in enumerate(array[i]):
                            tempo+=str(val)
                            if j!=len(array)-1:
                                tempo+=","
                        tempo+="}"
                        if i!=len(array)-1:
                            tempo+=","
                    tempo+="};"
                    main_content+=tempo
        main_content+=f"\n\n{method}("
        for i in range(len(params)):
            param_type = params[i][1]   
            if param_type == "int":
                    number = np.random.randint(1, 100)
                    main_content += str(number)
            elif param_type == "String":
                    random_string = ''.join(random.choices(string.ascii_letters + string.digits, k=10))
                    main_content += "\"" + random_string + "\""
            elif param_type == "boolean":
                    main_content += "true" if random.choice([True, False]) else "false"
            elif param_type == "double":
                    number = np.random.uniform(1.0, 100.0)
                    main_content += str(number)
            elif param_type == "float":
                    number = np.random.uniform(1.0, 100.0)
                    main_content += str(number) + "f"
            elif param_type == "long":
                    number = np.random.randint(1, 1000000)
                    main_content += str(number) + "L"
            elif param_type == "char":
                    random_char = random.choice(string.ascii_letters)
                    main_content += "'" + random_char + "'"
            elif "[]" in param_type:
                main_content+=f"{params[i][0]}"
                                    
            if i != len(params) - 1:
                    main_content += ", "
        main_content += ");\n"
        main_content += "}\n}"
        main_content=temp+main_content
        return main_content


    def make_JQF_file(self, file_name, file_content):
        # Define the project directory
        project_dir = r"JQF\examples"
        file_content=self.provide_main(file_content)
        # Define the file path
        file_path = os.path.join(project_dir,f"src/main/java/edu/berkeley/cs/jqf/examples/{file_name}.java")
        file_content=f"package edu.berkeley.cs.jqf.examples;\n\n{file_content}"
        # Write the file content
        with open(file_path, 'w') as file:
            file.write(file_content)
    def run_jqf_with_compilig(self,filename):
        command = (f"mvn clean install -DskipTests && mvn package &&" 
            f"mvn jqf:fuzz \"-Dclass=edu.berkeley.cs.jqf.examples.{filename}\" \"-Dmethod=fuzz\"  \"-Dtime=1m\"")

#         docker_command = (
#     "docker run -v %cd%:/app -it maven:3.6.1-jdk-12 /bin/bash -c "
#     f"\"cd /app && mvn package && java -jar JQF/fuzz/target/jqf-fuzz-2.1-SNAPSHOT-zest-cli.jar --duration=60s "
#     f"-e target/example-java-1.0-SNAPSHOT-fat-tests.jar dev.fuzzit.examplejava.{filename} fuzz\""
# )

# java -jar JQF/fuzz/target/jqf-fuzz-2.1-SNAPSHOT-zest-cli.jar --duration=60s -e target/example-java-1.0-SNAPSHOT-fat-tests.jar dev.fuzzit.examplejava.hoursToMinutesTest fuzz
        # Define the project directory
        project_dir = r"JQF\examples"

        try:
            # Change to the project directory
            os.chdir(project_dir)

            # Run the Docker command and capture output
            result = subprocess.run(
                command,
                shell=True,
                check=True,
                stdout=subprocess.PIPE,
                stderr=subprocess.PIPE
            )

            # Print output from the Docker command
            print("Command output:")
            print(result.stdout.decode())

        except FileNotFoundError:
            print(f"Error: The directory {project_dir} does not exist.")
        except subprocess.CalledProcessError as e:
            print("Error running Docker command:")
            print("Stdout:", e.stdout.decode() if e.stdout else "No output")
            print("Stderr:", e.stderr.decode() if e.stderr else "No errors")


    def run_jqf(self,filename):
        # Define the Docker command
        # docker_command = (
        #     "docker run -v %cd%:/app -it maven:3.6.1-jdk-12 /bin/bash -c "
        #     f"\"cd /app && java  -jar JQF/fuzz/target/jqf-fuzz-2.1-SNAPSHOT-zest-cli.jar --duration=60s "
        #     f"-e target/example-java-1.0-SNAPSHOT-fat-tests.jar dev.fuzzit.examplejava.{filename} fuzz\""
        # )
        command = (
        f"mvn jqf:fuzz \"-Dclass=edu.berkeley.cs.jqf.examples.{filename}\" \"-Dmethod=fuzz\"  \"-Dtime=1m\""
        )


# java -jar JQF/fuzz/target/jqf-fuzz-2.1-SNAPSHOT-zest-cli.jar --duration=60s -e target/example-java-1.0-SNAPSHOT-fat-tests.jar dev.fuzzit.examplejava.hoursToMinutesTest fuzz
        # Define the project directory
        project_dir = r"JQF\examples"

        try:
            # Change to the project directory
            os.chdir(project_dir)

            # Run the Docker command and capture output
            result = subprocess.run(
                command,
                shell=True,
                check=True,
                stdout=subprocess.PIPE,
                stderr=subprocess.PIPE
            )

            # Print output from the Docker command
            print("Command output:")
            print(result.stdout.decode())

        except FileNotFoundError:
            print(f"Error: The directory {project_dir} does not exist.")
        except subprocess.CalledProcessError as e:
            print("Error running Docker command:")
            print("Stdout:", e.stdout.decode() if e.stdout else "No output")
            print("Stderr:", e.stderr.decode() if e.stderr else "No errors")

    


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




    def generate_test_file(self,classname,params,method):
        libraries="""package edu.berkeley.cs.jqf.examples;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
"""
        test_class=f"public class {classname}Test"+"{ \n\n@Fuzz\npublic void "+"fuzz"+"("
        for i in range(len(params)):
            test_class+=params[i][1]+" "+params[i][0]
            if i!=len(params)-1:
                test_class+=","
        test_class+="){\n"
        test_class+=f"      {classname}.{method}("
        for i in range(len(params)):
            test_class+=params[i][0]
            if i!=len(params)-1:
                test_class+=","
        test_class+=");\n}\n}"

        

        file_content=libraries+test_class
        write_file_name = os.path.join(os.getcwd(), f'JQF/examples/src/test/java/edu/berkeley/cs/jqf/examples/{classname}Test.java')

        with open(write_file_name, 'w') as file:
            file.write(file_content)
    def compile_results_content(self,filename,folder):
        
        parent_folder = f"JQF/examples/target/fuzz-results/edu.berkeley.cs.jqf.examples.{filename}Test/fuzz/{folder}"
        
        os.chdir(self.maindir)
        
        file_contents = [] 
        for entry in os.listdir(parent_folder):
            file_path = os.path.join(parent_folder, entry)
            if os.path.isfile(file_path):
                with open(file_path, 'r', encoding='utf-8') as f:
                    content = f.read()
                    file_contents.append(content)
        return file_contents
    def compile_results(self,filename):
        valid_inputs=self.compile_results_content(filename,"corpus")
        failure_inputs=self.compile_results_content(filename,"failures")
        return valid_inputs,failure_inputs
