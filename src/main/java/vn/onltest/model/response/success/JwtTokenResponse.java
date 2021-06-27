package vn.onltest.model.response.success;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtTokenResponse<T> {
	private final String token;
	private T userInfo;

	public JwtTokenResponse(String token, T userInfo) {
		this.token = token;
		this.userInfo = userInfo;
	}
}