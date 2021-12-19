package com.movies.api.jwt;

import lombok.Data;

import java.io.Serializable;
@Data
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private String token;

	private String userName;

	private String role;

}