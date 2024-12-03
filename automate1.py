import os
import subprocess
import re
import javalang
import pandas as pd
import random
import string
import numpy as np
class SPF:
    def build_classfile(self, file_name, file_content):
        # save the file content to a file in the demo folder
        base_dir = r'SPF\jpf-symbc\src\examples\demo'
        
        # Create directory if it doesn't exist
        os.makedirs(base_dir, exist_ok=True)
        
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
        self.extract_specific_part(result.stdout + result.stderr)

    def extract_specific_part(self, output):
        pattern = r"Method Summaries(.*?)====="
        match = re.search(pattern, output, re.DOTALL)
        if match:
            print(match.group(1).strip())

    def generate_jpf_file(self, file_name, params, time_limit):
        params_str = '#'.join(params)
        
        file_content = f'''target=demo.{file_name}
classpath=${{jpf-symbc}}/build/examples
sourcepath=${{jpf-symbc}}/src/examples
symbolic.method = demo.{file_name}.{file_name}({params_str})

#symbolic.dp=z3
listener = .symbc.SymbolicListener

search.multiple_errors=true
jpf.time_limit={time_limit}
'''
        write_file_name = os.path.join(os.getcwd(), f'SPF\\jpf-symbc\\src\\examples\\demo\\{file_name}.jpf')
        
        with open(write_file_name, 'w') as file:
            file.write(file_content)
        return file_content
    
    def make_file(self,file_name,file_content,params):
        main_debug=""
        passing_values=[]
        new_file="package demo; import gov.nasa.jpf.symbc.Debug;"+file_content
        for i in range(len(params)):
            if params[i]=="sym":
                main_debug+=f"int x{i} = Debug.makeSymbolicInteger(\"x{i}\");\n"
                passing_values.append(f"x{i}")

        main_content="public static void main(String[] args)"+ '{\n'
        main_content+=main_debug+f"{file_name}({','.join(passing_values)});"+'\n}'
        new_file+=main_content
        print(new_file)





# def windows_path_to_wsl(path):
#     """
#     Convert a Windows path to a WSL-compatible path.
#     """
#     drive, rest = os.path.splitdrive(path)
#     print(f"drive is {drive}")
#     print(f"rest is {rest}")
#     drive_letter = drive[:-1].lower()  # Extract drive letter and make lowercase
#     wsl_path = f"/mnt/{drive_letter}{rest.replace('\\', '/')}"
#     return wsl_path

# def run_WSL(command):
#     """
#     Run a WSL command with a sanitized environment and return stdout and stderr.
#     """
#     try:
#         # Create a clean environment
#         clean_env = os.environ.copy()
#         clean_env.pop("PATH", None)  # Remove conflicting PATH variable
#         result = subprocess.run(
#             ['wsl', '/bin/bash', '-c', command],
#             capture_output=True,
#             text=True,
#             env=clean_env
#         )
#         return result.stdout.strip(), result.stderr.strip()
#     except Exception as e:
#         return "", str(e)


# def run_jqf():

#     command="""
#     cd JQF-wsl
#     cd java-fuzzing-example
#     docker run -v pwd:/app -it maven:3.6.1-jdk-12 /bin/bash
#     cd /app
#     java -jar JQF/fuzz/target/jqf-fuzz-2.1-SNAPSHOT-zest-cli.jar -e target/example-java-1.0-SNAPSHOT-fat-tests.jar dev.fuzzit.examplejava.ParseComplexTest fuzz
#     """
#     stdout, stderr = run_WSL(command)
#     print("STDOUT:")
#     print(stdout)
#     print("\nSTDERR:")
#     print(stderr)

