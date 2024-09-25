import re
import json
import os
import csv
import pandas as pd
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

    