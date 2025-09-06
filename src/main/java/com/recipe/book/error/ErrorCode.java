package com.recipe.book.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    /* 카테고리 */
    CATEGORY_DELETE_ERROR(HttpStatus.NOT_ACCEPTABLE, "레시피가 존재하는 카테고리는 삭제 할 수 없습니다."),

    NOT_FOUND_DATA_ERROR(HttpStatus.NOT_FOUND, "데이터가 존재 하지 않습니다."),
    NOT_FOUND_FILE_ERROR(HttpStatus.NOT_FOUND, "파일이 존재 하지 않습니다."),
    UPLOAD_SIZE_ERROR(HttpStatus.BAD_REQUEST, "파일 업로드 용량을 초과했습니다."),
    NOT_VALID_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사 오류 입니다.");



    private final HttpStatus httpStatus;
    private final String message;
}