class JQF:
    def provide_main(self, file_content):
        temp = file_content[:-1]
        class_name, method, params = self.make_AST(file_content)
        main_content = "public static void main(String[] args) {\n"
        for i in range(len(params)):
            print(params[i])
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
        project_dir = r"JQF-wsl\java-fuzzing-example"
        file_content=self.provide_main(file_content)
        # Define the file path
        file_path = os.path.join(project_dir, f"src/main/java/dev/fuzzit/examplejava/{file_name}.java")
        file_content=f"package dev.fuzzit.examplejava;\n\n{file_content}"
        # Write the file content
        with open(file_path, 'w') as file:
            file.write(file_content)
    def run_jqf_with_compilig(self,filename):
        docker_command = (
            "docker run -v %cd%:/app -it maven:3.6.1-jdk-12 /bin/bash -c "
            f"\"cd /app && mvn package && java -jar JQF/fuzz/target/jqf-fuzz-2.1-SNAPSHOT-zest-cli.jar --duration=60s "
            f"-e target/example-java-1.0-SNAPSHOT-fat-tests.jar dev.fuzzit.examplejava.{filename} fuzz\""
        )

# java -jar JQF/fuzz/target/jqf-fuzz-2.1-SNAPSHOT-zest-cli.jar --duration=60s -e target/example-java-1.0-SNAPSHOT-fat-tests.jar dev.fuzzit.examplejava.hoursToMinutesTest fuzz
        # Define the project directory
        project_dir = r"JQF-wsl\java-fuzzing-example"

        try:
            # Change to the project directory
            os.chdir(project_dir)

            # Run the Docker command and capture output
            result = subprocess.run(
                docker_command,
                shell=True,
                check=True,
                stdout=subprocess.PIPE,
                stderr=subprocess.PIPE
            )

            # Print output from the Docker command
            print("Docker command output:")
            print(result.stdout.decode())

        except FileNotFoundError:
            print(f"Error: The directory {project_dir} does not exist.")
        except subprocess.CalledProcessError as e:
            print("Error running Docker command:")
            print("Stdout:", e.stdout.decode() if e.stdout else "No output")
            print("Stderr:", e.stderr.decode() if e.stderr else "No errors")


    def run_jqf(self,filename):
        # Define the Docker command
        docker_command = (
            "docker run -v %cd%:/app -it maven:3.6.1-jdk-12 /bin/bash -c "
            f"\"cd /app && java -jar JQF/fuzz/target/jqf-fuzz-2.1-SNAPSHOT-zest-cli.jar --duration=60s "
            f"-e target/example-java-1.0-SNAPSHOT-fat-tests.jar dev.fuzzit.examplejava.{filename} fuzz\""
        )

# java -jar JQF/fuzz/target/jqf-fuzz-2.1-SNAPSHOT-zest-cli.jar --duration=60s -e target/example-java-1.0-SNAPSHOT-fat-tests.jar dev.fuzzit.examplejava.hoursToMinutesTest fuzz
        # Define the project directory
        project_dir = r"JQF-wsl\java-fuzzing-example"

        try:
            # Change to the project directory
            os.chdir(project_dir)

            # Run the Docker command and capture output
            result = subprocess.run(
                docker_command,
                shell=True,
                check=True,
                stdout=subprocess.PIPE,
                stderr=subprocess.PIPE
            )

            # Print output from the Docker command
            print("Docker command output:")
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
        libraries="""package dev.fuzzit.examplejava;
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
        write_file_name = os.path.join(os.getcwd(), f'JQF-wsl/java-fuzzing-example/src/test/java/dev/fuzzit/examplejava/{classname}Test.java')

        with open(write_file_name, 'w') as file:
            file.write(file_content)
        
        


# Fetch current working directory dynamically
# current_dir = os.getcwd()

# # Convert the current directory to WSL-compatible format
# # wsl_path = windows_path_to_wsl(current_dir)
# # print(f"Current directory in WSL format: {wsl_path}")

# Debug: Check if the WSL directory exists
# command = f"cd JQF-wsl && ls"
# stdout, stderr = run_WSL(command)


# JQF_object.run_jqf("AddNumbersTest")
# functions="""package dev.fuzzit.examplejava;

# public class AddNumbers {
#     public static int add(int a, int b) {
#         return a + b;
#     }
# }
# """



