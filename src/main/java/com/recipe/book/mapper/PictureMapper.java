package com.recipe.book.mapper;

import com.recipe.book.domain.Picture;
import com.recipe.book.model.PictureDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PictureMapper {
    @Mapping(target = "id", ignore = true)
    Picture dtoToPictureDomain(PictureDto dto);
}
