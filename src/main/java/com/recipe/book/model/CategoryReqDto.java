package com.recipe.book.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryReqDto {
    @NotEmpty(message = "카테고리 명은 필수 값 입니다.")
    private String name;
}
