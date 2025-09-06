package com.recipe.book.mapper;

import com.recipe.book.domain.Recipe;
import com.recipe.book.model.RecipeInfoResDto;
import com.recipe.book.model.RecipeListResDto;
import com.recipe.book.model.RecipeReqDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
    @Mapping(target = "id", ignore = true)
    Recipe reqDtoToRecipeDomain(RecipeReqDto reqDto);
    List<RecipeListResDto> domainsToRecipeListResDtoList(List<Recipe> recipes);
    RecipeInfoResDto domainsToRecipeInfoResDto(Recipe recipe);
}
