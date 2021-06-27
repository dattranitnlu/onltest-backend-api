package vn.onltest.model.response.success;

public class BaseResultResponse<T> extends AbstractResultResponse<T> {
    public BaseResultResponse(int errorCode, T data) {
        super(errorCode, data);
    }
}
