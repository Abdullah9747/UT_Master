import pandas as pd
import re

# Read the main CSV files
test1_df = pd.read_csv('test1.csv')  # Choco runs
test5_df = pd.read_csv('test5_z3bitvector.csv')  # Z3 bitvector runs
error_choco_df = pd.read_csv('error_choco.csv')

# Get rows with empty SPF_Results from both files
choco_empty = test1_df[
    (test1_df['SPF_Results'].isna()) | 
    (test1_df['SPF_Results'] == '') | 
    (test1_df['SPF_Results'].astype(str).str.strip() == '')
]['class_name'].unique()

z3bitvector_empty = test5_df[
    (test5_df['SPF_Results'].isna()) | 
    (test5_df['SPF_Results'] == '') | 
    (test5_df['SPF_Results'].astype(str).str.strip() == '')
]['class_name'].unique()

# Find intersection of empty results (functions that failed in both)
common_failed = set(choco_empty).intersection(set(z3bitvector_empty))

# Create base dataframe with common failed functions
base_df = test1_df[test1_df['class_name'].isin(common_failed)][
    ['class_name', 'cyclomatic_complexity', 'javadoc_length', 'tokens', 'LOC']
].drop_duplicates().reset_index(drop=True)

# Add Choco errors
choco_errors = error_choco_df.set_index('Class Name')[['Error 1 (er1)', 'Error 2 (er2)', 'Success Status']].to_dict('index')

base_df['choco_error_1'] = base_df['class_name'].map(lambda x: choco_errors.get(x, {}).get('Error 1 (er1)', 'No error info'))
base_df['choco_error_2'] = base_df['class_name'].map(lambda x: choco_errors.get(x, {}).get('Error 2 (er2)', 'No error info'))
base_df['choco_status'] = base_df['class_name'].map(lambda x: choco_errors.get(x, {}).get('Success Status', 'Unknown'))

# Read Z3 errors from the corrected filename
try:
    z3_errors_df = pd.read_csv('errors_with_function_z3_V2.csv')
    
    # Create dictionary mapping class name to Z3 errors
    z3_error_dict = {}
    for _, row in z3_errors_df.iterrows():
        class_name = row['Class Name']
        er1 = str(row['ER1']) if pd.notna(row['ER1']) else ''
        er2 = str(row['ER2']) if pd.notna(row['ER2']) else ''
        
        # Combine ER1 and ER2 if both exist, but truncate for readability
        if er1 and er2:
            # Extract key error information
            er1_short = er1.split('at gov.nasa.jpf')[0] if 'at gov.nasa.jpf' in er1 else er1[:200]
            er2_short = er2[:100] + '...' if len(er2) > 100 else er2
            z3_error_dict[class_name] = f"ER1: {er1_short} | ER2: {er2_short}"
        elif er1:
            er1_short = er1.split('at gov.nasa.jpf')[0] if 'at gov.nasa.jpf' in er1 else er1[:200]
            z3_error_dict[class_name] = f"ER1: {er1_short}"
        elif er2:
            er2_short = er2[:100] + '...' if len(er2) > 100 else er2
            z3_error_dict[class_name] = f"ER2: {er2_short}"
        else:
            z3_error_dict[class_name] = "No specific error details"
    
    base_df['z3_error'] = base_df['class_name'].map(lambda x: z3_error_dict.get(x, 'No Z3 error info'))
    
except FileNotFoundError:
    base_df['z3_error'] = 'Z3 error file not found'
except Exception as e:
    base_df['z3_error'] = f'Error reading Z3 file: {str(e)}'

# Read Z3 bitvector errors from text file
try:
    with open('spf_all_errors_z3_botvector.txt', 'r', encoding='utf-8') as f:
        z3_bitvector_content = f.read()
    
    # Parse the text file to extract errors per class
    z3_bitvector_errors = {}
    
    # Split content into sections and look for class names
    lines = z3_bitvector_content.split('\n')
    current_class = None
    current_error = []
    
    for line in lines:
        line = line.strip()
        if not line:
            continue
            
        # Check if line contains a class name from our failed functions
        found_class = None
        for class_name in common_failed:
            if class_name in line:
                found_class = class_name
                break
        
        if found_class:
            # Save previous error if exists
            if current_class and current_error:
                # Join and truncate error for readability
                error_text = ' | '.join(current_error)
                if len(error_text) > 500:
                    error_text = error_text[:500] + '...'
                z3_bitvector_errors[current_class] = error_text
            
            # Start new class
            current_class = found_class
            current_error = [line]
        elif current_class and line:
            # Add error line to current class
            current_error.append(line)
    
    # Add the last error if exists
    if current_class and current_error:
        error_text = ' | '.join(current_error)
        if len(error_text) > 500:
            error_text = error_text[:500] + '...'
        z3_bitvector_errors[current_class] = error_text
    
    base_df['z3_bitvector_error'] = base_df['class_name'].map(lambda x: z3_bitvector_errors.get(x, 'No Z3 bitvector error info'))
    
except FileNotFoundError:
    base_df['z3_bitvector_error'] = 'Z3 bitvector error file not found'
except Exception as e:
    base_df['z3_bitvector_error'] = f'Error reading Z3 bitvector file: {str(e)}'

# Save the result
base_df.to_csv('combined_solver_errors.csv', index=False)

print(f"Found {len(common_failed)} functions that failed in both Choco and Z3 bitvector")
print(f"Functions: {sorted(list(common_failed))}")
print("Saved to combined_solver_errors.csv")
print("\nFirst few rows with errors:")
print(base_df[['class_name', 'z3_error', 'z3_bitvector_error']].head())

# Debug: Show what Z3 errors were found
print(f"\nZ3 error file contains {len(z3_errors_df)} entries")
print("Z3 errors found for common failed functions:")
for class_name in sorted(common_failed):
    if class_name in z3_error_dict:
        print(f"✓ {class_name}")
    else:
        print(f"✗ {class_name}")