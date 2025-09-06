package com.recipe.book.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchReqDto {
    private String searchType;
    private String searchKeyword;
}
