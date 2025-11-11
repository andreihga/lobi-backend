# lobi-backend
This is the backend repository for Lobi. 


# Branch Naming Convention

To maintain consistency and clarity in our repository, all feature, bugfix, or task branches must follow this naming convention:

**Pattern:** `LOBI-<ticket_number>_<description>`  
**Example:** 
`LOBI-12_bug-fix`

---

## 🔹 Format Breakdown

| Part | Description | Example |
|------|-------------|---------|
| `LOBI-` | Literal prefix identifying the project | `LOBI-` |
| `<ticket_number>` | The number of the task or ticket in the issue tracker (one or more digits) | `12`, `42` |
| `_` | Underscore separating the ticket number from the description | `_` |
| `<description>` | Short, lowercase description of the branch. Words may be separated by hyphens (`-`). **Underscores are not allowed** in the description | `bug-fix`, `add-login-feature` |

---

# Local Development Setup

## Prerequisites

Before running the application locally, ensure you have the following:

1. **Docker** installed on your machine.
2. A `.env.local` file in the root directory of the project:
    - Copy the contents from `.env.example`.
    - Replace the placeholder values with the settings you want for running the app locally (database credentials, ports, etc.).

---

## Running the Application Locally

1. **Build and start the containers:**

```bash
docker compose --env-file .env.local build
docker compose --env-file .env.local up -d
```

2. **Verify the backend is running:**

After the containers are up, open your browser or use a tool like curl to check the health endpoint:

```
http://127.0.0.1:8080/actuator/lobi-backend
```

3. **Code Formatting with Spotless:**

I recommend using the Spotless plugin (**Spotless Applier**) to keep your code clean and consistent.

- Format the current file with `Ctrl+Alt+;`
- Format the entire project with `Ctrl+Alt+Shift+;`

4. **Running Integration Tests:**

Our integration tests use **Testcontainers** to spin up a temporary PostgreSQL database automatically. This ensures tests run in a clean, isolated environment without affecting your local database.

- Make sure Docker is running on your machine.
- Run the tests through your IDE or with Gradle:

```bash
./gradlew test
```

Testcontainers will automatically:
- Pull the required image.
- Start a container.
- Inject the JDBC connection details into Spring Boot.
- Clean up after the tests finish.

