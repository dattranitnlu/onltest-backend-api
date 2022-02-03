package vn.onltest.model.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Option Model Request")
public class OptionModelRequest {
    private String optionContent;
    private boolean correct;
}
