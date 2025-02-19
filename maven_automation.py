import os
import subprocess
import xml.etree.ElementTree as ET
import sys

def create_project_structure():
    # Create Maven standard directories
    os.makedirs('src/main/java/org/easy2excel', exist_ok=True)
    os.makedirs('src/test/java/org/easy2excel', exist_ok=True)

def write_source_files(source_code, test_code):
    # Write the provided source code to Calculator.java
    with open('src/main/java/org/easy2excel/Calculator.java', 'w') as f:
        f.write(source_code)
        
    # Write the provided JUnit test code to CalculatorTest.java
    with open('src/test/java/org/easy2excel/CalculatorTest.java', 'w') as f:
        f.write(test_code)
        
    # Write the pom.xml file with updated JUnit 5 dependencies and Surefire plugin configuration
    pom_xml = '''<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.easy2excel</groupId>
    <artifactId>Testing</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <dependencies>
        <!-- JUnit Jupiter API and Engine for JUnit 5 -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.9.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Maven Surefire Plugin configured for JUnit 5 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0-M7</version>
            </plugin>
            <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>0.8.8</version>
                <configuration>
                    <excludes>
                        <exclude>org/easy2excel/User.class</exclude>
                    </excludes>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>report</id>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>${project.build.directory}/jacoco-report</outputDirectory>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>
'''
    with open('pom.xml', 'w') as f:
        f.write(pom_xml)

def run_maven_commands():
    # Run Maven to clean, test and generate the Jacoco report.
    print("Running Maven build and tests...")
    # Use mvn.cmd on Windows; shell=True helps to resolve the batch file.
    result = subprocess.run("mvn.cmd clean test jacoco:report", shell=True, capture_output=True, text=True)
    print(result.stdout)
    if result.returncode != 0:
        print("Maven build failed:")
        print(result.stderr)
        exit(1)

def parse_coverage_report():
    """
    Simulate parsing a coverage report.
    Replace this dummy data with the actual logic to parse your coverage report file.
    """
    print("Parsing coverage report...")
    # Dummy coverage data for demonstration purposes
    coverage_data = {
        "BRANCH": {
            "percentage": 75.5,
            "covered": 15,
            "total": 20,
        },
        "LINE": {
            "percentage": 88.0,
            "covered": 44,
            "total": 50,
        }
    }
    return coverage_data

def CheckCoverage(source_code, test_code):
    # Check if command line arguments for source and test code file paths are provided.
    if len(sys.argv) >= 3:
        with open(sys.argv[1], 'r') as sf:
            source_code = sf.read()
        with open(sys.argv[2], 'r') as tf:
            test_code = tf.read()
    # Else, use the provided source_code and test_code parameters.
    
    create_project_structure()
    write_source_files(source_code, test_code)
    run_maven_commands()
    
    coverage = parse_coverage_report()
    if coverage:
        branch = coverage.get("BRANCH", {})
        line = coverage.get("LINE", {})
        return branch, line
    else:
        print("No coverage data available.")
        # Always return a tuple, even if it's (None, None)
        return None, None
