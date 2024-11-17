import subprocess
import os

# Define the Docker command
docker_command = (
    "docker run -v %cd%:/app -it maven:3.6.1-jdk-12 /bin/bash -c "
    "\"cd /app && java -jar JQF/fuzz/target/jqf-fuzz-2.1-SNAPSHOT-zest-cli.jar "
    "-e target/example-java-1.0-SNAPSHOT-fat-tests.jar dev.fuzzit.examplejava.ParseComplexTest fuzz\""
)

# Define the project directory
project_dir = r"JQF-wsl\java-fuzzing-example"


try:
    # Change to the project directory
    os.chdir(project_dir)
    print("Changed directory to:", project_dir)

    # Print the Docker command for debugging
    print("Docker command:", docker_command)

    # Run the Docker command and capture output
    result = subprocess.run(
        docker_command,
        shell=True,
        check=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE
    )

    # Print output from the Docker command
    print("Docker command output:")
    print(result.stdout.decode())

except FileNotFoundError:
    print(f"Error: The directory {project_dir} does not exist.")
except subprocess.CalledProcessError as e:
    print("Error running Docker command:")
    print("Stdout:", e.stdout.decode())
    print("Stderr:", e.stderr.decode())
