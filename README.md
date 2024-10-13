# Input Integration

Handles data from different sources, checks that it meets required standards, and outputs it in a consistent format for other systems to use.

## Requirements

- Makefile
- Java SDK 21
- Maven
- Docker

## How to run

1. Set enviournment variables

    ```bash
    cp .env-example .env
    ```

2. Start the database

    ```bash
    make db-build
    ```

3. Run the following command to run the solution

    ```bash
    make start
    ```

### Structure

├── input (input files)

├── output (processed input files)

├── migrations (SQL to generate Database structure)

├── scripts (SQL scripts)

├── src/main/kotlin/karmoalteberg

│&emsp;├── [Main.kt](./src/main/kotlin/com/karmoalteberg/Main.kt) (Main executable)

│&emsp;├── [Env.kt](./src/main/kotlin/com/karmoalteberg/Env.kt) (Enviournment variables fetcher)

│&emsp;├── models/config/*.kt (TODO: Describe)

│&emsp;├── models/output/*.kt (Output DTOs)

│&emsp;├── models/builder/*.kt (Builds input data to valid, uses validators)

│&emsp;├── models/generator/*.kt (Value generation logic)

│&emsp;├── [models/service/EmployeeAction.kt](./src/main/kotlin/com/karmoalteberg/service/EmployeeAction.kt) (Handles data manipulation and using builder to validate data suitability. Responsible for checking the existance of data, not the validity of it)

│&emsp;├── models/transformer/*.kt (Transformed scoped logic)

│&emsp;├── models/adapter/**/*.kt (Contains logic to fetch and send data)

├── src/test/kotlin/karmoalteberg - Tests

### TODO

&#x2610; Write functional tests

&#x2610; Dockerise

&#x2610; insert_or_update_employees to use transactions

&#x2610; Tests written more clearly and split into parts (1 test checking one scenario)

## Tests

### How to run tests

```bash
make start
```

### Test results

After tests are run, the test reports can be found in ./target/surefire-reports directory.

### Writing tests

Aiming to write black box tests, where only input and output should be known.
To achieve this, tests are written with the minimal amount of mocking, aiming to mock only outsourcing requests, like database connections ([example](./src/test/kotlin/com/karmoalteberg/DatabaseMock.kt)).

### Added out of scope dependecies

- Kotlin CSV

    Was added to not need to handle edge cases of importing data from CSV files, which is not the scope of the task. Example the use of "," as text.

- dataframe-jdbc and mariadb-java-client

    Needed to communicate with MariaDB database

- dotenv-kotlin

    Used to get enviournment variables for .env file

- mockito-core

    Used by tests to mock third party communication (example [DatabaseMock](./src/test/kotlin/com/karmoalteberg/DatabaseMock.kt))

## Mapping

| Field                                     | User Input                                |
| ----------------------------------------- | ----------------------------------------- |
| Action                                    | ACTION                                    |
| EmployeeCode                              | contract_workerId                         |
| (Person) Id                               | Generated                                 |
| (Person) Gender                           | worker_gender                             |
| (Person) Birthdate                        | worker_personalCode (First 6 digits)      |
| (Person) HireDate                         | contract_workStartDate                    |
| (Person) TerminationDate                  | contract_signatureDate                    |
| (Salary) PayComponent.amount              | pay_amount                                |
| (Salary) PayComponent.currency            | pay_currency                              |
| (Salary) PayComponent.startDate           | pay_effectiveFrom                         |
| (Salary) PayComponent.endDate             | pay_effectiveTo                           |
| (Compensation) PayComponent.amount        | compensation_amount                       |
| (Compensation) PayComponent.currency      | compensation_currency                     |
| (Compensation) PayComponent.startDate     | pay_currecompensation_effectiveFromncy    |
| (Compensation) PayComponent.endDate       | compensation_effectiveTo                  |

## Errors

Errors are return as a trace to its failure. One row might have multiple reasons for failure and those could be connected. Each row is represented by its systemId in the prefix of the error message.

## SQL scripts

├── src/scripts

│&emsp;├── [outputInsertToDb.sql](./scripts/outputInsertToDb.sql) (Example of inserting output payload to database)

│&emsp;├── [peopleSalaries.sql](./scripts/peopleSalaries.sql) (Example of showing people alongside their salaries)

## Questions

- Why is there a need for contractStart/End/StartedAt. They are mentioned, such as terminate being assumed to be ContractCreatedAt, but unsure where it is being used and it should not be shown in the output. Definitely does not belong in the output as of now
- What is Data supposed to be? As of right now, I am using it as all the existing fields to be placed there.
Is the given mapping correct?
- Does Date format need to be consistant throughout the row, throughout the entire CSV file or in case of multiple files, across all csv files?
- Is Person data only mandatory for HIRE action? Should Hire action not include person table data if for example "full name" is missing?
- "(optional) bonus points for an SQL script that inserts the same data as the hypothetical
REST API would based on the JSON." Am I correct to assume that this SQL script takes in the output payload JSON and from that alone adds the values to the database? Aka SQL that takes in the output JSON and knows to how to set take values from it and put them to the database?
