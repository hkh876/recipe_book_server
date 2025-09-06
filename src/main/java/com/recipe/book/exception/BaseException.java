package com.recipe.book.exception;

import com.recipe.book.error.ErrorCode;
import lombok.Getter;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class BaseException extends ResponseStatusException {
    private final ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getHttpStatus());
        this.errorCode = errorCode;
    }
}
