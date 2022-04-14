package vn.onltest.model.response.success;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.onltest.model.response.AbstractResponse;

@Getter
@Setter
@AllArgsConstructor
public class ResultMessageResponse extends AbstractResponse {
    private int code;
    private String message;
}
