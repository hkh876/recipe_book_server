package com.recipe.book.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResDto {
    private ErrorCode errorCode;
    private String message;

    @Builder
    public ErrorResDto(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
