import os
import json 

def check_json_validity(json_file_path):
    try:
        # Open the JSON file and load its content
        with open(json_file_path, 'r') as file:
            data = json.load(file)
        # If we get here, the JSON is valid
        return True  # Valid JSON
    except json.JSONDecodeError as e:
        # If there's a JSON decode error, print the error and return False
        print(f"Invalid JSON. Error: {json_file_path}")
        return False  # Invalid JSON
    except Exception as e:
        # Catch other potential errors (e.g., file not found)
        print(f"An error occurred: {json_file_path}")
        return False

def modify_json_file_as_text(file_path, word_to_replace, replacement_word):
    try:
        # Open the file in read mode
        with open(file_path, 'r', encoding='utf-8') as file:
            data = file.read()  # Read the entire content as a string
        
        # Replace the word in the file content (not worrying about JSON structure)
        modified_data = data.replace(word_to_replace, replacement_word)
        
        # Write the modified content back to the file
        with open(file_path, 'w', encoding='utf-8') as file:
            file.write(modified_data)
        
        #print(f"File '{file_path}' modified successfully.")
    
    except Exception as e:
        print(f"Error processing {file_path}: {e}")

# Example usage:
directory_path = '.'  # Set the directory path
word_to_replace = '\'flying\''  # The word to be replaced
replacement_word = 'Type.FLYING'  # The word to replace with The word you want to replace it with

# Modify all JSON files in the directory
for root, dirs, files in os.walk(directory_path):
    for file in files:
        if file.endswith('.json'):  # Check if the file is a JSON file
            file_path = os.path.join(root, file)
            modify_json_file_as_text(file_path, word_to_replace, replacement_word)   
            #check_json_validity(file_path)




