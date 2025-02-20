import re
import random
import string
import os
from dotenv import load_dotenv
from langchain_google_genai import GoogleGenerativeAI as GGAI
from langchain_openai import ChatOpenAI as COAI
from langchain.prompts import PromptTemplate
from langchain.chains import LLMChain, SequentialChain
from langchain.schema.runnable import RunnableLambda, RunnablePassthrough, RunnableSequence

class GenerateTestCasesLLM:
    def __init__(self):
        load_dotenv()
    def generate_random_string(self, length=10):
        letters = string.ascii_letters
        return ''.join(random.choice(letters) for _ in range(length))

    def extract_function_info(self,code ):
        """
        Extracts information about a single function from the provided code.
        
        Returns a dictionary containing:
        - generic_signature: a signature with a random function name and all parameters replaced with placeholders.
        - partial_placeholder: a signature with the actual function name, but with all parameter names replaced with random placeholders.
        - original_signature: the exact signature from the code.
        - javadoc: the Javadoc comment (if present).
        - full_impl: the complete function implementation including Javadoc (if available).
        """
        # Regex pattern to capture an optional Javadoc, modifiers, return type, function name,
        # parameters, and the body of the function.
        pattern = re.compile(
            r'(?P<javadoc>/\*\*.*?\*/)?\s*'
            r'(?P<signature>(?:public|protected|private|static|\s)+\s*'
            r'(?P<return_type>[^\s]+)\s+'
            r'(?P<func_name>\w+)\s*\('
            r'(?P<params>[^\)]*)\)\s*\{(?P<body>.*)\})',
            re.DOTALL
        )
        
        match = re.search(pattern, code)
        if not match:
            return None
        
        javadoc = match.group('javadoc')
        signature = match.group('signature')
        return_type = match.group('return_type')
        func_name = match.group('func_name')
        params = match.group('params').strip()
        
        # Parse parameters assuming they are separated by commas and are in the format: "type name"
        param_list = []
        if params:
            param_list = [p.strip() for p in params.split(',') if p.strip()]
        
        # Build generic_signature: use a random function name and placeholders for every parameter.
        generic_params = []
        for p in param_list:
            parts = p.split()
            if parts:
                # The type is everything except the last part (parameter name)
                p_type = ' '.join(parts[:-1])
                generic_params.append(f"{p_type} {self.generate_random_string()}")
        generic_signature = f"{return_type} {self.generate_random_string()}({', '.join(generic_params)})"
        
        # Build partial_placeholder: use the actual function name, but replace all parameter names with random placeholders.
        partial_params = []
        for p in param_list:
            parts = p.split()
            if parts:
                p_type = ' '.join(parts[:-1])
                partial_params.append(f"{p_type} {self.generate_random_string()}")
        partial_placeholder = f"{return_type} {func_name}({', '.join(partial_params)})"
        
        # The original signature remains unchanged.
        original_signature = f"{return_type} {func_name}({params})"
        
        # Combine the Javadoc (if any) with the full function implementation.
        full_impl = ""
        if javadoc:
            full_impl += javadoc.strip() + "\n"
        full_impl += signature.strip()
        
        return {
            "generic_signature": generic_signature,
            "partial_placeholder": partial_placeholder,
            "original_signature": original_signature,
            "javadoc": javadoc.strip() if javadoc else None,
            "full_impl": full_impl,
            "func_name": func_name
        }
    
    def driver_LLM(self,java_code,model):
        info = self.extract_function_info(java_code)
        if not info:
            print("No function information extracted.")
            return
        
        # Initialize the test case generator
        # test_gen = GenerateTestCasesLLM()
        
        # Generate test cases for each output
        outputs = {
            "generic_signature": info["generic_signature"],
            "partial_placeholder": info["partial_placeholder"],
            "original_signature": info["original_signature"],
            "javadoc": info["javadoc"],
            "full_impl": info["full_impl"],
            

        }
        func_name=info["func_name"]
        
        for key, value in outputs.items():
            if value is None:
                print(f"Skipping {key} as it is None.")
                continue
            
            print(f"Generating test cases for {key}...")
            result = self.gen_TC_test(value, model)
            
            if isinstance(result, Exception):
                print(f"Error generating test cases for {key}:", result)
                continue
            
            # Write the generated test cases to a file
            # filename = f"TestCases_{key}.java"
            # with open(filename, "w") as f:
            #     f.write(result)
            folder = "llmsresults"
            if not os.path.exists(folder):
                os.makedirs(folder)
            filename = os.path.join(folder, f"{func_name}_TestCases_{key}.java")
            with open(filename, "w") as f:
                f.write(result)

            print(f"Test cases for {key} generated successfully in {filename}")

    def gen_TC_Gemini(self, func, model):
        try:

            llm = GGAI(model=model, api_key=os.getenv("Google_API_KEY"))
            messages = [
                ("system", "You are going to generate Junit4 file for the given Java function and do not write anything else like ```java or ```"),
                ("human", "Just give me Junit directly I do not need any other information or code block elements"),
                ("human", func)
            ]
            response = llm.invoke(messages)
            cleaned_response = response.strip("```java").strip("```").strip()
            return cleaned_response
        except Exception as e:
            print(e)
            return e

    def gen_TC_test(self, func, model):
        try:
            llm = GGAI(model=model, api_key=os.getenv("Google_API_KEY"))
            # Step 1: Analyze the Java function
            analysis_prompt = PromptTemplate(
                input_variables=["func"],
                template="Analyze the following Java function and explain what it does in simple terms:\n\n{func}"
            )
            analysis_chain = LLMChain(llm=llm, prompt=analysis_prompt, output_key="analysis")

            # Step 2: Generate test values for statement coverage
            # can be branch cov.
            test_values_prompt = PromptTemplate(
                input_variables=["analysis"],
                template="Based on this function analysis, generate test values to ensure statement coverage:\n\n{analysis}"
            )
            test_values_chain = LLMChain(llm=llm, prompt=test_values_prompt, output_key="test_values")

            # Step 3: Generate JUnit 4 test cases
            junit_prompt = PromptTemplate(
                input_variables=["test_values", "func"],
                template="You are going to generate a JUnit 4 test file for the given Java function.\n"
                        "Use these test values: {test_values}\n\nFunction:\n{func}\n\n"
                        "Just return the JUnit 4 test code, no explanations and code block notations."
            )
            junit_chain = LLMChain(llm=llm, prompt=junit_prompt, output_key="junit_code")

            # Create the sequential chain
            overall_chain = SequentialChain(
                chains=[analysis_chain, test_values_chain, junit_chain],
                input_variables=["func"],
                output_variables=["junit_code"]
            )

            # Run the chain
            result = overall_chain({"func": func})
            if "```java" or "```"in result["junit_code"]:

                result["junit_code"]=result["junit_code"].replace("```java","")
                result["junit_code"]=result["junit_code"].replace("```","")
                result["junit_code"]=result["junit_code"][1:]
            return result["junit_code"]
        except Exception as e:
            print(e)
            return e




    def gen_TC_GPT(self, func, model):
        try:
            llm = COAI(model=model, api_key=os.getenv("OPENAI_API_KEY"))
            messages = [
                ("system", "You are going to generate Junit4 file for the given Java function and do not write anything else like ```java or ```"),
                ("human", func)
            ]
            response = llm.invoke(messages)
            return response.content
        except Exception as e:
            print(e)
            return e


