# Train Billing System

## Description

Train billing system generates a billing file from a tap file.

Examples of both formats are provided here:
- [Example Tap File](resources%2FCandidateInputExample.txt)
- [Example Billing File](resources%2FCandidateOutputExample.txt)

## Compile and Run Tests

Train billing system is built with Maven:
- compile with: <code>mvn clean install</code>
- run tests with: <code>mvn test</code>

## Run the application

Train billing system is a java CLI application which needs two parameters provided in this order:
- the name of the tap file to be read
- the name of the billing file to be written

You can use the run configuration for intellij provided here
- [MainApplication.run.xml](runConfigurations%2FMainApplication.run.xml)