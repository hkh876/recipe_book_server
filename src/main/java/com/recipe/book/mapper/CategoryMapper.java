package com.recipe.book.mapper;

import com.recipe.book.domain.Category;
import com.recipe.book.model.CategoryListResDto;
import com.recipe.book.model.CategoryReqDto;
import com.recipe.book.model.CategoryResDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    Category reqDtoToCategoryDomain(CategoryReqDto reqDto);
    CategoryListResDto categoryToCategoryListResDto(Category category);
    List<CategoryListResDto> categoriesToCategoryListResDto(List<Category> categories);
    CategoryResDto categoryToCategoryResDto(Category category);
}
