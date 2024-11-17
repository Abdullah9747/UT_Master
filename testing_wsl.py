import subprocess
import time

def run_docker_image():
    # Start Docker container with interactive session
    process = subprocess.Popen(
        ['wsl', '-e', 'bash', '-c', 'ls'],
        stdin=subprocess.PIPE,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True
    )
    return process

# def execute_docker_command(process, command):
#     # Send command to Docker container
#     process.stdin.write(f"{command}\n")
#     process.stdin.flush()
#     time.sleep(1)  # Give time for command to execute

def execute_docker_command(process, command):
    # Send command to Docker container
    process.stdin.write(f"{command}\n")
    process.stdin.flush()
    
    # Give time for command to execute
    time.sleep(1)
    
    # Read output and error streams
    output = process.stdout.readline()
    error = process.stderr.readline()
    
    return {
         output.strip(),
        error.strip()
    }

def run_maven_commands(process):
    # Example commands
    commands = [
        "ls",
    ]
    
    for cmd in commands:
        output,error=execute_docker_command(process, cmd)
        print(f"Output: {output}")
        print(f"Error: {error}")    

# Usage example:
docker_process = run_docker_image()
run_maven_commands(docker_process)




# def run_WSL(process,command):
#     """
#     Run a WSL command with a sanitized environment and return stdout and stderr.
#     """
#     try:
#         # Create a clean environment
#         clean_env = os.environ.copy()
#         clean_env.pop("PATH", None)  # Remove conflicting PATH variable
#         process.stdin.write(f"{command}\n")
#         process.stdin.flush()
#         print("sleeping")
#         time.sleep(5)
#         output = process.stdout.readline()
#         error = process.stderr.readline()
#         return output.strip(), error.strip()
#     except Exception as e:
#         return "", str(e)
    

def windows_path_to_wsl(path):
    """
    Convert a Windows path to a WSL-compatible path.
    """
    drive, rest = os.path.splitdrive(path)
    print(f"drive is {drive}")
    print(f"rest is {rest}")
    drive_letter = drive[:-1].lower()  # Extract drive letter and make lowercase
    wsl_path = f"/mnt/{drive_letter}{rest.replace('\\', '/')}"
    return wsl_path
def run_WSL(process, command):
    """
    Run a WSL command with a sanitized environment and return stdout and stderr.
    """
    try:
        # Create a clean environment
        clean_env = os.environ.copy()
        clean_env.pop("PATH", None)  # Remove conflicting PATH variable
        
        # Write command to stdin
        process.stdin.write(f"{command}\n")
        process.stdin.flush()
        
        # Wait briefly for command to execute
        time.sleep(1)
        
        output = []
        error = []
        
        # Read with timeout using select
        readable, _, _ = select.select([process.stdout, process.stderr], [], [], 5.0)
        
        if process.stdout in readable:
            output = process.stdout.readlines()
        if process.stderr in readable:
            error = process.stderr.readlines()
            
        return ''.join(output).strip(), ''.join(error).strip()
        
    except Exception as e:
        return "", str(e)




def run_docker_image():
    # Start Docker container with interactive session
    process = subprocess.Popen(
        ['wsl', '-e', 'bash'],
        stdin=subprocess.PIPE,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True
    )
    return process

# Fetch current working directory dynamically
current_dir = os.getcwd()

# Convert the current directory to WSL-compatible format
wsl_path = windows_path_to_wsl(current_dir)
print(f"Current directory in WSL format: {wsl_path}")

# Debug: Check if the WSL directory exists
command = f"ls"
process=run_docker_image()
stdout, stderr = run_WSL(process,command)

print("STDOUT:")
print(stdout)
print("\nSTDERR:")
print(stderr)
