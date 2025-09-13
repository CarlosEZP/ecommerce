package dev.carlosezp.ecommerce.exception.handler;

import dev.carlosezp.ecommerce.exception.APIException;
import dev.carlosezp.ecommerce.exception.ResourceNotFoundException;
import dev.carlosezp.ecommerce.payload.APIResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> argumentNotValid(MethodArgumentNotValidException exception){
        Map<String, String> response = new HashMap<>();
        exception.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError)error).getField();
                    String message = error.getDefaultMessage();
                    response.put(fieldName, message);
                });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> resourceNotFound(ResourceNotFoundException exception){
        return new ResponseEntity<>(new APIResponse(exception.getMessage(),false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> apiException(APIException exception){

        return new ResponseEntity<>(new APIResponse(exception.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse> constraintViolation(ConstraintViolationException exception){
        return new ResponseEntity<>(new APIResponse(exception.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<APIResponse> illegalArgument(IllegalArgumentException exception){
        return new ResponseEntity<>(new APIResponse(exception.getMessage(),false),HttpStatus.BAD_REQUEST);
    }
}
