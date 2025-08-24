import pandas as pd
import re
import os

def convert_function_name_to_lowercase(java_code_string):
    """
    Finds a Java method declaration within a string of code and converts
    the method name to lowercase. It avoids changing the class name.
    
    Args:
        java_code_string (str): A string containing a Java class and method.
        
    Returns:
        str: The modified Java code with the method name in lowercase.
    """
    # Regex to find the method signature (e.g., "public boolean acceptNumParam(...)")
    # It captures three groups:
    # 1. The part before the function name (e.g., "public boolean ")
    # 2. The function name itself (e.g., "acceptNumParam")
    # 3. The part after, which is the opening parenthesis (e.g., "(")
    pattern = re.compile(r'(public\s+(?:static\s+)?\w+\s+)(\w+)(\s*\()')
    
    # Use a lambda function in re.sub to replace only the function name (group 2)
    # with its lowercase version.
    modified_code = pattern.sub(
        lambda match: match.group(1) + match.group(2).lower() + match.group(3),
        java_code_string
    )
    
    return modified_code

def process_csv(input_filepath, output_filepath):
    """
    Reads a CSV, processes the 'Functions' column to make method names lowercase,
    and saves to a new CSV.
    """
    if not os.path.exists(input_filepath):
        print(f"Error: Input file not found at '{input_filepath}'")
        return

    print(f"Reading data from '{input_filepath}'...")
    df = pd.read_csv(input_filepath)

    # Check if the 'Functions' column exists
    if 'Functions' not in df.columns:
        print("Error: 'Functions' column not found in the CSV file.")
        return

    print("Converting function names to lowercase...")
    # Apply the conversion function to each item in the 'Functions' column
    df['Functions'] = df['Functions'].apply(convert_function_name_to_lowercase)

    # Save the modified DataFrame to a new CSV file
    df.to_csv(output_filepath, index=False)
    print(f"Processing complete. Modified data saved to '{output_filepath}'")


if __name__ == "__main__":
    # --- Configuration ---
    # Change these file paths to match your input and desired output file names.
    input_csv_file = 'primitive_functions_withCC.csv'  # e.g., 'functions_data.csv'
    output_csv_file = 'lowercasefunction.csv' # e.g., 'functions_data_lowercase.csv'
    
    process_csv(input_csv_file, output_csv_file)