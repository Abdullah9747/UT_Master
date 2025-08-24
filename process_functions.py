import csv
import re

def convert_private_to_public(java_code):
    """
    Convert private method declarations to public in Java code.
    """
    # Pattern to match private method declarations
    # This matches variations like:
    # - private static type methodName
    # - private type methodName
    # - private final type methodName
    # etc.
    pattern = r'\bprivate\b'
    
    # Replace 'private' with 'public'
    converted_code = re.sub(pattern, 'public', java_code)
    
    return converted_code

def normalize_function_for_comparison(java_code):
    """
    Normalize function code for duplicate comparison by removing extra whitespace.
    """
    # Remove extra whitespace and normalize line breaks
    normalized = re.sub(r'\s+', ' ', java_code.strip())
    return normalized

def process_csv_file(input_file, output_file):
    """
    Process the CSV file and convert private methods to public, removing duplicates.
    """
    with open(input_file, 'r', encoding='utf-8') as infile:
        # Use csv.QUOTE_ALL to handle the complex quoted content
        reader = csv.reader(infile, quoting=csv.QUOTE_ALL)
        
        # Read the header
        header = next(reader)
        
        # Prepare output data
        output_rows = [header]
        seen_functions = set()  # Track seen functions to remove duplicates
        duplicate_count = 0
        
        for row in reader:
            if len(row) >= 1:  # Make sure we have at least the Function column
                # Convert the function code (first column)
                original_function = row[0]
                converted_function = convert_private_to_public(original_function)
                
                # Normalize for duplicate checking
                normalized_function = normalize_function_for_comparison(converted_function)
                
                # Check if we've seen this function before
                if normalized_function not in seen_functions:
                    seen_functions.add(normalized_function)
                    # Create new row with converted function
                    new_row = [converted_function] + row[1:]
                    output_rows.append(new_row)
                else:
                    duplicate_count += 1
            else:
                # Keep the row as is if it doesn't have the expected structure
                output_rows.append(row)
    
    # Write the converted data to output file
    with open(output_file, 'w', encoding='utf-8', newline='') as outfile:
        writer = csv.writer(outfile, quoting=csv.QUOTE_ALL)
        writer.writerows(output_rows)
    
    return duplicate_count

def main():
    input_file = 'final_normalized.csv'  # Your input file
    output_file = 'final_normalized_public.csv'  # Output file with public methods
    
    try:
        duplicate_count = process_csv_file(input_file, output_file)
        print(f"Conversion completed successfully!")
        print(f"Input file: {input_file}")
        print(f"Output file: {output_file}")
        print(f"Duplicates removed: {duplicate_count}")
        
        # Count lines in both files to show the difference
        with open(input_file, 'r', encoding='utf-8') as f:
            input_lines = sum(1 for _ in f) - 1  # Subtract header
        
        with open(output_file, 'r', encoding='utf-8') as f:
            output_lines = sum(1 for _ in f) - 1  # Subtract header
        
        print(f"Original functions: {input_lines}")
        print(f"Unique functions after conversion: {output_lines}")
        
        # Show a sample of what was converted
        print("\nSample conversion:")
        sample_private = "private static boolean isvalid(long sValue)"
        sample_public = convert_private_to_public(sample_private)
        print(f"Before: {sample_private}")
        print(f"After:  {sample_public}")
        
    except FileNotFoundError:
        print(f"Error: Could not find the input file '{input_file}'")
    except Exception as e:
        print(f"Error processing file: {str(e)}")

if __name__ == "__main__":
    main()