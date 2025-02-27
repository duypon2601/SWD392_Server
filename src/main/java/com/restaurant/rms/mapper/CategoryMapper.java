package com.restaurant.rms.mapper;


import com.restaurant.rms.dto.request.CategoryDTO;
import com.restaurant.rms.entity.Category;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category category) {
        return CategoryDTO.builder()
                .category_id(category.getCategory_id())
                .name(category.getName())
                .build();
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        return Category.builder()
                .category_id(categoryDTO.getCategory_id())
                .name(categoryDTO.getName())
                .build();
    }
}