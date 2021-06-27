package vn.onltest.model.response.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorResponse extends AbstractErrorResponse {
    private long timeStamp;
    private String error;
    private Object errors;

    public ApiErrorResponse(int errorCode, String message, long timeStamp, String error, Object errors) {
        super(errorCode, message);
        this.timeStamp = timeStamp;
        this.error = error;
        this.errors = errors;
    }
}
