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

│&emsp;├── models/service/*.kt (Business logic)

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
