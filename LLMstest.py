import re
import random
import string
import os
import pandas as pd
import time
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
            javadoc, signature, body, full_method, class_name = extract_java_method_parts(java_code)
            start_time = time.time()
            result = self.gen_TC_Gemini(signature, model, javadoc, body,class_name)
            end_time = time.time()
            duration = end_time - start_time
            print(f"Execution time: {duration} seconds")        
            return result, duration


            
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


       

# ...existing code...
    def gen_TC_Gemini(self, func, model, javadoc='', method_body='',class_name=''):
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

"""
            )
            analysis_chain = LLMChain(llm=llm, prompt=analysis_prompt, output_key="analysis")
            
            # Run Step 1 and print result
            print("=" * 50)
            print("STEP 1: ANALYSIS")
            print("=" * 50)
            analysis_result = analysis_chain.run({
                "func": func,
                "javadoc": javadoc,
                "method_body": method_body
            })
            print("Analysis Result:")
            print(analysis_result)
            print()

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
            
            # Run Step 2 and print result
            print("=" * 50)
            print("STEP 2: TEST VALUES GENERATION")
            print("=" * 50)
            test_values_result = test_values_chain.run({
                "func": func,
                "analysis": analysis_result
            })
            print("Test Values Result:")
            print(test_values_result)
            print()

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
            
            # Run Step 3 and print result
            print("=" * 50)
            print("STEP 3: EXPECTED OUTPUTS")
            print("=" * 50)
            expected_result = expected_chain.run({
                "test_values": test_values_result,
                "analysis": analysis_result,
                "func": func
            })
            print("Expected Outputs Result:")
            print(expected_result)
            print()

# ...existing code...
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

Requirements:
- Use JUnit 4 (org.junit.Test and org.junit.Assert).
- Name the test class appropriately.
- Name each test method testCaseN(), numbering sequentially.
- Inside each test, call the method with the given args and use an appropriate `Assert` method to verify the expected return.
"""
            )
            junit_chain = LLMChain(llm=llm, prompt=junit_prompt, output_key="junit_code")
            
            # Run Step 4 and print result
            print("=" * 50)
            print("STEP 4: JUNIT CODE GENERATION")
            print("=" * 50)
            print(f"Using class_name: {class_name!r}")
            junit_result = junit_chain.run({
                "test_pairs": expected_result,
                "func": func,
                "class_name": class_name or ""
            })
            print("JUnit Code Result:")
            print(junit_result)
            print()

            # Remove code block notations if present
            junit_code = junit_result.replace("```java", "").replace("```", "").strip()
# ...existing code...

            print("=" * 50)
            print("FINAL CLEANED JUNIT CODE")
            print("=" * 50)
            print(junit_code)

            return junit_code

        except Exception as e:
            print(f"Error: {e}")
            return str(e)
# ...existing code...


    def gen_TC_GPT(self, func, model):
        try:
            llm = COAI(model=model, api_key=os.getenv("OPENAI_API_KEY"))
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
                        "While writing test make sure to give correct values for both input and what to expect as output."
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


class GenerateTestCasesSPF:
    def __init__(self):
        load_dotenv()
    def gen_TC_Gemini(self, func, test_values, model):
        try:
            llm = GGAI(model=model, api_key=os.getenv("Google_API_KEY"))

            # Step 1: Analyze the Java function
            analysis_prompt = PromptTemplate(
                input_variables=["func"],
                template="Analyze the following Java function and explain what it does in simple terms:\n\n{func}"
            )
            analysis_chain = LLMChain(llm=llm, prompt=analysis_prompt, output_key="analysis")

            # Step 2: Determine expected outputs
            expected_prompt = PromptTemplate(
                input_variables=["test_values", "analysis", "func"],
                template="Based on the given test values: {test_values}, "
                        "provide the expected outputs of the function.\n\nFunction Analysis:\n{analysis}\n\nFunction Code:\n{func}"
            )
            expected_chain = LLMChain(llm=llm, prompt=expected_prompt, output_key="expected_output")

            # Step 3: Generate JUnit test
            junit_prompt = PromptTemplate(
                input_variables=["analysis", "func", "test_values", "expected_output"],
                template="Based on this function analysis, generate a JUnit 4 test file for the given Java function.\n"
                        "I will be providing you with test values and the expected output values.\n"
                        "Use these test values: {test_values} for generating test cases and these are expected output values: {expected_output}\n\n"
                        "Function:\n{func}\n\n"
                        "Make sure to use test values to generate the test cases. "
                        "Test values are resprnted in tuples and do not consider keyword 'demo'"
                        "While writing tests, make sure to give correct values for both input and expected output. "
                        "Just return the JUnit 4 test code, no explanations and no code block notations."
            )
            junit_chain = LLMChain(llm=llm, prompt=junit_prompt, output_key="junit_code")

            # Create the sequential chain
            overall_chain = SequentialChain(
                chains=[analysis_chain, expected_chain, junit_chain],
                input_variables=["func", "test_values"],
                output_variables=["junit_code"]
            )

            # Run the chain
            result = overall_chain({"func": func, "test_values": test_values})

            # Remove code block notations if present
            junit_code = result.get("junit_code", "").replace("```java", "").replace("```", "").strip()

            return junit_code

        except Exception as e:
            print(f"Error: {e}")
            return str(e)

    

