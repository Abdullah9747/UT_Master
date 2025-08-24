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
            result = self.gen_TC_Gemini(value, model)
            
            if isinstance(result, Exception):
                print(f"Error generating test cases for {key}:", result)
                continue
            
            folder = "llmsresults"
            if not os.path.exists(folder):
                os.makedirs(folder)
            filename = os.path.join(folder, f"{func_name}_TestCases_{key}.java")
            with open(filename, "w") as f:
                f.write(result)

            print(f"Test cases for {key} generated successfully in {filename}")

            
    def driver_LLM_Ui(self,java_code,model,choice):
        info = self.extract_function_info(java_code)
        if not info:
            print("No function information extracted.")
            return
        # Generate test cases for each output
        outputs = {
            "generic_signature": info["generic_signature"],
            "partial_placeholder": info["partial_placeholder"],
            "original_signature": info["original_signature"],
            "javadoc": info["javadoc"],
            "full_impl": info["full_impl"],
            

        }
        func_name=info["func_name"]
        
        if choice=="Function Signature":
            key="original_signature"
            value=outputs[key]
            if value is None:
                print(f"Skipping {key} as it is None.")
                return
            print(f"Generating test cases for {key}...")
            result = self.gen_TC_Gemini(value, model)
            
            return result
        elif choice=="JavaDoc":
            key="javadoc"
            value=outputs[key]
            if value is None:
                print(f"Skipping {key} as it is None.")
                return
            print(f"Generating test cases for {key}...")
            result = self.gen_TC_Gemini(value, model)
            
            return result
        elif choice=="Complete Implementation":
            key="full_impl"
            value=outputs[key]
            if value is None:
                print(f"Skipping {key} as it is None.")
                return
            print(f"Generating test cases for {key}...")
            result = self.gen_TC_Gemini(value, model)
            
            return result
        else:
            return None 


       

    def gen_TC_Gemini(self, func, model, javadoc='', method_body='',class_name='',coverage=''):
        try:
            llm = GGAI(model=model, api_key=os.getenv("Google_API_KEY"))

            # Step 1: Analyze the Java function
            analysis_prompt = PromptTemplate(
                input_variables=["func", "javadoc", "method_body"],
                template="""I am going to show you a Java method. Depending on what I give you, you may only have its signature, its Javadoc, or the entire implementation. Please:

1. Summarize what the method is for.
2. List its inputs (parameters) and outputs (return value).

Here is what I know:
{func}
{javadoc}
{method_body}

Now give me your analysis.

""")
            analysis_chain = LLMChain(llm=llm, prompt=analysis_prompt, output_key="analysis")

            # Step 2: Generate test values for statement coverage
            test_values_prompt = PromptTemplate(
                input_variables=["func","analysis","coverage"],
                template="""Generate a minimal set of test input values that collectively achieve 100% {coverage} coverage for the following Java method.
Method Signature:
{func}

Prior Analysis:
{analysis}

Requirements:
1. Coverage Type: {coverage}
2. Output: One test case per line, with only comma-separated argument values.
3. No full JUnit code—just the raw inputs.
"""
            )
            test_values_chain = LLMChain(llm=llm, prompt=test_values_prompt, output_key="test_values")

            # Step 3: Determine expected outputs
            expected_prompt = PromptTemplate(
                input_variables=["test_values", "analysis", "func"],
                template="""You are given a Java method, a prior analysis, and a list of test inputs (one per line, comma-separated). For each input, predict the method's expected return value.

Method Signature:
{func}

Prior Analysis:
{analysis}

Test Inputs:
{test_values}

Output format (one pair per line):
Input: <arg1>, <arg2>, ...
Expected Output: <value>"""
            )
            expected_chain = LLMChain(llm=llm, prompt=expected_prompt, output_key="test_pairs")

            # Step 4: Generate JUnit 4 test cases
            junit_prompt = PromptTemplate(
                input_variables=["test_pairs", "func","class_name"],
                template="""Generate a JUnit 4 test class for the following Java method using exactly the provided input–output pairs. Do not add or remove any test cases. Only output compilable Java code (including all necessary imports and the enclosing test class); no comments or explanations.

The Method Under Test is:
{func}
The Class Under Test is:
{class_name}
Input–Output Pairs (one per line, format: comma-separated args -> expected return):
{test_pairs}

Constraints:
- Do NOT include or redefine the implementation of {class_name} (or any production code). Assume {class_name} and the method from the signature already exist and are on the classpath.
- Use only given test values to generate test cases.

Requirements:
- Use JUnit 4 (org.junit.Test and org.junit.Assert).
- Name the test class appropriately.
- Name each test method testCaseN(), numbering sequentially.
- Inside each test, call the method with the given args and use an appropriate `Assert` method to verify the expected return.
"""
            )
            junit_chain = LLMChain(llm=llm, prompt=junit_prompt, output_key="junit_code")

            # Create the sequential chain
            overall_chain = SequentialChain(
                chains=[analysis_chain, test_values_chain, expected_chain, junit_chain],
                input_variables=["func", "javadoc", "method_body", "class_name","coverage"],
                output_variables=["junit_code"]
            )

            # Run the chain
            result = overall_chain({"func": func, "javadoc": javadoc, "method_body": method_body, "class_name": class_name,"coverage":coverage})

            # Remove code block notations if present
            junit_code = result.get("junit_code", "").replace("```java", "").replace("```", "").strip()

            return junit_code

        except Exception as e:
            return str(e)




    def gen_TC_GPT(self, func, model, javadoc='', method_body='', class_name=''):
        try:
            llm = COAI(model=model, api_key=os.getenv("OPENAI_API_KEY"))
            # Step 1: Analyze the Java function
            analysis_prompt = PromptTemplate(
                input_variables=["func", "javadoc", "method_body"],
                template="""I am going to show you a Java method. Depending on what I give you, you may only have its signature, its Javadoc, or the entire implementation. Please:

1. Summarize what the method is for.
2. List its inputs (parameters) and outputs (return value).

Here is what I know:
{func}
{javadoc}
{method_body}

Now give me your analysis.

""")
            analysis_chain = LLMChain(llm=llm, prompt=analysis_prompt, output_key="analysis")

            # Step 2: Generate test values for statement coverage
            test_values_prompt = PromptTemplate(
                input_variables=["func","analysis"],
                template="""Generate a minimal set of test input values that collectively achieve 100% Statement and Branch coverage for the following Java method.
Method Signature:
{func}

Prior Analysis:
{analysis}

Requirements:
1. Coverage Type: Statement and Branch
2. Output: One test case per line, with only comma-separated argument values.
3. No full JUnit code—just the raw inputs.
"""
            )
            test_values_chain = LLMChain(llm=llm, prompt=test_values_prompt, output_key="test_values")

            # Step 3: Determine expected outputs
            expected_prompt = PromptTemplate(
                input_variables=["test_values", "analysis", "func"],
                template="""You are given a Java method, a prior analysis, and a list of test inputs (one per line, comma-separated). For each input, predict the method's expected return value.

Method Signature:
{func}

Prior Analysis:
{analysis}

Test Inputs:
{test_values}

Output format (one pair per line):
Input: <arg1>, <arg2>, ...
Expected Output: <value>"""
            )
            expected_chain = LLMChain(llm=llm, prompt=expected_prompt, output_key="test_pairs")

            # Step 4: Generate JUnit 4 test cases
            junit_prompt = PromptTemplate(
                input_variables=["test_pairs", "func","class_name"],
                template="""Generate a JUnit 4 test class for the following Java method using exactly the provided input–output pairs. Do not add or remove any test cases. Only output compilable Java code (including all necessary imports and the enclosing test class); no comments or explanations.

The Method Under Test is:
{func}
The Class Under Test is:
{class_name}
Input–Output Pairs (one per line, format: comma-separated args -> expected return):
{test_pairs}

Constraints:
- Do NOT include or redefine the implementation of {class_name} (or any production code). Assume {class_name} and the method from the signature already exist and are on the classpath.
- Use only given test values to generate test cases.

Requirements:
- Use JUnit 4 (org.junit.Test and org.junit.Assert).
- Name the test class appropriately.
- Name each test method testCaseN(), numbering sequentially.
- Inside each test, call the method with the given args and use an appropriate `Assert` method to verify the expected return.
"""
            )
            junit_chain = LLMChain(llm=llm, prompt=junit_prompt, output_key="junit_code")

            # Create the sequential chain
            overall_chain = SequentialChain(
                chains=[analysis_chain, test_values_chain, expected_chain, junit_chain],
                input_variables=["func", "javadoc", "method_body", "class_name"],
                output_variables=["junit_code"]
            )

            # Run the chain
            result = overall_chain({"func": func, "javadoc": javadoc, "method_body": method_body, "class_name": class_name})

            # Remove code block notations if present
            junit_code = result.get("junit_code", "").replace("```java", "").replace("```", "").strip()

            return junit_code

        except Exception as e:
            return str(e)


