package com.recipe.book.model;

import com.recipe.book.domain.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RecipeReqDto {
    @NotEmpty(message = "제목은 필수 값 입니다.")
    private String title;

    @Min(value = 1, message = "카테고리 아이디는 1 이상 입니다.")
    private Long categoryId;

    @NotEmpty(message = "식재료는 필수 값 입니다.")
    private String ingredients;

    @NotEmpty(message = "레시피는 필수 값 입니다.")
    private String contents;

    private String tip;
    private String reference;
    private MultipartFile picture;

    // 조회 후 데이터
    private Category category;
}
