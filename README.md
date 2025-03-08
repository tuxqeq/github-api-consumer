# GitHub API Consumer

## Overview
Lists all GitHub repositories (not forks) for a given user.
Displays each repository name, its owner login, and branch details (branch name and last commit SHA).

## Requirements
- Java\ 21
- Maven
- Quarkus\ 3.19.2

## Building \& Running
Run:
```bash
   ./mvnw clean install
```
Then run:
```bash
  ./mvnw quarkus:dev
```
   
## Usage
Request all non-fork repos for a given user:
   ```bash
    curl -X GET "http://localhost:8080/github/users/{username}/repos"
   ```
Replace {username} with an actual GitHub username.

## Using GitHub API Key
Using unathenticated requests to the GitHub API is limited. So if you are planning to use this application frequently, you should authenticate your requests using the github API key.

To authenticate requests, you can use a GitHub API key. The `GithubClient` class has a commented-out lines that can be used to add the Authorization header to the requests. These are this lines:
```java
/*@ClientHeaderParam(name = "Authorization", value = "token {githubToken}")*/
```

```java
/*default String githubToken() {
    return System.getenv("GITHUB_TOKEN");
}*/
```
Create the GitHub API key on github.com and set it as an environment variable named `GITHUB_TOKEN`.

```bash
  export GITHUB_TOKEN=your_github_token
```

*Do this before building and running the application.*

## Example
```bash
  curl -X GET "http://localhost:8080/github/users/torwalds/repos"
```
```json
[{"name":"MS005_Scheme","owner":{"login":"torwalds"},"fork":false,"branches":[{"name":"main","commit":{"sha":"1aae87c8ec41854c49f7195c76eeb7d60e826a1f"}}]}]
```

## Error Handling
If the user does not exist, the endpoint returns 404 with a JSON body containing status and message.
```bash
  curl -X GET "http://localhost:8080/github/users/nonexistentuser/repos"
```
```json
{"status":404,"message":"User not found"}
```


## Testing
Run the following command to execute the tests:
   ```bash
    ./mvnw test
   ```
This executes an integration test checking the happy path
