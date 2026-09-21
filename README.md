# Simple Banking System — Jenkins + Maven Mini Project

A small console-based banking app used to practice a real Jenkins **Pipeline** job
(not just a Freestyle "Hello World"), continuing from your Week 5 Jenkins/Maven setup.

## What it does

- `Bank` manages multiple `BankAccount`s (open, look up, transfer between accounts)
- `BankAccount` supports deposit / withdraw, keeps a transaction log, and validates input
- Throws a custom `InsufficientFundsException` on bad withdrawals/transfers
- `Main` runs a short demo scenario and prints balances + transaction history
- 13 JUnit 5 tests across `BankAccountTest` and `BankTest` cover the happy paths and the
  error cases (negative amounts, duplicate accounts, missing accounts, insufficient funds)

## Project structure

```
jenkins-maven-demo/
├── pom.xml
├── Jenkinsfile
├── .gitignore
└── src
    ├── main/java/com/example/bank/
    │   ├── Main.java
    │   ├── Bank.java
    │   ├── BankAccount.java
    │   └── InsufficientFundsException.java
    └── test/java/com/example/bank/
        ├── BankAccountTest.java
        └── BankTest.java
```

## 1. Run it locally first (sanity check before Jenkins)

```
cd jenkins-maven-demo
mvn clean test        REM runs the 13 unit tests
mvn clean package      REM builds target/bank-demo.jar
java -jar target/bank-demo.jar
```

You should see `BUILD SUCCESS`, all tests passing, and the demo output with account
balances and a transaction log.

## 2. Put it in Git

Jenkins pipelines read the `Jenkinsfile` from source control, so push this folder to a
Git repo (GitHub, or a local repo) before wiring up the Jenkins job:

```
git init
git add .
git commit -m "Simple banking system - Maven + JUnit 5"
git remote add origin <your-repo-url>
git push -u origin main
```

## 3. Create the Jenkins Pipeline job

Using the JDK/Maven tool names you already configured (**Java21**, **Maven3**):

1. Jenkins Dashboard → **New Item**
2. Name it `Bank-Demo-Pipeline`, select **Pipeline**, click **OK**
3. Under **Pipeline**, set **Definition** to `Pipeline script from SCM`
4. **SCM**: Git → paste your repo URL (and branch, e.g. `main`)
5. **Script Path**: `Jenkinsfile` (already the default)
6. Save, then click **Build Now**

## 4. What the pipeline does

The included `Jenkinsfile` runs five stages instead of one flat batch script:

| Stage      | What happens |
|------------|--------------|
| Checkout   | Pulls the code from your Git repo |
| Build      | `mvn clean compile` |
| Test       | `mvn test`, then publishes results with the `junit` step so Jenkins shows a pass/fail trend graph |
| Package    | `mvn package -DskipTests` to produce `target/bank-demo.jar` |
| Archive    | Saves the jar as a build artifact you can download from the Jenkins UI |
| Run Demo   | Runs the packaged jar so the console output shows up in the build log |

Console Output on a successful build will end with `BUILD SUCCESS` and the demo's
printed account balances.

## 5. If your agent isn't Windows

The `Jenkinsfile` uses `bat` steps (Windows), matching your Week 5 setup. If your
Jenkins agent runs Linux/macOS instead, just swap every `bat 'mvn ...'` /
`bat 'java ...'` for `sh 'mvn ...'` / `sh 'java ...'` in the Jenkinsfile.
