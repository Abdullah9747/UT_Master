import re
import random
import string
import os
from dotenv import load_dotenv
from langchain_google_genai import GoogleGenerativeAI as GGAI
from langchain_openai import ChatOpenAI as COAI


def generate_random_string(length=10):
    """
    Generates a random string of the specified length.
    """
    letters = string.ascii_letters
    return ''.join(random.choice(letters) for _ in range(length))

def extract_function_info(code):
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
            generic_params.append(f"{p_type} {generate_random_string()}")
    generic_signature = f"{return_type} {generate_random_string()}({', '.join(generic_params)})"
    
    # Build partial_placeholder: use the actual function name, but replace all parameter names with random placeholders.
    partial_params = []
    for p in param_list:
        parts = p.split()
        if parts:
            p_type = ' '.join(parts[:-1])
            partial_params.append(f"{p_type} {generate_random_string()}")
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
        "full_impl": full_impl
    }

class GenerateTestCasesLLM:
    def __init__(self):
        load_dotenv()
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



def main():
    load_dotenv()
    
    # Example Java code (replace with actual code input as needed)
    java_code = """
public class LIS {

    /**
     * Computes the longest increasing subsequence (LIS) in an array of integers.
     *
     * @param nums the input array of integers
     * @return a list representing the longest increasing subsequence
     */
    public static List<Integer> longestIncreasingSubsequence(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Input array must not be null");
        }
        // ... implementation ...
        return new ArrayList<>();
    }
}
"""
    # Extract function information using code1's function
    info = extract_function_info(java_code)
    if not info:
        print("No function information extracted.")
        return
    
    # Initialize the test case generator
    test_gen = GenerateTestCasesLLM()
    
    # Generate test cases for each output
    outputs = {
        "generic_signature": info["generic_signature"],
        "partial_placeholder": info["partial_placeholder"],
        "original_signature": info["original_signature"],
        "javadoc": info["javadoc"],
        "full_impl": info["full_impl"]
    }
    
    for key, value in outputs.items():
        if value is None:
            print(f"Skipping {key} as it is None.")
            continue
        
        print(f"Generating test cases for {key}...")
        result = test_gen.gen_TC_Gemini(value, "gemini-1.5-flash")
        
        if isinstance(result, Exception):
            print(f"Error generating test cases for {key}:", result)
            continue
        
        # Write the generated test cases to a file
        filename = f"TestCases_{key}.java"
        with open(filename, "w") as f:
            f.write(result)
        print(f"Test cases for {key} generated successfully in {filename}")

# if __name__ == "__main__":
#     main()


obj=GenerateTestCasesJQF()

f1="E:/FYP/UT_Master/TestValGen/JQF-wsl/java-fuzzing-example/fuzz-results/plot_data"
f2="E:/FYP/UT_Master/TestValGen/JQF-wsl/java-fuzzing-example/fuzz-results/fuzz.log"

f3="""public class WaterUsage {
    public static double calculateWaterUsage(int familyMembers, int appliances, boolean hasGarden, int dailyUseLiters) {
        double baseUsage = familyMembers * dailyUseLiters;
        if (hasGarden) {
            baseUsage += 50; // Additional for garden
        }
        if (appliances > 0) {
            baseUsage += appliances * 10; // Additional for appliances
        }
        if (familyMembers > 5) {
            baseUsage *= 1.1; // Slight increase for larger families
        }
        return baseUsage;
    }

}"""
f4="gemini-1.5-flash"

result=obj.gen_TC_Gemini(f1,f2,f3,f4)
print(result)
# with open("E:/FYP/UT_Master/TestValGen/JQF-wsl/java-fuzzing-example/fuzz-results/plot_data", "r") as f:
#     plot_data = f.read()

# with open("E:/FYP/UT_Master/TestValGen/JQF-wsl/java-fuzzing-example/fuzz-results/fuzz.log", "r") as f:
#     fuzz_log = f.read()

# print(fuzz_log)