package com.recipe.book.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeListResDto {
    private Long id;
    private String title;
    private CategoryListResDto category;
}
