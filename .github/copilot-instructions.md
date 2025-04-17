# GitHub Copilot Instructions for Java and Quarkus Development

## Project Context
This is a Quarkus-based Todo application. The application is built using Java and follows RESTful API principles to manage todo items.

## Coding Standards

### Java Best Practices
- Use Java 21 language features where appropriate
- Follow standard Java naming conventions (camelCase for methods/variables, PascalCase for classes)
- Prefer immutable objects whenever possible
- Avoid null references; use Optional where appropriate
- Use streams and functional programming when it improves readability
- Ensure proper exception handling with specific exception types

### Quarkus Best Practices
- Follow the package naming conventions (e.g., io.avios.resource, io.avios.service, io.avios.entity)
- Use Quarkus extensions instead of manually implementing cross-cutting concerns
- Utilize Dev Services for development environment dependencies
- For resources (controllers), focus only on request routing and parameter validation
- Move business logic to dedicated service classes
- Implement repository pattern for data access
- Use DTOs to separate API contracts from internal data models
- Apply proper validation using Bean Validation annotations
- Implement global exception handling
- Use reactive programming patterns when appropriate (e.g., RESTEasy Reactive, Mutiny)

### Testing Practices
- Write unit tests for all business logic
- Create integration tests for API endpoints
- Use @QuarkusTest for testing resources with the full application context
- Implement test containers for database integration tests
- Apply continuous testing during development

### DevOps Practices
- Configure proper health checks and metrics endpoints
- Externalize all configurations
- Secure sensitive information using environment variables or config files
- Use Quarkus' native compilation capabilities for production deployments
- Follow Docker best practices for container images
- Implement CI/CD pipelines for automated testing and deployment

## Design Patterns to Use
- Repository Pattern for data access
- Service Layer for business logic
- DTO Pattern for API contracts
- Dependency Injection for component coupling
- Builder Pattern for complex object creation

## Libraries and Extensions to Suggest
- Quarkus RESTEasy Reactive for responsive REST endpoints
- Quarkus Hibernate ORM with Panache for simplified data access
- Quarkus OpenAPI for API documentation
- Quarkus Security for authentication and authorization
- SmallRye Health for health checks
- Mutiny for reactive programming
- Testcontainers for integration testing

## Code Generation Guidance
When generating code for this project, follow these patterns:
- For REST resources, use JAX-RS annotations and return Response objects with proper status codes
- For data entities, use Hibernate ORM with Panache for simplified persistence
- For validation, apply Bean Validation annotations directly to entity fields
- For configuration, use application.properties with proper namespacing

## Example Patterns

### REST Resource Example
```java
@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {
    
    @Inject
    TodoService todoService;
    
    @GET
    public Response getAllTodos() {
        return Response.ok(todoService.findAll()).build();
    }
    
    @GET
    @Path("/{id}")
    public Response getTodo(@PathParam("id") Long id) {
        return todoService.findById(id)
            .map(todo -> Response.ok(todo).build())
            .orElse(Response.status(Status.NOT_FOUND).build());
    }
    
    @POST
    public Response createTodo(TodoDTO dto) {
        Todo created = todoService.create(dto);
        return Response.status(Status.CREATED).entity(created).build();
    }
}
```

### Service Example
```java
@ApplicationScoped
public class TodoService {
    
    @Inject
    TodoRepository repository;
    
    public List<TodoDTO> findAll() {
        return repository.listAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }
    
    public Optional<TodoDTO> findById(Long id) {
        return repository.findByIdOptional(id)
            .map(this::mapToDTO);
    }
    
    public Todo create(TodoDTO dto) {
        Todo entity = new Todo();
        entity.setTitle(dto.title);
        entity.setDescription(dto.description);
        entity.setCompleted(false);
        
        repository.persist(entity);
        return entity;
    }
    
    private TodoDTO mapToDTO(Todo entity) {
        TodoDTO dto = new TodoDTO();
        dto.id = entity.getId();
        dto.title = entity.getTitle();
        dto.description = entity.getDescription();
        dto.completed = entity.isCompleted();
        return dto;
    }
}
```

### Entity Example
```java
@Entity
public class Todo extends PanacheEntity {
    
    @NotBlank(message = "Title cannot be blank")
    private String title;
    
    private String description;
    
    private boolean completed;
    
    // Getters and setters
}
```