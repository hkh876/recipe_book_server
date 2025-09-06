package com.recipe.book.service;

import com.recipe.book.domain.Category;
import com.recipe.book.domain.Picture;
import com.recipe.book.domain.Recipe;
import com.recipe.book.error.ErrorCode;
import com.recipe.book.exception.BaseException;
import com.recipe.book.mapper.CategoryMapper;
import com.recipe.book.mapper.PictureMapper;
import com.recipe.book.mapper.RecipeMapper;
import com.recipe.book.model.*;
import com.recipe.book.repository.CategoryRepository;
import com.recipe.book.repository.PictureRepository;
import com.recipe.book.repository.RecipeQueryRepository;
import com.recipe.book.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final RecipeMapper recipeMapper;
    private final RecipeRepository recipeRepository;
    private final RecipeQueryRepository recipeQueryRepository;
    private final FileService fileService;
    private final PictureRepository pictureRepository;
    private final PictureMapper pictureMapper;

    @Value("${upload.base}")
    private String baseDirectoryPath;

    @Value("${upload.picture}")
    private String picturePath;

    @Transactional
    public void createCategory(CategoryReqDto reqDto) {
        Category category = categoryMapper.reqDtoToCategoryDomain(reqDto);
        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryListResDto> getCategories() {
        List<Category> results = categoryRepository.findAll();
        return categoryMapper.categoriesToCategoryListResDto(results);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Long count = recipeRepository.countByCategoryId(id);
        if (count > 0) {
            throw new BaseException(ErrorCode.CATEGORY_DELETE_ERROR);
        }

        categoryRepository.deleteById(id);
    }

    @Transactional
    public void createRecipe(RecipeReqDto reqDto) throws IOException {
        // 카테고리 정보 조회
        Category category = categoryRepository.findById(reqDto.getCategoryId()).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_DATA_ERROR)
        );
        reqDto.setCategory(category);

        // 데이터 저장
        Recipe recipe = recipeMapper.reqDtoToRecipeDomain(reqDto);
        Recipe result = recipeRepository.save(recipe);

        // 파일 처리
        String directoryPath = baseDirectoryPath + picturePath + "/" + result.getId() + "/";
        MultipartFile file = reqDto.getPicture();

        if (file != null) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            fileService.saveFile(directoryPath, fileName, file);

            // 데이터 저장
            PictureDto pictureDto = PictureDto.builder()
                    .filePath(directoryPath)
                    .fileName(fileName)
                    .recipe(result)
                    .build();
            pictureRepository.save(pictureMapper.dtoToPictureDomain(pictureDto));
        }
    }

    @Transactional(readOnly = true)
    public List<RecipeListResDto> getRecipes(SearchReqDto reqDto) {
        // 검색 조건
        String searchType = reqDto.getSearchType();
        String searchKeyword = reqDto.getSearchKeyword();

        return recipeMapper.domainsToRecipeListResDtoList(recipeQueryRepository.findAllBySearch(searchType, searchKeyword));
    }

    @Transactional(readOnly = true)
    public RecipeInfoResDto getRecipeInfo(Long id) {
        Recipe result = recipeRepository.findById(id).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_DATA_ERROR)
        );

        return recipeMapper.domainsToRecipeInfoResDto(result);
    }

    @Transactional(readOnly = true)
    public Path getPicturePath(Long id) {
        Picture picture = pictureRepository.findById(id).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_DATA_ERROR)
        );

        return Paths.get(picture.getFilePath()).resolve(picture.getFileName()).normalize();
    }

    @Transactional
    public void updateRecipe(RecipeUpdateReqDto reqDto) throws IOException {
        Recipe recipe = recipeRepository.findById(reqDto.getId()).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_DATA_ERROR)
        );

        Category category = categoryRepository.findById(reqDto.getCategoryId()).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_DATA_ERROR)
        );

        // 데이터 업데이트
        recipe.update(
            reqDto.getTitle(),
            reqDto.getIngredients(),
            reqDto.getContents(),
            reqDto.getTip(),
            reqDto.getReference(),
            category
        );

        // 파일 처리
        String directoryPath = baseDirectoryPath + picturePath + "/" + recipe.getId() + "/";
        MultipartFile file = reqDto.getPicture();

        if (file != null) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            fileService.saveFile(directoryPath, fileName, file);

            // 데이터 저장
            PictureDto pictureDto = PictureDto.builder()
                    .filePath(directoryPath)
                    .fileName(fileName)
                    .recipe(recipe)
                    .build();
            pictureRepository.save(pictureMapper.dtoToPictureDomain(pictureDto));
        }
    }

    @Transactional
    public void deleteRecipe(Long id) throws IOException {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_DATA_ERROR)
        );

        // 사진 파일 삭제
        Picture picture = recipe.getPicture();
        if (picture != null) {
            deletePicture(picture.getId());
        }
    }

    @Transactional
    public void deletePicture(Long id) throws IOException {
        Picture picture = pictureRepository.findById(id).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_DATA_ERROR)
        );

        // 파일 삭제
        fileService.deleteFile(picture.getFilePath(), picture.getFileName());

        // 데이터 삭제
        pictureRepository.delete(picture);
    }
}