class GenerateTestCasesSPF:
    def __init__(self):
        load_dotenv()
    def gen_TC_GPT(self, func, test_values, model, javadoc='', method_body='', class_name=''):
        try:
            llm = COAI(model=model, api_key=os.getenv("Google_API_KEY"))
            # Step 1: Determine expected outputs
            pair_prompt = PromptTemplate(
                input_variables=["test_values", "func"],
                template="Based on the given test values pairs: {test_values}, here it is structured as (Test value)  --> Return Value: Expected Output  "
                        """convert it into this format
Output format (one pair per line):
Input: <arg1>, <arg2>, ...
Expected Output: <value>
if No path condition, return empty """
            )
            pair_chain = LLMChain(llm=llm, prompt=pair_prompt, output_key="pair_output")

            # Step 2: Generate JUnit test
            junit_prompt = PromptTemplate(
                input_variables=["func","pair_output","class_name"],
                template="""Generate a JUnit 4 test class for the following Java method using exactly the provided input–output pairs. Do not add or remove any test cases. Only output compilable Java code (including all necessary imports and the enclosing test class); no comments or explanations.

The Method Under Test is:
{func}
The Class Under Test is:
{class_name}
Input–Output Pairs (one per line, format: comma-separated args -> expected return):
{pair_output}

Constraints:
- Do NOT include or redefine the implementation of {class_name} (or any production code). Assume {class_name} and the method from the signature already exist and are on the classpath.
- Use only given test values to generate test cases.

Requirements:
- Use JUnit 4 (org.junit.Test and org.junit.Assert).
- Name the test class appropriately.
- Name each test method testCaseN(), numbering sequentially.
- Inside each test, call the method with the given args and use an appropriate `Assert` method to verify the expected return.
""")
            junit_chain = LLMChain(llm=llm, prompt=junit_prompt, output_key="junit_code")

            # Create the sequential chain
            overall_chain = SequentialChain(
                chains=[pair_chain, junit_chain],
                input_variables=["func", "test_values","class_name","test_values"],
                output_variables=["junit_code"]
            )

            # Run the chain
            result = overall_chain({"func": func, "test_values": test_values, "class_name": class_name})

            # Remove code block notations if present
            junit_code = result.get("junit_code", "").replace("```java", "").replace("```", "").strip()

            return junit_code

        except Exception as e:
            return str(e)
    def gen_TC_Gemini(self, func, test_values, model, javadoc='', method_body='', class_name=''):
        try:
            llm = GGAI(model=model, api_key=os.getenv("Google_API_KEY"))
            # Step 1: Determine expected outputs
            pair_prompt = PromptTemplate(
                input_variables=["test_values", "func"],
                template="Based on the given test values pairs: {test_values}, here it is structured as (Test value)  --> Return Value: Expected Output  "
                        """convert it into this format
Output format (one pair per line):
Input: <arg1>, <arg2>, ...
Expected Output: <value>
if No path condition, return empty """
            )
            pair_chain = LLMChain(llm=llm, prompt=pair_prompt, output_key="pair_output")

            # Step 2: Generate JUnit test
            junit_prompt = PromptTemplate(
                input_variables=["func","pair_output","class_name"],
                template="""Generate a JUnit 4 test class for the following Java method using exactly the provided input–output pairs. Do not add or remove any test cases. Only output compilable Java code (including all necessary imports and the enclosing test class); no comments or explanations.

The Method Under Test is:
{func}
The Class Under Test is:
{class_name}
Input–Output Pairs (one per line, format: comma-separated args -> expected return):
{pair_output}

Constraints:
- Do NOT include or redefine the implementation of {class_name} (or any production code). Assume {class_name} and the method from the signature already exist and are on the classpath.
- Use only given test values to generate test cases.

Requirements:
- Use JUnit 4 (org.junit.Test and org.junit.Assert).
- Name the test class appropriately.
- Name each test method testCaseN(), numbering sequentially.
- Inside each test, call the method with the given args and use an appropriate `Assert` method to verify the expected return.
""")
            junit_chain = LLMChain(llm=llm, prompt=junit_prompt, output_key="junit_code")

            # Create the sequential chain
            overall_chain = SequentialChain(
                chains=[pair_chain, junit_chain],
                input_variables=["func", "test_values","class_name","test_values"],
                output_variables=["junit_code"]
            )

            # Run the chain
            result = overall_chain({"func": func, "test_values": test_values, "class_name": class_name})

            # Remove code block notations if present
            junit_code = result.get("junit_code", "").replace("```java", "").replace("```", "").strip()

            return junit_code

        except Exception as e:
            return str(e)
    

