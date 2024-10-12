# Input Integration

Handles data from different sources, checks that it meets required standards, and outputs it in a consistent format for other systems to use.

## Requirements

- Makefile
- Java SDK 21
- Maven

## How to run

Run the following command to run the solution

```bash
make start
```

### Structure

├── input (input files)

├── output (processed input files)

├── migrations (SQL to generate Database structure)

├── scripts (SQL scripts)

├── src/main/kotlin/karmoalteberg

│&emsp;├── Main.kt (Main executable)

│&emsp;├── models/config/*.kt (TODO: Describe)

│&emsp;├── models/output/*.kt (Output DTOs)

│&emsp;├── models/builder/*.kt (Builds input data to valid, uses validators)

│&emsp;├── models/generator/*.kt (Value generation logic)

│&emsp;├── models/service/EmployeeAction.kt (Handles data manipulation and using builder to validate data suitability. Responsible for checking the existance of data, not the validity of it)

│&emsp;├── models/transformer/*.kt (Transformed scoped logic)

│&emsp;├── models/adapter (Contains logic to fetch and send data)

├── src/test/kotlin/karmoalteberg - Tests

### TODO

&#x2610; Write functional tests

&#x2610; Dockerise

&#x2610; Database

## Test related notes

### Added out of scope dependecies

- Kotlin CSV

    Was added to not need to handle edge cases of importing data from CSV files, which is not the scope of the task. Example the use of "," as text.

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

## Questions

- Why is there a need for contractStart/End/StartedAt. They are mentioned, such as terminate being assumed to be ContractCreatedAt, but unsure where it is being used and it should not be shown in the output. Definitely does not belong in the output as of now
- What is Data supposed to be? As of right now, I am using it as all the existing fields to be placed there.
Is the given mapping correct?
- Does Date format need to be consistant throughout the row, throughout the entire CSV file or in case of multiple files, across all csv files?
