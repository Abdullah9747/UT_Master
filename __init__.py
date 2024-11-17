import re
import json
import os
import csv
import pandas as pd
import random
import string
from concurrent.futures import ThreadPoolExecutor, as_completed


class DataFilter:
    def __init__(self):
        pass
    def select_random_data(self, input_file, output_file, percentage):
    
        df = pd.read_csv(input_file)
        unique_params = df['Parameter'].unique()
        selected_rows = []

        # For each unique parameter, select a percentage of rows
        for param in unique_params:
            param_group = df[df['Parameter'] == param]
            selected_group = param_group.sample(frac=percentage)
            selected_rows.append(selected_group)

        # Combine the selected rows into a new DataFrame
        result_df = pd.concat(selected_rows)

        # Save the result to a new CSV file
        result_df.to_csv(output_file, index=False)
        print(f"Fair selection completed! Selected data saved to {output_file}")

    def is_predefined_type_or_zero_params(self, parameter, filter_parameters):
        # Regex to match parameter list, like (int idx1, String name)
        pattern = r'^\(([\w\[\],\s]*)\)$'
        match = re.match(pattern, parameter)

        if match:
            types = match.group(1).split(',')
            for t in types:
                param_type = t.strip().split(' ')[0]  # Extract only the type
                if param_type not in filter_parameters:
                    return False
            return True
        return False

    def is_compilable_method(self,method_body):
        # Check for balanced curly braces and parentheses
        braces = method_body.count('{') == method_body.count('}')
        parentheses = method_body.count('(') == method_body.count(')')

        return braces and parentheses

    # Function to process each file and extract required data
    def process_file(self,file_path, filter_parameters):
        try:
            with open(file_path, 'r') as json_file:
                data = json.load(json_file)

                if 'focal_method' in data:
                    focal_method = data['focal_method']
                    parameters = focal_method.get('parameters', '')
                    method_body = focal_method.get('body', '')
                    # Wrap the method body in a class to avoid syntax errors
                    method_body = f"class {focal_method.get('identifier', '')} {{\n{method_body}\n}}"
                    test_case_body = data.get('test_case', {}).get('body', '')

                    # Filter parameters for predefined data types or zero parameters
                    if self.is_predefined_type_or_zero_params(parameters,filter_parameters) and self.is_compilable_method(method_body):
                        return parameters, method_body, test_case_body
        except Exception as e:
            print(f"Error processing file {file_path}: {e}")

        return None
    
    def process_and_filter_files(self,input_folder, output_file, filter_parameters):
        os.makedirs(os.path.dirname(output_file), exist_ok=True)

        file_paths = []
        # Collect all JSON file paths from the folder
        for root, _, files in os.walk(input_folder):
            for file in files:
                if file.endswith('.json'):
                    file_paths.append(os.path.join(root, file))
                    
        print(f"Files loading completed!")
        # Process files in parallel and write to CSV
        with ThreadPoolExecutor() as executor:
            future_to_file = {executor.submit(self.process_file, file_path, filter_parameters): file_path for file_path in file_paths}
            with open(output_file, 'w', newline='', encoding='utf-8') as csv_file:
                csv_writer = csv.writer(csv_file)
                csv_writer.writerow(['Parameter', 'Function', 'Test Case'])

                count = 0
                for future in as_completed(future_to_file):
                    result = future.result()
                    if result is not None:
                        csv_writer.writerow(result)
                        count += 1
                        print(f"Processed file: {count}")
        print(f"Data extraction and filtering completed!")
    

    def check_functionIsBuildable(sef, input_file, output_file):
        count=0
        df = pd.read_csv(input_file)
        buildable_functions = []
        for index, row in df.iterrows():
            method_body = row['Function']
            #excute the java function to check if it is buildable
            excute = os.system(f"echo '{method_body}' > temp.java")
            excute = os.system("javac temp.java")
            if excute == 0:
                print(f"added {count}Function in output file")
                count+=1
                #add crodponding parameter and test case to the buildable_functions list
                buildable_functions.append({
                    'Parameter': row['Parameter'],
                    'Function': row['Function'],
                    'Test Case': row['Test Case']
                })
        # Save the result to a new CSV file
        result_df = pd.DataFrame(buildable_functions)
        result_df.to_csv(output_file, index=False)
        print(f"Buildable functions checked! Selected data saved to {output_file}")


    def generate_random_function_name(self,length=8):
        """Generate a random function name."""
        return ''.join(random.choices(string.ascii_lowercase, k=length))

    def extract_function_signature(self,function_text):
        """Extract function signature including parameters."""
        # Find the function declaration
        match = re.search(r'public\s+\w+\s+(\w+)\s*\((.*?)\)', function_text)
        if match:
            func_name = match.group(1)
            params = match.group(2)
            return func_name, params
        return None, None

    def process_csv(self,input_file):
        """Process the CSV file using pandas and create three output files."""
        # Read the input CSV
        df = pd.read_csv(input_file)
        
        # Create lists to store processed data
        original_data = []
        random_data = []
        complete_data = []
        
        # Process each row
        for _, row in df.iterrows():
            function_text = row['Function']
            func_name, params = self.extract_function_signature(function_text)
            
            if func_name:
                random_name = self.generate_random_function_name()
                
                # Store original function name
                original_data.append({
                    'Parameter': row['Parameter'],
                    'Function Name': func_name,
                    'Test Case': row['Test Case']

                })
                
                # Store random function name with parameters
                random_data.append({
                    'Parameter': row['Parameter'],
                    'Random Function Name': f"{random_name}({params})",
                    'Test Case': row['Test Case']
                })
                
        
        # Create DataFrames from processed data
        df_original = pd.DataFrame(original_data)
        df_random = pd.DataFrame(random_data)
                
        # Save to CSV files
        df_original.to_csv('Output/original_functions.csv', index=False)
        df_random.to_csv('Output/random_functions.csv', index=False)
        
    #write a function to combine the data of three csv files into one and have all colums each are same only one time 
    def combine_csv_files(self,original_file, random_file, Selected_file, output_file):
        # Read the input CSV files
        df_original = pd.read_csv(original_file)
        df_random = pd.read_csv(random_file)
        df_Selected = pd.read_csv(Selected_file)
        
        # Combine the data by merging on Parameter
        # Read input files, keeping common columns only once
        df_original = pd.read_csv(original_file)[['Parameter', 'Function Name', 'Test Case']]
        df_random = pd.read_csv(random_file)[['Parameter', 'Random Function Name', 'Test Case']]
        df_Selected = pd.read_csv(Selected_file)[['Parameter', 'Function', 'Test Case']]

        # First merge original with random
        # Remove rows where 'Function Name' or 'Random Function Name' is NaN

        # Merge and print row count
        df_combined = pd.merge(df_original, df_random, on=['Parameter', 'Test Case'], how='inner')
        df_combined = pd.merge(df_original, df_random, on=['Parameter', 'Test Case'], how='outer')

        # Then merge with selected
        df_combined = pd.merge(df_combined, df_Selected, on=['Parameter', 'Test Case'], how='outer')

        # Reorder columns
        df_combined = df_combined[['Parameter', 'Function Name', 'Random Function Name', 'Function', 'Test Case']]

        df_combined = df_combined.dropna(subset=['Function Name', 'Random Function Name', 'Function'])
        print(f"Final rows after Everythings: {len(df_combined)}")
        # Save to a new CSV file
        df_combined.to_csv(output_file, index=False)
        print(f"Combined data saved to {output_file}")
        os.remove(original_file)
        os.remove(random_file)        
        
        