class GenerateTestCasesJQF:
    def __init__(self):
        load_dotenv()



    def gen_TC_GPT(self, values, func, model, javadoc='', method_body='', class_name=''):
        try:
            llm = COAI(model=model, api_key=os.getenv("OPENAI_API_KEY"))
            # Step 1: Analyze the Java function
            analysis_prompt = PromptTemplate(
                input_variables=["func", "javadoc", "method_body"],
                template="""I am going to show you a Java method. Depending on what I give you, you may only have its signature, its Javadoc, or the entire implementation. Please:

    1. Summarize what the method is for.
    2. List its inputs (parameters) and outputs (return value).

    Here is what I know:
    {func}
    {javadoc}
    {method_body}

    Now give me your analysis.

    """)
            analysis_chain = LLMChain(llm=llm, prompt=analysis_prompt, output_key="analysis")

            # Step 2: Determine expected outputs
            expected_prompt = PromptTemplate(
                input_variables=["values", "analysis", "func"],
                template="""You are given a Java method, a prior analysis, and a list of test inputs (one per line, comma-separated). For each input, predict the method's expected return value.

    Method Signature:
    {func}

    Prior Analysis:
    {analysis}

    Test Inputs:
    {values}

    The format of Test Inputs is:
    ['[val1]', '[val2]', '[val3]']
    each sub array represents a different test case with its input values.

    Output format (one pair per line):
    Input: <arg1>, <arg2>, ...
    Expected Output: <value>"""
            )
            expected_chain = LLMChain(llm=llm, prompt=expected_prompt, output_key="test_pairs")  # Changed from "expected_output" to "test_pairs"

            # Step 3: Generate JUnit test
            junit_prompt = PromptTemplate(
                input_variables=["test_pairs", "func", "class_name"],
                template="""Generate a JUnit 4 test class for the following Java method using exactly the provided input–output pairs. Do not add or remove any test cases. Only output compilable Java code (including all necessary imports and the enclosing test class); no comments or explanations.

    The Method Under Test is:
    {func}
    The Class Under Test is:
    {class_name}
    Input–Output Pairs (one per line, format: comma-separated args -> expected return):
    {test_pairs}

    Constraints:
    - Do NOT include or redefine the implementation of {class_name} (or any production code). Assume {class_name} and the method from the signature already exist and are on the classpath.
    - Use only given test values to generate test cases.

    Requirements:
    - Use JUnit 4 (org.junit.Test and org.junit.Assert).
    - Name the test class appropriately.
    - Name each test method testCaseN(), numbering sequentially.
    - Inside each test, call the method with the given args and use an appropriate `Assert` method to verify the expected return.
    """)
            junit_chain = LLMChain(llm=llm, prompt=junit_prompt, output_key="junit_code")

            # Create the sequential chain
            overall_chain = SequentialChain(
                chains=[analysis_chain, expected_chain, junit_chain],
                input_variables=["func", "values", "javadoc", "method_body", "class_name"],
                output_variables=["junit_code"]
            )

            # Run the chain
            result = overall_chain({"func": func, "values": values, "javadoc": javadoc, "method_body": method_body, "class_name": class_name})

            # Remove code block notations if present
            junit_code = result.get("junit_code", "").replace("```java", "").replace("```", "").strip()

            return junit_code

        except Exception as e:
            return str(e)





        
    def gen_TC_Gemini(self, values, func, model, javadoc='', method_body='', class_name=''):
        try:
            llm = GGAI(model=model, api_key=os.getenv("Google_API_KEY"))
            # Step 1: Analyze the Java function
            analysis_prompt = PromptTemplate(
                input_variables=["func", "javadoc", "method_body"],
                template="""I am going to show you a Java method. Depending on what I give you, you may only have its signature, its Javadoc, or the entire implementation. Please:

    1. Summarize what the method is for.
    2. List its inputs (parameters) and outputs (return value).

    Here is what I know:
    {func}
    {javadoc}
    {method_body}

    Now give me your analysis.

    """)
            analysis_chain = LLMChain(llm=llm, prompt=analysis_prompt, output_key="analysis")

            # Step 2: Determine expected outputs
            expected_prompt = PromptTemplate(
                input_variables=["values", "analysis", "func"],
                template="""You are given a Java method, a prior analysis, and a list of test inputs (one per line, comma-separated). For each input, predict the method's expected return value.

    Method Signature:
    {func}

    Prior Analysis:
    {analysis}

    Test Inputs:
    {values}

    The format of Test Inputs is:
    ['[val1]', '[val2]', '[val3]']
    each sub array represents a different test case with its input values.

    Output format (one pair per line):
    Input: <arg1>, <arg2>, ...
    Expected Output: <value>"""
            )
            expected_chain = LLMChain(llm=llm, prompt=expected_prompt, output_key="test_pairs")  # Changed from "expected_output" to "test_pairs"

            # Step 3: Generate JUnit test
            junit_prompt = PromptTemplate(
                input_variables=["test_pairs", "func", "class_name"],
                template="""Generate a JUnit 4 test class for the following Java method using exactly the provided input–output pairs. Do not add or remove any test cases. Only output compilable Java code (including all necessary imports and the enclosing test class); no comments or explanations.

    The Method Under Test is:
    {func}
    The Class Under Test is:
    {class_name}
    Input–Output Pairs (one per line, format: comma-separated args -> expected return):
    {test_pairs}

    Constraints:
    - Do NOT include or redefine the implementation of {class_name} (or any production code). Assume {class_name} and the method from the signature already exist and are on the classpath.
    - Use only given test values to generate test cases.

    Requirements:
    - Use JUnit 4 (org.junit.Test and org.junit.Assert).
    - Name the test class appropriately.
    - Name each test method testCaseN(), numbering sequentially.
    - Inside each test, call the method with the given args and use an appropriate `Assert` method to verify the expected return.
    """)
            junit_chain = LLMChain(llm=llm, prompt=junit_prompt, output_key="junit_code")

            # Create the sequential chain
            overall_chain = SequentialChain(
                chains=[analysis_chain, expected_chain, junit_chain],
                input_variables=["func", "values", "javadoc", "method_body", "class_name"],
                output_variables=["junit_code"]
            )

            # Run the chain
            result = overall_chain({"func": func, "values": values, "javadoc": javadoc, "method_body": method_body, "class_name": class_name})

            # Remove code block notations if present
            junit_code = result.get("junit_code", "").replace("```java", "").replace("```", "").strip()

            return junit_code

        except Exception as e:
            return str(e)