class GenerateTestCasesJQF:
    def __init__(self):
        load_dotenv()



    def gen_TC_Gemini(self, valid, failures, func, model):
            try:
                llm = GGAI(model=model, api_key=os.getenv("Google_API_KEY"))

                # Step 1: Analyze the Java function
                analysis_prompt = PromptTemplate(
                    input_variables=["func"],
                    template="Analyze the following Java function and explain what it does in simple terms:\n\n{func}"
                )
                analysis_chain = LLMChain(llm=llm, prompt=analysis_prompt, output_key="analysis")

                # Step 2: Determine expected outputs
                expected_prompt = PromptTemplate(
                    input_variables=["valid", "failures", "analysis", "func"],
                    template="Based on the given valid test values: {valid} and failure test values: {failures}, "
                            "provide the expected outputs of the function.\n\nFunction Analysis:\n{analysis}\n\nFunction Code:\n{func}"
                )
                expected_chain = LLMChain(llm=llm, prompt=expected_prompt, output_key="expected_output")

                # Step 3: Generate JUnit test
                junit_prompt = PromptTemplate(
                    input_variables=["analysis", "func", "valid", "failures", "expected_output"],
                    template="Based on this function analysis, generate a JUnit 4 test file for the given Java function.\n"
                            "I will be providing you with the valid and failure test values and the expected output values.\n"
                            "Use these valid values: {valid} for generating test cases, also these are test values "
                            "in case of any failure: {failures} and these are expected output values: {expected_output}\n\n"
                            "Function:\n{func}\n\n"
                            "Make sure to use valid and failure test values to generate the test cases. "
                            "While writing tests, make sure to give correct values for both input and expected output. "
                            "Just return the JUnit 4 test code, no explanations and no code block notations."
                )
                junit_chain = LLMChain(llm=llm, prompt=junit_prompt, output_key="junit_code")

                # Create the sequential chain
                overall_chain = SequentialChain(
                    chains=[analysis_chain, expected_chain, junit_chain],
                    input_variables=["func", "valid", "failures"],
                    output_variables=["junit_code"]
                )

                # Run the chain
                result = overall_chain({"func": func, "valid": valid, "failures": failures})

                # Remove code block notations if present
                junit_code = result.get("junit_code", "").replace("```java", "").replace("```", "").strip()

                return junit_code

            except Exception as e:
                print(f"Error: {e}")
                return str(e)





        
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



# ...existing code...
def extract_java_method_parts(java_code: str):
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
# ...existing code...
# Update the call site to unpack class_name as well
function1="""public class ISINSIDETRIANGLE {
/**
	 * Check if the point p is inside the triangle
	 */
	private static boolean isInsideTriangle(double Ax, double Ay,
      double Bx, double By,
      double Cx, double Cy,
      double Px, double Py)

		{
		double ax, ay, bx, by, cx, cy, apx, apy, bpx, bpy, cpx, cpy;
		double cCROSSap, bCROSScp, aCROSSbp;
		
		ax = Cx - Bx;  ay = Cy - By;
		bx = Ax - Cx;  by = Ay - Cy;
		cx = Bx - Ax;  cy = By - Ay;
		apx= Px - Ax;  apy= Py - Ay;
		bpx= Px - Bx;  bpy= Py - By;
		cpx= Px - Cx;  cpy= Py - Cy;
		
		aCROSSbp = ax*bpy - ay*bpx;
		cCROSSap = cx*apy - cy*apx;
		bCROSScp = bx*cpy - by*cpx;
		
		return ((aCROSSbp >= 0.0f) && (bCROSScp >= 0.0f) && (cCROSSap >= 0.0f));
		}
}"""

