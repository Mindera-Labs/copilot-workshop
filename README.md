# Todo Application with Quarkus

A modern RESTful Todo application built with Quarkus, following best practices in software architecture including layered design, proper separation of concerns, and comprehensive testing.

## Project Overview

This application demonstrates a well-structured Quarkus application with:

- Layered architecture (resource, service, repository, entity, DTO layers)
- RESTful API patterns with proper validation
- Reactive programming with Quarkus
- Integration with PostgreSQL database
- Comprehensive test coverage

## Model Context Protocol (MCP) Integration Possibilities

This application could be enhanced with Model Context Protocol (MCP) servers to extend its capabilities and enable AI assistants to interact with various services directly.

### MCP Servers for Integration

1. **GitHub MCP Server**
   - Repository management and file operations
   - Pull request and issue handling
   - Issues creation and more



### Additional MCP Resources

Find more MCP servers and resources at:
- [MCP.so](https://mcp.so/) - The official directory of MCP servers
- [MCPServers.net](https://mcpservers.net/) - Comprehensive MCP server navigation platform
- [GitHub MCP Servers Repository](https://github.com/EvalsOne/mcp-servers) - Collection of reference implementations

## GitHub Copilot Commands in VS Code

GitHub Copilot in VS Code provides several ways to reference code and context in your prompts using hash (#) commands.

### Code Reference Commands

1. **#file**: Reference specific files in your workspace
   ```
   Explain the validation in #file:src/main/java/io/avios/entity/Todo.java
   ```

2. **#selection**: Reference your currently selected code
   ```
   Generate a test for this method: #selection
   ```

3. **#editor**: Reference code in the active editor
   ```
   Refactor this: #editor
   ```

4. **#terminalOutput**: Reference output from the terminal
   ```
   Explain this error: #terminalOutput
   ```

5. **#vscodeAPI**: Add VS Code API as context to your prompt for extension development
   ```
   How do I implement a custom editor using #vscodeAPI?
   ```

### Web Search and Documentation Commands

1. **#websearch**: Search the web for current information
   ```
   #websearch Quarkus best practices for validation
   ```

2. **#fetch**: Fetch and analyze content from a specific URL
   ```
   #fetch https://quarkus.io/guides/validation
   ```

### TDD Workflow with GitHub Copilot

1. **Writing Tests First**:
   ```
   /tests
   ```
   This slash command generates tests for methods in your editor

2. **Setting Up Test Framework**:
   ```
   /setupTests
   ```
   This slash command helps configure a testing framework for your project

3. **Implementing Features**:
   ```
   Implement the TodoService method to pass this test: #selection
   ```

4. **Researching Implementation Methods**:
   ```
   #websearch efficient SQL query for filtering todos by multiple criteria in Quarkus
   ```

5. **Checking Documentation**:
   ```
   #fetch https://quarkus.io/guides/hibernate-orm-panache
   ```

### Example Complete Workflow

To implement a "search todos by keyword" feature:

1. **Research First**: 
   ```
   #websearch PostgreSQL text search with Quarkus Panache
   ```

2. **Check Documentation**:
   ```
   #fetch https://quarkus.io/guides/hibernate-orm-panache#query-methods
   ```

3. **Create Test**: 
   Use the `/tests` command to generate tests for your search feature

4. **Implement Feature**:
   ```
   Implement the search method in TodoService based on this test: #selection
   ```

5. **Add Endpoint**:
   ```
   Add a REST endpoint in TodoResource for this search feature: #selection
   ```

6. **Document the API**:
   ```
   Update the OpenAPI documentation for the new search endpoint: #selection
   ```

7. **Learn Best Practices**:
   ```
   #websearch RESTful API pagination best practices with Quarkus
   ```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it's not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Testing

Run the tests using:

```shell
./mvnw test
```

The application uses TestContainers for integration tests, ensuring tests run against a real PostgreSQL database.

## API Documentation

When running in dev mode, access the Swagger UI at:

```
http://localhost:8080/q/swagger-ui/
```

## Related Guides

- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the active record or the repository pattern
- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A Jakarta REST implementation utilizing build time processing and Vert.x.
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC
- GitHub Copilot ([documentation](https://docs.github.com/en/copilot)): AI pair programming tool
