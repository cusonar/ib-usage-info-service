package com.cusonar.ib.response;

import lombok.Getter;

@Getter
public class ResponseOne<T> {

	private T result;
	
	public ResponseOne(T response) {
		this.result = response;
	}
}
