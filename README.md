# Todo Application with Quarkus

A modern RESTful Todo application built with Quarkus, following best practices in software architecture including layered design, proper separation of concerns, and comprehensive testing.

## Project Overview

This application demonstrates a well-structured Quarkus application with:

- Layered architecture (resource, service, repository, entity, DTO layers)
- RESTful API patterns with proper validation
- Reactive programming with Quarkus
- Integration with PostgreSQL database
- Comprehensive test coverage

## GitHub Copilot Commands with Hash (#)

GitHub Copilot's hash (#) commands allow you to reference code elements, search the web for documentation, and more.

### Code Reference Commands

1. **#file**: Reference specific files
   ```
   Explain the validation in #file:src/main/java/io/avios/entity/Todo.java
   ```

2. **#selection**: Reference selected code
   ```
   Generate a test for this method: #selection
   ```

3. **#codebase**: Reference your entire codebase
   ```
   Find usages of TodoDTO in the #codebase
   ```

4. **#editor**: Reference code in the active editor
   ```
   Refactor this: #editor
   ```

### Web Search and Documentation Commands

1. **#websearch**: Search the web for current information
   ```
   #websearch Quarkus best practices for validation
   ```

2. **#fetch**: Fetch and analyze content from specific URLs
   ```
   #fetch https://quarkus.io/guides/validation
   ```

### TDD Workflow with Hash Commands

1. **Writing Tests First**:
   ```
   Write a test for a feature to find todos by priority in #file:src/test/java/io/avios/service/TodoServiceTest.java
   ```

2. **Implementing Features**:
   ```
   Implement the TodoService method to pass this test: #selection
   ```

3. **Researching Implementation Methods**:
   ```
   #websearch efficient SQL query for filtering todos by multiple criteria in Quarkus
   ```

4. **Checking Documentation**:
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
   ```
   Create a test for searching todos by keyword in #file:src/test/java/io/avios/service/TodoServiceTest.java
   ```

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
