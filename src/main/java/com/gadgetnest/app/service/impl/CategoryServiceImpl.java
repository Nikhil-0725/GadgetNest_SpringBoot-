package com.gadgetnest.app.service.impl;

import com.gadgetnest.app.entity.Category;
import com.gadgetnest.app.exception.ResourceNotFoundException;
import com.gadgetnest.app.repository.CategoryRepository;
import com.gadgetnest.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found With Id : " + id));
        category.setName(categoryDetails.getName());
        category.setSlug(categoryDetails.getSlug());
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryBySlug(String slug) {
        return categoryRepository.findByName(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found With Slug : " + slug));
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category Not Found With Id : " + id));

        categoryRepository.delete(category);
    }
}
