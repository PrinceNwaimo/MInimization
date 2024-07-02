#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Function to compile and test Java code using Maven
compile_and_test_java() {
  echo "Compiling Java code..."
  mvn clean compile

  echo "Running Java unit tests..."
  mvn test
}

# Function to run Python unit tests using unittest
test_python() {
  echo "Running Python unit tests..."
  python -m unittest discover -s tests
}

# Function to compile and test Node.js code using npm
compile_and_test_nodejs() {
  echo "Installing Node.js dependencies..."
  npm install

  echo "Running Node.js unit tests..."
  npm test
}

# Detect the language based on the presence of specific files
if [ -f "pom.xml" ]; then
  # Java project
  compile_and_test_java
elif [ -f "package.json" ]; then
  # Node.js project
  compile_and_test_nodejs
elif [ -f "requirements.txt" ] || [ -d "tests" ]; then
  # Python project
  test_python
else
  echo "Unsupported project type or missing configuration files."
  exit 1
fi

# Check if tests passed
if [ $? -eq 0 ]; then
  echo "All unit tests passed successfully."
else
  echo "Some unit tests failed."
  exit 1
fi

