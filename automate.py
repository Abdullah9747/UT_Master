import os
import subprocess
import re
import javalang

class SPF:
    def build_classfile(self, file_name):
        base_dir = r'SPF\jpf-symbc\src\examples\demo'
        path = os.path.join(base_dir, file_name)
        print(path)
        return path

    def compiling_spf(self):
        # Print the current working directory
        newpath = os.path.join(os.getcwd(), r'SPF')
        os.chdir(newpath)
        print('current dir is: ', os.getcwd())
        command = 'gradle :jpf-symbc:compile'
        result = subprocess.run(command, shell=True, capture_output=True, text=True)
        print(result.stdout)
        print(result.stderr)

    def generate_spf_test(self, file_name):
        # Print the current working directory
        newpath = os.path.join(os.getcwd(), r'SPF\jpf-symbc')
        os.chdir(newpath)
        print('current dir is: ', os.getcwd())
        command = f'java -Xmx1024m -ea -jar ../jpf-core/build/RunJPF.jar ./src/examples/demo/{file_name}.jpf'
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
#     docker run -v `pwd`:/app -it maven:3.6.1-jdk-12 /bin/bash
#     cd /app
#     java -jar JQF/fuzz/target/jqf-fuzz-2.1-SNAPSHOT-zest-cli.jar -e target/example-java-1.0-SNAPSHOT-fat-tests.jar dev.fuzzit.examplejava.ParseComplexTest fuzz
#     """
#     stdout, stderr = run_WSL(command)
#     print("STDOUT:")
#     print(stdout)
#     print("\nSTDERR:")
#     print(stderr)

class JQF:
    def run_jqf(self,filename):
        # Define the Docker command
        docker_command = (
            "docker run -v %cd%:/app -it maven:3.6.1-jdk-12 /bin/bash -c "
            f"\"cd /app && java -jar JQF/fuzz/target/jqf-fuzz-2.1-SNAPSHOT-zest-cli.jar --duration=5s "
            f"-e target/example-java-1.0-SNAPSHOT-fat-tests.jar dev.fuzzit.examplejava.{filename} fuzz\""
        )

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

    def make_AST(self,function_code):
        parameters = []
        class_name=""
        method_name=""
        try:
            tokens = list(javalang.tokenizer.tokenize(function_code))
            parser = javalang.parser.Parser(tokens)
            tree = parser.parse()

            for path, node in tree.filter(javalang.tree.ClassDeclaration):
                class_name = node.name

                for method in node.methods:
                    method_name = method.name
                    parameters = [(param.name, param.type.name) for param in method.parameters]
            return class_name,parameters,method_name
        except javalang.parser.JavaSyntaxError as e:
            print(f"Syntax error: {e.description} position {e.at}")
        except javalang.tokenizer.LexerError as e:
            print(f"Tokenization error: {str(e)}")
        except Exception as e:
            print(f"Unexpected error: {str(e)}")

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

JQF_object = JQF()
# JQF_object.run_jqf("AddNumbersTest")
functions="""package dev.fuzzit.examplejava;

public class AddNumbers {
    public static int add(int a, int b) {
        return a + b;
    }
}
"""

JQF_object.run_jqf("AddNumbersTest")
