package com.recipe.book.model;

import com.recipe.book.domain.Recipe;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PictureDto {
    private String filePath;
    private String fileName;
    private Recipe recipe;

    @Builder
    public PictureDto(String filePath, String fileName, Recipe recipe) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.recipe = recipe;
    }
}
