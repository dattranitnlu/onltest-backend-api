package vn.onltest.model.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.onltest.model.response.AbstractResponse;

@Getter
@Setter
@AllArgsConstructor
public class AbstractErrorResponse extends AbstractResponse {
    protected int code;
    protected String message;
}
