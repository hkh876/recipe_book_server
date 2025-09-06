package com.recipe.book.repository;

import com.recipe.book.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Long countByCategoryId(Long categoryId);
}
