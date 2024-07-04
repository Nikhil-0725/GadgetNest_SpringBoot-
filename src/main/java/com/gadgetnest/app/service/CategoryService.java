package com.gadgetnest.app.service;

import com.gadgetnest.app.entity.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(Category category);

    public Category updateCategory(Long id, Category categoryDetails);

    public List<Category> getAllCategories();

    public Category getCategoryBySlug(String slug);

    public void deleteCategory(Long id);
}
