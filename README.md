Unit Test Master is a tool that automatically generates unit tests for Java functions using symbolic execution and fuzz testing. It integrates tools like Symbolic PathFinder (SPF) and JQF+Zest to generate meaningful test cases from Java code.

Getting Started
Prerequisites
Before you begin, make sure you have the following installed:

Docker Desktop: You can download it from https://www.docker.com/products/docker-desktop

Installation
To get started with the project, follow these steps:

Clone the repository:


git clone https://github.com/your-username/unit-test-master.git
cd unit-test-master
Build and run the project using Docker Compose:
docker-compose up --build
This will build all necessary Docker images and start the required services.

Project Structure
spf/ – Contains the setup and configuration for Symbolic PathFinder.

jqf-zest/ – Contains the components related to JQF and Zest fuzzing.

scripts/ – Includes helper scripts for processing outputs.

data/ – Includes datasets and Java functions used for testing.

Important Notes
Make sure Docker Desktop is running before executing the docker-compose command.

The project is designed to work with Java 8 code without external dependencies.

Results from JQF+Zest may be output in binary format and might need to be parsed manually.

License
This project is licensed under the MIT License.
