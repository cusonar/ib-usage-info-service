package com.cusonar.ib.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ExceptionResponse {
	private int errorCode;
	private String message;
}
