from jqf import JQF
from spf import SPF
from LLMs import GenerateTestCasesJQF as GTCJQF
from LLMs import GenerateTestCasesSPF as GTCSPF
from LLMs import GenerateTestCasesLLM as GTCLLM
import pandas as pd
import re
import time # Import the time module
class Driver:
    def __init__(self):
        self.jqf=JQF()
        self.spf=SPF()
        self.GTCJQF=GTCJQF()
        self.GTCSPF=GTCSPF()
        self.GTCLLM=GTCLLM()

    def run_jqf(self,function_code):
        self.jqf.driverjqf(function_code)
        f1="E:/FYP/UT_Master/TestValGen/JQF-wsl/java-fuzzing-example/fuzz-results/plot_data"
        f2="E:/FYP/UT_Master/TestValGen/JQF-wsl/java-fuzzing-example/fuzz-results/fuzz.log"
        f4="gemini-1.5-flash"
        result=self.GTCJQF.gen_TC_Gemini(f1,f2,function_code,f4)
        print(result)


    def run_spf(self,result,function):
        #result=self.spf.driverspf(function)
        f4="gemini-1.5-flash"
        fres=self.GTCSPF.gen_TC_Gemini(result,function,f4)
        #put the result into the first c
        return fres
    
    def run_LLM(self,function):
        f4="gemini-1.5-flash"
        result=self.GTCLLM.driver_LLM(function,f4)
        print(result)
    def run_llm_ds(self,function):
        results=self.GTCLLM.gen_TC_DS(function)
        print(results)


obj=Driver()


function3="""
public class GETMINRATING {
/**
     * Obtains the min rating of the length sum of the 2 names. In essence the larger the sum length the smaller the
     * min rating. Values strictly from documentation.
     *
     * <h2>API Usage</h2>
     * <p>
     * Consider this method public, it is package public for unit testing only.
     * </p>
     *
     * @param sumLength
     *            The length of 2 strings sent down.
     * @return The min rating value.
     */
    int getMinRating(final int sumLength) {
        int minRating = 0;

        if (sumLength <= 4) {
            minRating = 5;
        } else if (sumLength <= 7) { // already know it is at least 5
            minRating = 4;
        } else if (sumLength <= 11) { // already know it is at least 8
            minRating = 3;
        } else if (sumLength == 12) {
            minRating = 2;
        } else {
            minRating = 1; // docs said little here.
        }

        return minRating;
    }
}


"""
def standardize_function_names(function_code):
    """
    Changes the class name to 'Distance' and method name to 'distance' in Java code.
    
    Args:
        function_code (str): The Java function code as a string
        
    Returns:
        str: Modified Java code with standardized names
    """
    # Replace class name with 'Distance'
    modified_code = re.sub(r'(public\s+class\s+)\w+(\s*\{)', r'\1Distance\2', function_code)
    
    # Replace method name - only target actual method declarations
    # Look for method signature pattern: access_modifier static return_type methodName(params) {
    modified_code = re.sub(
        r'(\s+(?:public|private|protected)\s+(?:static\s+)?\w+\s+)\w+(\s*\([^)]*\)\s*\{)',
        r'\1distance\2',
        modified_code
    )
    
    # Handle methods without explicit access modifiers
    modified_code = re.sub(
        r'(\s+static\s+\w+\s+)\w+(\s*\([^)]*\)\s*\{)',
        r'\1distance\2',
        modified_code
    )
    
    return modified_code





