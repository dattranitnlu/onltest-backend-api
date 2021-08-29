package vn.onltest.exception.movie;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class RequestException extends RuntimeException {

    Map<String, String> errors;
    HttpStatus httpStatus;

    public RequestException(Map<String, String> errors, HttpStatus httpStatus) {
        this.errors = errors;
        this.httpStatus = httpStatus;
    }

    public RequestException(String message, Map<String, String> errors, HttpStatus httpStatus) {
        super(message);
        this.errors = errors;
        this.httpStatus = httpStatus;
    }

    public RequestException(String message, Throwable cause, Map<String, String> errors, HttpStatus httpStatus) {
        super(message, cause);
        this.errors = errors;
        this.httpStatus = httpStatus;
    }

    public RequestException(Throwable cause, Map<String, String> errors, HttpStatus httpStatus) {
        super(cause);
        this.errors = errors;
        this.httpStatus = httpStatus;
    }

    public RequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map<String, String> errors, HttpStatus httpStatus) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errors = errors;
        this.httpStatus = httpStatus;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}