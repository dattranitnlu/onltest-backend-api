package vn.onltest.model.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AbstractErrorResponse {
    protected int code;
    protected String message;
}
