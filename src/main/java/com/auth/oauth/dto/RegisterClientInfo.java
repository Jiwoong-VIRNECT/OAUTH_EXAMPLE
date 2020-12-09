package com.auth.oauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterClientInfo {

	private String name;
	private String redirectUri;
	private String clientType;

}