# javadoc, signature, body, full_method, class_name = extract_java_method_parts(function1)
# print(full_method)
# print("Class:", class_name)
# newgen=GenerateTestCasesLLM()
# result=newgen.gen_TC_Gemini(signature, "gemini-1.5-flash", javadoc, body,class_name)
# print(result)
def process_csv_functions(input_csv='sampled_15_primitive_functions.csv', output_csv='test_results.csv', model='gemini-1.5-flash'):
    """
    Read functions from CSV, extract method parts, generate test cases, and save results.
    
    Args:
        input_csv: Path to input CSV file
        output_csv: Path to output CSV file  
        model: Model to use for test generation
    """
    try:
        # Read the input CSV
        df = pd.read_csv(input_csv)
        print(f"Loaded {len(df)} functions from {input_csv}")
        
        # Initialize the test generator
        newgen = GenerateTestCasesLLM()
        
        # Lists to store results
        results = []
        
        for idx, row in df.iterrows():
            java_code = row['Functions']
            cyclomatic_complexity = row['cyclomatic_complexity']
            
            print(f"\nProcessing function {idx + 1}/{len(df)}...")
            print("=" * 60)
            
            try:
                # Extract method parts
                javadoc, signature, body, full_method, class_name = extract_java_method_parts(java_code)
                
                if signature is None:
                    print(f"Failed to extract method parts for function {idx + 1}")
                    results.append({
                        'original_code': java_code,
                        'class_name': None,
                        'signature': None,
                        'javadoc': None,
                        'body': None,
                        'full_method': None,
                        'cyclomatic_complexity': cyclomatic_complexity,
                        'generated_tests': None,
                        'error': 'Failed to extract method parts'
                    })
                    continue
                
                print(f"Extracted method from class: {class_name}")
                print(f"Method signature: {signature}")
                
                # Generate test cases
                test_cases = newgen.gen_TC_Gemini(signature, model, javadoc or '', body or '', class_name or '')
                
                # Store results
                results.append({
                    'original_code': java_code,
                    'class_name': class_name,
                    'signature': signature,
                    'javadoc': javadoc,
                    'body': body,
                    'full_method': full_method,
                    'cyclomatic_complexity': cyclomatic_complexity,
                    'generated_tests': test_cases,
                    'error': None
                })
                
                print(f"Successfully generated tests for function {idx + 1}")
                
            except Exception as e:
                print(f"Error processing function {idx + 1}: {str(e)}")
                results.append({
                    'original_code': java_code,
                    'class_name': None,
                    'signature': None,
                    'javadoc': None,
                    'body': None,
                    'full_method': None,
                    'cyclomatic_complexity': cyclomatic_complexity,
                    'generated_tests': None,
                    'error': str(e)
                })
        
        # Create results DataFrame
        results_df = pd.DataFrame(results)
        
        # Save to CSV
        results_df.to_csv(output_csv, index=False)
        print(f"\nResults saved to {output_csv}")
        
        # Print summary
        successful = len(results_df[results_df['error'].isna()])
        failed = len(results_df[results_df['error'].notna()])
        print(f"\nSummary:")
        print(f"Successfully processed: {successful}")
        print(f"Failed: {failed}")
        print(f"Total: {len(results_df)}")
        
        return results_df
        
    except Exception as e:
        print(f"Error reading CSV file: {str(e)}")
        return None

# Function to save individual test files
def save_individual_test_files(results_df, output_dir='generated_tests'):
    """
    Save each generated test as a separate .java file
    
    Args:
        results_df: DataFrame with test results
        output_dir: Directory to save test files
    """
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)
    
    saved_count = 0
    for idx, row in results_df.iterrows():
        if row['generated_tests'] is not None and row['class_name'] is not None:
            filename = f"{row['class_name']}_Test_{idx}.java"
            filepath = os.path.join(output_dir, filename)
            
            try:
                with open(filepath, 'w', encoding='utf-8') as f:
                    f.write(row['generated_tests'])
                saved_count += 1
            except Exception as e:
                print(f"Error saving {filename}: {str(e)}")
    
    print(f"Saved {saved_count} individual test files to {output_dir}/")

# Usage example:
if __name__ == "__main__":
    # Process the CSV and generate tests
    results = process_csv_functions(
        input_csv='sampled_15_primitive_functions.csv',
        output_csv='test_generation_results.csv',
        model='gemini-1.5-flash'
    )
    
    # Optionally save individual test files
    if results is not None:
        save_individual_test_files(results, 'generated_tests')
# ...existing code...