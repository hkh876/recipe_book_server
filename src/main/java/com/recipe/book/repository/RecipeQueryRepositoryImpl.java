package com.recipe.book.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.recipe.book.domain.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.recipe.book.domain.QRecipe.recipe;

@Repository
@RequiredArgsConstructor
public class RecipeQueryRepositoryImpl implements RecipeQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Recipe> findAllBySearch(String searchType, String searchKeyword) {
        BooleanBuilder condition = new BooleanBuilder();

        if (!Objects.equals(searchType, "all")) {
            // 카테고리 별 검색
            condition.and(recipe.category.name.eq(searchType));
            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                condition.and(recipe.title.containsIgnoreCase(searchKeyword));
            }
        } else {
            // 일반 검색
            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                condition.and(recipe.title.containsIgnoreCase(searchKeyword));
            }
        }

        List<Recipe> query = jpaQueryFactory.selectFrom(recipe)
                .where(condition)
                .fetch();

        return query;
    }
}
