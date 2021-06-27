package vn.onltest.exception.movie;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

public class CustomMethodArgumentNotValidException extends RuntimeException {
    BindingResult bindingResult;

    public CustomMethodArgumentNotValidException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }


    public Errors getBindingResult() {
        return bindingResult;
    }
}
