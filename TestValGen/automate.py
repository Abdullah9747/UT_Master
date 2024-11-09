import os
import subprocess


class SPF:
    def build_classfile(self, file_name):
        base_dir = r'SPF\jpf-symbc\src\examples\demo'
        path = os.path.join(base_dir, file_name)
        print(path)
        return path

    def compiling_spf(self):
        # Print the current working directory
        newpath = os.path.join(os.getcwd(), 'SPF')
        os.chdir(newpath)
        print('current dir is: ', os.getcwd())
        command = 'gradle :jpf-symbc:compile'
        result = subprocess.run(command, shell=True, capture_output=True, text=True)
        print(result.stdout)
        print(result.stderr)

    def generate_spf_test(self, file_name):
        # Print the current working directory
        newpath = os.path.join(os.getcwd(), 'SPF\jpf-symbc')
        os.chdir(newpath)
        print('current dir is: ', os.getcwd())
        command = f'java -Xmx1024m -ea -jar ../jpf-core/build/RunJPF.jar ./src/examples/demo/{file_name}.jpf'
        result = subprocess.run(command, shell=True, capture_output=True, text=True)
        
        print(result.stdout)
        print(result.stderr)

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
        write_file_name = os.path.join(os.getcwd(), f'SPF\jpf-symbc\src\examples\demo\{file_name}.jpf')
        
        
        with open(write_file_name, 'w') as file:
            file.write(file_content)
        return file_content


# Example usage:
spf = SPF()
#file_name = "ExampleClass"
#params = ["sym", "sym"]
#time_limit = 5  
#jpf_file_content = spf.generate_jpf_file(file_name, params, time_limit)
#print(jpf_file_content)

spf.generate_spf_test("NumericExample")