package com.restaurant.rms.service.categoryService;

import com.restaurant.rms.entity.Category;
import com.restaurant.rms.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Lấy danh sách tất cả các category
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Lấy category theo ID
    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }

    // Thêm mới category
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Cập nhật category
    public Category updateCategory(int id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(updatedCategory.getName());
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    // Xóa category theo ID
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}