from testing import GenerateTestCasesJQF as GTCJQF
from testing import GenerateTestCasesSPF as GTCSPF
from testing import GenerateTestCasesLLM as GTCLLM
import testing
import pandas as pd
import re
import time # Import the time module
import pandas as pd

llm_spf=GTCSPF()
llm_jqf=GTCJQF()
llm_llm=GTCLLM()

def script_LLM(func):
    javadoc, signature, body, full_method, class_name=testing.extract_java_method_parts(func)
    print("Full method:", full_method)
    print("Signature:", signature)
    print("Class:", class_name)

    result=llm_llm.gen_TC_Gemini(signature, "gemini-2.0-flash", javadoc, body,class_name,"Statement")
    while "429 You exceeded your current quota" in str(result):
        time.sleep(60)
        result=llm_llm.gen_TC_Gemini(signature, "gemini-2.0-flash", javadoc, body,class_name,"Statement")
    result2=llm_llm.gen_TC_Gemini(signature, "gemini-2.0-flash", javadoc, body,class_name,"Branch")
    while "429 You exceeded your current quota" in str(result2):
        time.sleep(60)
        result2=llm_llm.gen_TC_Gemini(signature, "gemini-2.0-flash", javadoc, body,class_name,"Branch")
    result3=llm_llm.gen_TC_Gemini(signature, "gemini-2.0-flash", "", "",class_name,"Statement")
    while "429 You exceeded your current quota" in str(result3):
        time.sleep(60)
        result3=llm_llm.gen_TC_Gemini(signature, "gemini-2.0-flash", "", "",class_name,"Statement")

    result4=llm_llm.gen_TC_Gemini(signature, "gemini-2.0-flash", "", "",class_name,"Branch")
    while "429 You exceeded your current quota" in str(result4):
        time.sleep(60)
        result4=llm_llm.gen_TC_Gemini(signature, "gemini-2.0-flash", "", "",class_name,"Branch")

    result5=llm_llm.gen_TC_Gemini(signature, "gemini-2.0-flash", javadoc, "",class_name,"Statement")
    while "429 You exceeded your current quota" in str(result5):
        time.sleep(60)
        result5=llm_llm.gen_TC_Gemini(signature, "gemini-2.0-flash", javadoc, "",class_name,"Statement")

    result6=llm_llm.gen_TC_Gemini(signature, "gemini-2.0-flash", javadoc, "",class_name,"Branch")
    while "429 You exceeded your current quota" in str(result6):
        time.sleep(60)
        result6=llm_llm.gen_TC_Gemini(signature, "gemini-2.0-flash", javadoc, "",class_name,"Branch")
    return result,result2,result3,result4,result5,result6

def script_SPF(func,test_values):
    javadoc, signature, body, full_method, class_name=testing.extract_java_method_parts(func)
    result=llm_spf.gen_TC_Gemini(signature, test_values, "Gemini-2.0-flash")
    return result

def script_JQF(func,test_values):
    javadoc, signature, body, full_method, class_name=testing.extract_java_method_parts(func)
    result=llm_jqf.gen_TC_Gemini(test_values, func, "gemini-2.0-flash", javadoc, body, class_name)
    return result



def process_functions_with_llm(input_csv: str,
                               output_csv: str = "llm_processed_results.csv",
                               limit: int | None = None,
                               checkpoint_every: int = 5):
    """
    Read functions from a CSV (expects a 'Function' column), run script_LLM on each,
    and save results with columns: Class_Name, Function, LLM_Result.
    """
    df = pd.read_csv(input_csv)

    if limit is not None:
        df = df.head(limit)

    results = []
    for idx, func_code in enumerate(df["Function Code"]):
        javadoc, signature, body, full_method, class_name = testing.extract_java_method_parts(func_code)
        print(f"[{idx}] Processing {class_name}")

        llm_result1, llm_result2, llm_result3, llm_result4, llm_result5, llm_result6 = script_LLM(func_code)
        print(llm_result1)
        print(llm_result2)
        ff=0
       
        llm_result1, llm_result2, llm_result3, llm_result4, llm_result5, llm_result6 = script_LLM(func_code)

        results.append({
            "Class_Name": class_name,
            "Function": func_code,
            "LLM_Result_White_Box_Statement": llm_result1,
            "LLM_Result_White_Box_Branch": llm_result2,
            "LLM_Black_Box_Result_Statement": llm_result3,
            "LLM_Black_Box_Result_Branch": llm_result4,
            "LLM_Gray_Box_Result_Statement": llm_result5,
            "LLM_Gray_Box_Result_Branch": llm_result6
            
        })

        if checkpoint_every and len(results) % checkpoint_every == 0:
            pd.DataFrame(results).to_csv(output_csv, index=False)
            print(f"Checkpoint saved ({len(results)})")

    out_df = pd.DataFrame(results)
    out_df.to_csv(output_csv, index=False)
    print(f"Saved {len(out_df)} rows to {output_csv}")
    return

