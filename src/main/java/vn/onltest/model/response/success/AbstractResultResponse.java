package vn.onltest.model.response.success;

import lombok.Getter;
import lombok.Setter;
import vn.onltest.model.response.AbstractResponse;

@Getter
@Setter
public abstract class AbstractResultResponse<T> extends AbstractResponse {
    protected int code;
    protected T data;

    public AbstractResultResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }
}
