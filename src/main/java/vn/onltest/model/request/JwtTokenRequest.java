package vn.onltest.model.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Login Model Request")
public class JwtTokenRequest {

	@Size(min = 5, max = 128)
	private String username;

	@Size(min = 8, max = 128)
	private String password;
}