class GenerateTestCasesSPF:
    def __init__(self):
        load_dotenv()
    def gen_TC_test(self, func, test_values, model):
        try:
            llm = GGAI(model=model, api_key=os.getenv("Google_API_KEY"))

            # Step 1: Analyze the Java function
            analysis_prompt = PromptTemplate(
                input_variables=["func"],
                template="Analyze the following Java function and explain what it does in simple terms:\n\n{func}"
            )
            analysis_chain = LLMChain(llm=llm, prompt=analysis_prompt, output_key="analysis")

            # Step 2: Generate JUnit test
            junit_prompt = PromptTemplate(
                input_variables=["analysis", "func", "test_values"],
                template="Based on this function {analysis}, generate a JUnit 4 test file for the given Java function.\n"
                        "Use these test values: {test_values}\n\nFunction:\n{func}\n\n"
                        "Just return the JUnit 4 test code, no explanations and code block notations."
            )
            junit_chain = LLMChain(llm=llm, prompt=junit_prompt, output_key="junit_code")

            # Create the sequential chain
            overall_chain = SequentialChain(
                chains=[analysis_chain, junit_chain],
                input_variables=["func", "test_values"],
                output_variables=["junit_code"]
            )

            # Run the chain
            result = overall_chain({"func": func, "test_values": test_values})

            # Remove code block notations if present
            if "```java" in result["junit_code"] or "```" in result["junit_code"]:
                result["junit_code"] = result["junit_code"].replace("```java", "").replace("```", "").strip()

            return result["junit_code"]

        except Exception as e:
            print(e)
            return str(e)

    def gen_TC_Gemini(self,output,func,model):
        try:
            llm = GGAI(model=model, api_key=os.getenv("Google_API_KEY"))
            messages = [
                ("system", "You are going to generate Junit4 file for the given Java function and do not write anything else like ```java or ```"),
                ("human", "Just give me Junit directly I do not need any other information or code block elements"),
                ("human", f"Function: {func}"),
                ("human", f"Output: {output}")
            ]
            response = llm.invoke(messages)
            cleaned_response = response.replace("```java","").replace("```","")
            return cleaned_response
        except Exception as e:
            print(e)
            return e
    

