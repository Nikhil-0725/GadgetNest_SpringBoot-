package com.gadgetnest.app.controller;

import com.gadgetnest.app.entity.Category;
import com.gadgetnest.app.response.ApiResponse;
import com.gadgetnest.app.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create-category")
    public ResponseEntity<ApiResponse<Category>> createCategory(@Valid @RequestBody Category category, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, result.getAllErrors().get(0).getDefaultMessage(), null));
        }

        category.setSlug(generateSlug(category.getName()));

        Category category1 = categoryService.createCategory(category);

        return ResponseEntity.ok().body(new ApiResponse<>(true, "Category Created Successfully", category1));
    }

    @PutMapping("/update-category/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category, BindingResult result) {
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, result.getAllErrors().get(0).getDefaultMessage(), null));
        }

        category.setSlug(generateSlug(category.getName()));

        Category category1 = categoryService.updateCategory(id, category);

        return ResponseEntity.ok().body(new ApiResponse<>(true, "Category Updated Successfully", category1));
    }

    @GetMapping("/get-category")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse<>(true, "Categories fetched successfully", categories));
    }

    @GetMapping("/single-category/{slug}")
    public ResponseEntity<ApiResponse<Category>> getCategoryBySlug(@PathVariable String slug) {
        Category category = categoryService.getCategoryBySlug(slug);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category fetched successfully", category));
    }

    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category deleted successfully", null));
    }

    private String generateSlug(String input){
        String nonWhitespace = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String slug = pattern.matcher(nonWhitespace).replaceAll("").toLowerCase(Locale.ENGLISH);
        return slug.replaceAll("[^\\w\\s-]", "").replaceAll("\\s+", "-").replaceAll("[-]+", "-").trim();
    }
}