function2="""public class WaterUsage {
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
#implement timer start here

# start_time = time.time()
# obj.run_llm_ds(function3)
# end_time = time.time()
# duration = end_time - start_time
# print(f"Execution time: {duration} seconds")
# driver = Driver()
# print(function3)
# driver.spf.driverspf(function3)
# # List of problematic function names
# # Example blacklist: note that these entries should match the extracted pattern (e.g. "class IsPrime")
blacklist = {
    "Nextpowoftwo"
}
#     "class IsPrime",
#     "class GetColumnName",
#     "class Add",
#     "class Mul",
#     "class Sleep",
#     "class Wrap",
#     "class WrapDouble",
#     "class QuantityToRSStackSize",
#     "class Sqrt",
#     "class ToString",
#     "class NormalQuantile",
#     "class ConvertBase",
#     "class FullHouse",
#     "class SmallStraight",
#     "class LargeStraight"
# }
# v=0
# blacklist={}
# df = pd.read_csv("test2.csv")
# print("Total Functions:", len(df))
# driver = Driver()  # assuming your Driver class is already imported/defined

# # Add this before the for loop to open the error log file
# error_log_file = open("spf_all_errors_z3_botvector.txt", "w", encoding="utf-8")
# error_log_file.write("SPF Error Log\n")
# error_log_file.write("=" * 50 + "\n\n")

# for index, row in df.iterrows():
#     # You can skip rows by index if needed
#     # if index > 10:
#     #     break
#     # if index < 137:
#     #     continue
#     if index==4 or index==25 or index==36:
#         continue
#     # if index==4 or index==49 or index==139 or index==120:
#     #     continue
#     # if index==117 or index==408 or index==426 or index==553 or index==816 or index==817:
#     #     continue

#     func_code = row["Function"]
#     # Split the function code into lines and get the first non-empty line.
#     lines = func_code.splitlines()
#     first_line = None
#     for l in lines:
#         if l.strip():
#             first_line = l.strip()
#             break
#     if not first_line:
#         print(f"No first line for row at index {index}")
#         continue  # Skip if there is no valid first line

#     # Extract the class name using regex.
#     m = re.search(r'class\s+(\w+)', first_line)
#     if m:
#         extracted_class_line = "class " + m.group(1)
#         if extracted_class_line in blacklist:
#             print(f"Skipping blacklisted function: {extracted_class_line} at index {index}")
#             continue
#         else:
#             print(f"Processing function: {extracted_class_line} at index {index}")
#             spf_result,total_time,er1,er2 = driver.spf.driverspf(standardize_function_names(row["Function"]))
            
#             # Log errors to the consolidated error file
#             error_log_file.write(f"Function: {extracted_class_line} (Index: {index})\n")
#             error_log_file.write("=" * 50 + "\n")
#             error_log_file.write("ERROR 1 (er1):\n")
#             error_log_file.write(str(er1) + "\n\n")
#             error_log_file.write("ERROR 2 (er2):\n")
#             error_log_file.write(str(er2) + "\n\n")
#             error_log_file.write("-" * 80 + "\n\n")
#             error_log_file.flush()  # Ensure data is written immediately
            
#             if spf_result != "":
#                 v=v+1
#             df.at[index, "SPF_Results"] = str(spf_result)
#             df.at[index, "SPF_Time"] = total_time
#     else:
#         print(f"Could not extract class from row at index {index}")

# # Add this after the for loop to close the error log file
# error_log_file.close()
# print("Error log saved to spf_all_errors.txt")
    

# # Save the updated DataFrame with SPF_Results back to a new CSV file.
# df.to_csv("test5_z3bitvector.csv", index=False)
# print("Updated CSV saved with SPF_Results.")


# df=pd.read_csv("primitive_functions_v1.csv")
# print("Total Functions:", len(df))

# print("Total Functions:", len(df))
# driver = Driver()  # assuming your Driver class is already imported/defined
# for index, row in df.iterrows():
#     # You can skip rows by index if needed
#     if index < 0:
#         continue


#     func_code = row["Function"]
#     # Split the function code into lines and get the first non-empty line.
#     lines = func_code.splitlines()
#     first_line = None
#     for l in lines:
#         if l.strip():
#             first_line = l.strip()
#             break
#     if not first_line:
#         print(f"No first line for row at index {index}")
#         continue  # Skip if there is no valid first line

#     # Extract the class name using regex.
#     m = re.search(r'class\s+(\w+)', first_line)
#     if m:
#         extracted_class_line = "class " + m.group(1)
#         if extracted_class_line in blacklist:
#             print(f"Skipping blacklisted function: {extracted_class_line} at index {index}")
#             continue
#         else:
#             print(f"Processing function: {extracted_class_line} at index {index}")
#             check = driver.spf.compile_check(row["Function"])
#             #if the check is true i want to keep the function in the dataframe else remove it
#             if check==True:
#                 continue
#             else:
#                 #remove that row from the dataframe
#                 df.drop(index, inplace=True)
#     else:
#         print(f"Could not extract class from row at index {index}")
    

# # Save the updated DataFrame with SPF_Results back to a new CSV file.
# df.to_csv("primitive_functions_v1_compile.csv", index=False)
# print("Updated CSV saved with SPF_Results.")