class GenerateTestCasesJQF:
    def __init__(self):
        load_dotenv()

    def gen_TC_test(self, func, test_values, model):
        try:
            llm = GGAI(model=model, api_key=os.getenv("Google_API_KEY"))

            # Step 1: Analyze the Java function
            analysis_prompt = PromptTemplate(
                input_variables=["func"],
                template="Analyze the following Java function and explain what it does in simple terms:\n\n{func}"
            )
            analysis_chain = LLMChain(llm=llm, prompt=analysis_prompt, output_key="analysis")

            # Step 2: Generate JUnit test
            junit_prompt = PromptTemplate(
                input_variables=["analysis", "func", "test_values"],
                template="Based on this function analysis, generate a JUnit 4 test file for the given Java function.\n"
                        "Use these test values: {test_values}\n\nFunction:\n{func}\n\n"
                        "Just return the JUnit 4 test code, no explanations and code block notations."
            )
            junit_chain = LLMChain(llm=llm, prompt=junit_prompt, output_key="junit_code")

            # Create the sequential chain
            overall_chain = SequentialChain(
                chains=[analysis_chain, junit_chain],
                input_variables=["func", "test_values"],
                output_variables=["junit_code"]
            )

            # Run the chain
            result = overall_chain({"func": func, "test_values": test_values})

            # Remove code block notations if present
            if "```java" in result["junit_code"] or "```" in result["junit_code"]:
                result["junit_code"] = result["junit_code"].replace("```java", "").replace("```", "").strip()

            return result["junit_code"]

        except Exception as e:
            print(e)
            return str(e)

    def gen_TC_Gemini(self,plot_data,fuzz_log,fun,model):
        try:
            llm = GGAI(model=model, api_key=os.getenv("Google_API_KEY"))
            messages = [
                ("system", "You are going to generate Junit4 file for the given Java function and do not write anything else like ```java or ```"),
                ("human", "Just give me Junit directly I do not need any other information or code block elements"),
                ("human", "I am going to give you the plot data and fuzz log from JQF containing the test values along with logs"),
                ("human", f"Function: {fun}"),
                ("human", f"Plot Data: {plot_data}"),
                ("human", f"Fuzz Log: {fuzz_log}")
            ]
            response = llm.invoke(messages)
            cleaned_response=response.replace("```java","").replace("```","")
            return cleaned_response
        except Exception as e:
            print(e)
            return e
       
        
    def gen_TC_GPT(self,plot_data,fuzz_log,fun,model):
        try:
            llm = COAI(model=model, api_key=os.getenv("OPENAI_API_KEY"))
            messages = [
                ("system", "You are going to generate Junit4 file for the given Java function and do not write anything else like ```java or ```"),
                ("human", "Just give me Junit directly I do not need any other information or code block elements"),
                ("human", "I am going to give you the plot data and fuzz log from JQF containing the test values along with logs"),
                ("human", f"Function: {fun}"),
                ("human", f"Plot Data: {plot_data}"),
                ("human", f"Fuzz Log: {fuzz_log}")
            ]
            response = llm.invoke(messages)
            cleaned_response=response.content.replace("```java","").replace("```","")
            return cleaned_response
        except Exception as e:
            print(e)
            return e



