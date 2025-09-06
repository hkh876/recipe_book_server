package com.recipe.book.controller;

import com.recipe.book.error.ErrorCode;
import com.recipe.book.exception.BaseException;
import com.recipe.book.model.*;
import com.recipe.book.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recipe")
public class RecipeRestController {
    private final RecipeService recipeService;

    @PostMapping("/category/create")
    public void createCategory(@RequestBody @Valid CategoryReqDto reqDto) {
        recipeService.createCategory(reqDto);
    }

    @GetMapping("/category/list")
    public List<CategoryListResDto> getCategories() {
        return recipeService.getCategories();
    }

    @DeleteMapping("/category/delete")
    public void deleteCategory(@RequestParam(name = "id") Long id) {
        recipeService.deleteCategory(id);
    }

    @PostMapping("/create")
    public void createRecipe(@ModelAttribute @Valid RecipeReqDto reqDto) throws IOException {
        recipeService.createRecipe(reqDto);
    }

    @GetMapping("/list")
    public List<RecipeListResDto> getRecipes(@ModelAttribute SearchReqDto reqDto) {
        log.info("search type : {}, search keyword: {}", reqDto.getSearchType(), reqDto.getSearchKeyword());

        return recipeService.getRecipes(reqDto);
    }

    @GetMapping("/info")
    public RecipeInfoResDto getRecipeInfo(Long id) {
        return recipeService.getRecipeInfo(id);
    }

    @PutMapping("/update")
    public void updateRecipe(@ModelAttribute @Valid RecipeUpdateReqDto reqDto) throws IOException {
        recipeService.updateRecipe(reqDto);
    }

    @GetMapping("/picture/preview")
    public ResponseEntity<Resource> getPicturePreview(@RequestParam(name = "id") Long id) throws IOException {
        Path path = recipeService.getPicturePath(id);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new BaseException(ErrorCode.NOT_FOUND_FILE_ERROR);
        }

        String contentType = Files.probeContentType(path);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }

    @DeleteMapping("/delete")
    public void deleteRecipe(@RequestParam(name = "id") Long id) throws IOException {
        recipeService.deleteRecipe(id);
    }

    @DeleteMapping("/picture/delete")
    public void deletePicture(@RequestParam(name = "id") Long id) throws IOException {
        recipeService.deletePicture(id);
    }
}
