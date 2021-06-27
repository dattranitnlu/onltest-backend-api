package vn.onltest.exception;

import vn.onltest.exception.movie.CustomMethodArgumentNotValidException;
import vn.onltest.exception.movie.NotFoundException;
import vn.onltest.exception.movie.RequestException;
import vn.onltest.model.response.error.AbstractErrorResponse;
import vn.onltest.model.response.error.ApiErrorResponse;
import vn.onltest.model.response.error.BaseErrorResponse;
import vn.onltest.model.response.error.ValidationErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@ControllerAdvice
public class AppExceptionsHandler {
    AbstractErrorResponse response;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<AbstractErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }
        response = new ValidationErrorResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomMethodArgumentNotValidException.class)
    public ResponseEntity<AbstractErrorResponse> handleMethodArgumentNotValidException(CustomMethodArgumentNotValidException exc) {
        Map<String, String> errors = new HashMap<>();
        exc.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        response = new ApiErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "The given data was invalid.",
                System.currentTimeMillis(),
                HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),
                errors);

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {NullPointerException.class, ServiceException.class})
    public ResponseEntity<AbstractErrorResponse> handleSpecificExceptions(Exception ex) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if (errorMessageDescription == null) {
            errorMessageDescription = ex.toString();
        }

        response = new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessageDescription);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<AbstractErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if (errorMessageDescription == null) {
            errorMessageDescription = ex.toString();
        }

        response = new BaseErrorResponse(HttpStatus.NOT_FOUND.value(), errorMessageDescription);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AbstractErrorResponse> handleAuthenticationException(BadCredentialsException ex) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if (errorMessageDescription == null) {
            errorMessageDescription = ex.toString();
        } else {
            errorMessageDescription = "Invalid username or password";
        }

        response = new BaseErrorResponse(HttpStatus.UNAUTHORIZED.value(), errorMessageDescription);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<AbstractErrorResponse> handleMovieNotFoundException(NotFoundException exc) {
        response = new ApiErrorResponse(HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<AbstractErrorResponse> handleMovieBadRequestException(RequestException exc) {
        response = new ApiErrorResponse(exc.getHttpStatus().value(),
                exc.getMessage(),
                System.currentTimeMillis(),
                exc.getHttpStatus().getReasonPhrase(),
                exc.getErrors());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<AbstractErrorResponse> handleExpiredTime(Exception exc) {
        response = new BaseErrorResponse(HttpStatus.UNAUTHORIZED.value(), exc.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<AbstractErrorResponse> handleAccessDenied(Exception exc) {
        response = new BaseErrorResponse(HttpStatus.UNAUTHORIZED.value(), exc.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<AbstractErrorResponse> handleMethodNotAllowed(Exception exc) {
        response = new BaseErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), exc.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AbstractErrorResponse> handleMethodArgumentNotValidException(Exception exc) {
        response = new BaseErrorResponse((HttpStatus.BAD_REQUEST.value()), exc.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
