package io.avios.resource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        Map<String, Object> response = new HashMap<>();
        
        if (exception instanceof WebApplicationException) {
            WebApplicationException webEx = (WebApplicationException) exception;
            response.put("message", webEx.getMessage());
            return Response.status(webEx.getResponse().getStatus())
                    .entity(response).build();
        } else if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException validationEx = (ConstraintViolationException) exception;
            
            Map<String, String> validationErrors = validationEx.getConstraintViolations()
                    .stream()
                    .collect(Collectors.toMap(
                            violation -> violation.getPropertyPath().toString(),
                            ConstraintViolation::getMessage
                    ));
            
            response.put("message", "Validation failed");
            response.put("errors", validationErrors);
            
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(response).build();
        }
        
        // Generic error case
        response.put("message", "Internal server error");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(response).build();
    }
}