def process_functions_with_spf(input_csv: str,
                               output_csv: str = "spf_processed_results.csv",
                               limit: int | None = None,
                               checkpoint_every: int = 5):
    """
    Read functions and SPF results from CSV, run script_SPF on each,
    and save results with columns: Class_Name, Function, SPF_Test_Values, LLM_Result.
    """
    try:
        df = pd.read_csv(input_csv)
    except Exception as e:
        print(f"Failed to read {input_csv}: {e}")
        return None

    required_cols = ["Function", "SPF_Results"]
    missing_cols = [col for col in required_cols if col not in df.columns]
    if missing_cols:
        print(f"Missing columns: {missing_cols}")
        print(f"Available columns: {df.columns.tolist()}")
        return None

    if limit is not None:
        df = df.head(limit)

    results = []
    for idx, row in df.iterrows():
        func_code = row["Function Code"]
        spf_results = row["SPF_Results"]
        
        # Skip if function or SPF results are empty
        if not isinstance(func_code, str) or not func_code.strip():
            print(f"[{idx}] Skipping empty function")
            continue
            
        if not isinstance(spf_results, str) or not spf_results.strip():
            print(f"[{idx}] Skipping empty SPF results")
            continue

        # Extract class name
        try:
            javadoc, signature, body, full_method, class_name = testing.extract_java_method_parts(func_code)
        except Exception as e:
            print(f"[{idx}] Parse error: {e}")
            class_name = "UnknownClass"

        if not class_name:
            m = re.search(r'class\s+(\w+)', func_code)
            class_name = m.group(1) if m else "UnknownClass"

        print(f"[{idx}] Processing {class_name} with SPF results")
        
        try:
            llm_result = script_SPF(func_code, spf_results)
        except Exception as e:
            llm_result = f"ERROR: {e}"
            print(f"[{idx}] SPF processing error: {e}")

        results.append({
            "Class_Name": class_name,
            "Function": func_code,
            "SPF_Test_Values": spf_results,
            "LLM_Result": llm_result
        })

        if checkpoint_every and len(results) % checkpoint_every == 0:
            pd.DataFrame(results).to_csv(output_csv, index=False)
            print(f"Checkpoint saved ({len(results)} rows)")

    out_df = pd.DataFrame(results)
    out_df.to_csv(output_csv, index=False)
    print(f"Saved {len(out_df)} rows to {output_csv}")
    return out_df

def process_functions_with_jqf(input_csv: str,
                               output_csv: str = "jqf_processed_results.csv",
                               limit: int | None = None,
                               checkpoint_every: int = 5):
    """
    Read functions and JQF results from CSV, combine valid and failure values,
    run script_JQF on each, and save results with columns: Class_Name, Function, JQF_Test_Values, LLM_Result.
    """
    try:
        df = pd.read_csv(input_csv)
    except Exception as e:
        print(f"Failed to read {input_csv}: {e}")
        return None

    required_cols = ["Function", "JQF Valid", "JQF Failures"]
    missing_cols = [col for col in required_cols if col not in df.columns]
    if missing_cols:
        print(f"Missing columns: {missing_cols}")
        print(f"Available columns: {df.columns.tolist()}")
        return None

    if limit is not None:
        df = df.head(limit)

    results = []
    for idx, row in df.iterrows():
        func_code = row["Function Code"]
        jqf_valid = row["JQF Valid"]
        jqf_failures = row["JQF Failures"]
        
        # Skip if function is empty
        if not isinstance(func_code, str) or not func_code.strip():
            print(f"[{idx}] Skipping empty function")
            continue
        
        # Combine JQF valid and failure values
        combined_jqf_values = []
        
        # Parse JQF valid values
        if isinstance(jqf_valid, str) and jqf_valid.strip() and jqf_valid not in ["No match found", "N/A", ""]:
            try:
                if jqf_valid.startswith('[') and jqf_valid.endswith(']'):
                    import ast
                    valid_values = ast.literal_eval(jqf_valid)
                    if isinstance(valid_values, list):
                        combined_jqf_values.extend(valid_values)
                else:
                    combined_jqf_values.append(jqf_valid)
            except Exception as e:
                print(f"[{idx}] Error parsing JQF valid values: {e}")
        
        # Parse JQF failure values
        if isinstance(jqf_failures, str) and jqf_failures.strip() and jqf_failures not in ["No match found", "N/A", ""]:
            try:
                if jqf_failures.startswith('[') and jqf_failures.endswith(']'):
                    import ast
                    failure_values = ast.literal_eval(jqf_failures)
                    if isinstance(failure_values, list):
                        combined_jqf_values.extend(failure_values)
                else:
                    combined_jqf_values.append(jqf_failures)
            except Exception as e:
                print(f"[{idx}] Error parsing JQF failure values: {e}")
        
        # Skip if no JQF values found
        if not combined_jqf_values:
            print(f"[{idx}] Skipping - no JQF test values found")
            continue

        # Extract class name
        try:
            javadoc, signature, body, full_method, class_name = testing.extract_java_method_parts(func_code)
        except Exception as e:
            print(f"[{idx}] Parse error: {e}")
            class_name = "UnknownClass"

        if not class_name:
            m = re.search(r'class\s+(\w+)', func_code)
            class_name = m.group(1) if m else "UnknownClass"

        # Convert combined values to string format for script_JQF
        jqf_test_values = str(combined_jqf_values)
        
        print(f"[{idx}] Processing {class_name} with JQF results")
        
        try:
            llm_result = script_JQF(func_code, jqf_test_values)
        except Exception as e:
            llm_result = f"ERROR: {e}"
            print(f"[{idx}] JQF processing error: {e}")

        results.append({
            "Class_Name": class_name,
            "Function": func_code,
            "JQF_Test_Values": jqf_test_values,
            "LLM_Result": llm_result
        })

        if checkpoint_every and len(results) % checkpoint_every == 0:
            pd.DataFrame(results).to_csv(output_csv, index=False)
            print(f"Checkpoint saved ({len(results)} rows)")

    out_df = pd.DataFrame(results)
    out_df.to_csv(output_csv, index=False)
    print(f"Saved {len(out_df)} rows to {output_csv}")
    return out_df

#process_functions_with_jqf("detailed_combined_results.csv", "a_jqf_process_res.csv")

#process_functions_with_spf("detailed_combined_results.csv", "a_spf_process_res.csv")

process_functions_with_llm("detailed_combined_results.csv","a_llm_process_res.csv",999999,2)