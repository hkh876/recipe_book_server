package com.recipe.book.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeInfoResDto {
    private Long id;
    private String title;
    private String ingredients;
    private String contents;
    private String tip;
    private String reference;
    private PictureResDto picture;
    private CategoryResDto category;
}
