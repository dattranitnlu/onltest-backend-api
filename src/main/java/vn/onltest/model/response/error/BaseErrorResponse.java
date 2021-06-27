package vn.onltest.model.response.error;

public class BaseErrorResponse extends AbstractErrorResponse {
    public BaseErrorResponse(int errorCode, String message) {
        super(errorCode, message);
    }
}
