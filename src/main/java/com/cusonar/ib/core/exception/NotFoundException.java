package com.cusonar.ib.core.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("리소스를 찾을 수 없습니다.");
    }
}
