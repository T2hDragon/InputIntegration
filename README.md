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

├── src/main/kotlin/karmoalteberg

│&emsp;├── Main.kt (Main executable)

│&emsp;├── models/output/*.kt (Output DTOs)

├── src/test/kotlin/karmoalteberg - Tests

### TODO

&#x2610; Write functional tests

&#x2610; Dockerise

&#x2610; Database
