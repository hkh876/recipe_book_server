package com.recipe.book.repository;

import com.recipe.book.domain.Recipe;

import java.util.List;

public interface RecipeQueryRepository {
    List<Recipe> findAllBySearch(String searchType, String searchKeyword);
}