class Diver:
    def __init__(self):
        self.jqf=JQF()
        self.spf=SPF()

    def prepare_JQf(self,function_code):
        class_name,method,params=self.jqf.make_AST(function_code)
        print("params from driver",params)
        self.jqf.make_JQF_file(class_name,function_code)
        self.jqf.generate_test_file(class_name,params,method)
    def driver(self,file_name):

        jqf_file_name = f"{file_name}Test"
        self.jqf.run_jqf(jqf_file_name)


    def calling_gen(self,file_name,file_content,params):

        time_limit = 5
        self.spf.build_classfile(file_name, file_content)
        self.spf.compiling_spf()
        self.jpf_file_content = self.spf.generate_jpf_file(file_name, params, time_limit)
        print("JPF file generated successfully")
        self.spf.generate_spf_test(file_name)

# Run the generation
file_name = "testing"
file_content = """
public class testing {
    public static int testing(int sym) {
        if (sym > 0) {
            return sym * sym;
        } else {
            return 0;
        }
    }

 
}
"""
params = ["sym","sym"]
spf=SPF()
spf.make_file(file_name,file_content,params)
# calling_gen(file_name,file_content,params)


driver_Object=Diver()
function_code="""
public class calculateGridPaths {
    public static void calculateGridPaths(int rows, int cols, int[][] obstacles) {
        if (rows <= 0 || cols <= 0) {
            System.out.println("Grid dimensions must be positive.");
            return;
        }

        int[][] dp = new int[rows][cols];

        // Initialize the starting position
        dp[0][0] = obstacles[0][0] == 1 ? 0 : 1;

        // Fill the first row
        for (int col = 1; col < cols; col++) {
            dp[0][col] = (obstacles[0][col] == 1 || dp[0][col - 1] == 0) ? 0 : 1;
        }

        // Fill the first column
        for (int row = 1; row < rows; row++) {
            dp[row][0] = (obstacles[row][0] == 1 || dp[row - 1][0] == 0) ? 0 : 1;
        }

        // Fill the rest of the grid
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                if (obstacles[row][col] == 1) {
                    dp[row][col] = 0; // Obstacle blocks the path
                } else {
                    dp[row][col] = dp[row - 1][col] + dp[row][col - 1];
                }
            }
        }

        System.out.println(dp[rows - 1][cols - 1]);
    }

    public static void main(String[] args) {
        int rows = 3;
        int cols = 3;
        int[][] obstacles = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
        };

        calculateGridPaths(rows, cols, obstacles); // Example: Outputs 2
    }

    
}"""


functioing="""package dev.fuzzit.examplejava;


public class calculateGridPaths {
    public static void calculateGridPaths(int rows, int cols, int[][] obstacles) {
        if (rows <= 0 || cols <= 0) {
            System.out.println("Grid dimensions must be positive.");
            return;
        }

        int[][] dp = new int[rows][cols];

        // Initialize the starting position
        dp[0][0] = obstacles[0][0] == 1 ? 0 : 1;

        // Fill the first row
        for (int col = 1; col < cols; col++) {
            dp[0][col] = (obstacles[0][col] == 1 || dp[0][col - 1] == 0) ? 0 : 1;
        }

        // Fill the first column
        for (int row = 1; row < rows; row++) {
            dp[row][0] = (obstacles[row][0] == 1 || dp[row - 1][0] == 0) ? 0 : 1;
        }

        // Fill the rest of the grid
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                if (obstacles[row][col] == 1) {
                    dp[row][col] = 0; // Obstacle blocks the path
                } else {
                    dp[row][col] = dp[row - 1][col] + dp[row][col - 1];
                }
            }
        }

        System.out.println(dp[rows - 1][cols - 1]);
    }


    
}"""


JQF_object=JQF()
JQF_object.make_JQF_file("calculateGridPaths",functioing)
#driver_Object.prepare_JQf(function_code)

#JQF_object.run_jqf("calculateGridPathsTest")