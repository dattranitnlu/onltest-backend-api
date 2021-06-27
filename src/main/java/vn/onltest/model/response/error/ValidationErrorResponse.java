package vn.onltest.model.response.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationErrorResponse<T> extends AbstractErrorResponse {
    private T errors;

    public ValidationErrorResponse(int errorCode, String message, T errors) {
        super(errorCode, message);
        this.errors = errors;
    }
}
