package com.cusonar.ib.core.exception;

public class NotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2412300725382554749L;

	public NotFoundException() {
        super("리소스를 찾을 수 없습니다.");
    }
}