def extract_java_method_parts(java_code):
    """
    Extracts the first Java method's Javadoc, signature (without body), body, full method text, and enclosing class name.
    Returns a tuple: (javadoc, signature, body, full_method, class_name).
    """
    import re

    def find_matching_brace(text: str, start_idx: int) -> int:
        # start_idx must point to the opening '{'
        n = len(text)
        i = start_idx
        if i >= n or text[i] != '{':
            return -1
        depth = 1
        i += 1
        while i < n:
            ch = text[i]
            # Handle line comments
            if ch == '/' and i + 1 < n and text[i+1] == '/':
                nl = text.find('\n', i + 2)
                if nl == -1:
                    return -1
                i = nl + 1
                continue
            # Handle block comments
            if ch == '/' and i + 1 < n and text[i+1] == '*':
                end = text.find('*/', i + 2)
                if end == -1:
                    return -1
                i = end + 2
                continue
            # Handle strings
            if ch == '"':
                i += 1
                while i < n:
                    if text[i] == '\\':
                        i += 2
                        continue
                    if text[i] == '"':
                        i += 1
                        break
                    i += 1
                continue
            # Handle char literals
            if ch == '\'':
                i += 1
                while i < n:
                    if text[i] == '\\':
                        i += 2
                        continue
                    if text[i] == '\'':
                        i += 1
                        break
                    i += 1
                continue
            # Braces
            if ch == '{':
                depth += 1
                i += 1
                continue
            if ch == '}':
                depth -= 1
                if depth == 0:
                    return i
                i += 1
                continue
            i += 1
        return -1

    header_re = re.compile(r"""
        (?P<prefix>\s*)
        (?P<mods>(?:@\w+(?:\([^)]*\))?\s+)*                                 # annotations
               (?:(?:public|protected|private|static|final|abstract|
                   synchronized|native|strictfp)\s+)*)
        (?P<ret>[\w<>\[\].?,\s]+?)\s+                                       # return type
        (?P<name>[A-Za-z_]\w*)\s*                                           # method name
        \(
            (?P<params>[^)]*)
        \)\s*
        (?:throws\s+[^{]*)?                                                 # optional throws
        \{                                                                  # opening brace of method body
    """, re.VERBOSE | re.DOTALL)

    # Find a method header
    m = header_re.search(java_code)
    if not m:
        return None, None, None, None, None

    open_brace_idx = m.end() - 1
    close_brace_idx = find_matching_brace(java_code, open_brace_idx)
    if close_brace_idx == -1:
        return None, None, None, None, None

    # Extract signature (without body braces)
    signature = java_code[m.start():open_brace_idx].strip()

    # Extract body content (inside braces)
    body = java_code[open_brace_idx + 1:close_brace_idx].strip()

    # Try to capture the Javadoc immediately preceding the method
    javadoc = None
    j_start = None  # ensure defined even if no Javadoc
    before_header_end = m.start()
    j_end = java_code.rfind("*/", 0, before_header_end)
    if j_end != -1:
        j_start_candidate = java_code.rfind("/**", 0, j_end)
        if j_start_candidate != -1:
            between = java_code[j_end + 2:before_header_end]
            # Ensure only whitespace and annotations are between Javadoc and header
            if re.match(r'^\s*(?:@\w+(?:\([^)]*\))?\s*)*$', between, re.DOTALL):
                javadoc = java_code[j_start_candidate:j_end + 2].strip()
                j_start = j_start_candidate

    # Enclosing class/interface/enum/record name (last one before the method)
    class_name = None
    for cm in re.finditer(r'\b(?:class|interface|enum|record)\s+([A-Za-z_]\w*)', java_code[:m.start()]):
        class_name = cm.group(1)

    # Full method text (with Javadoc if present)
    start_full = j_start if javadoc is not None else m.start()
    full_method = java_code[start_full:close_brace_idx + 1].strip()

    return javadoc, signature, body, full_method, class_name
