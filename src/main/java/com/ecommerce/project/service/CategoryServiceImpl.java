package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final List<Category> categories = new ArrayList<>();
    private long nextId = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void addCategory(Category category) {
        categories.add(category);
        category.setCategoryId(nextId++);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category =
                categories.stream().filter(c -> c.getCategoryId() == categoryId).
                        findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found"));
        categories.remove(category);
        return "Deleted Successfully";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Optional<Category> optionalCategory =
                categories.stream().filter(c -> c.getCategoryId() == categoryId).findFirst();
        if (optionalCategory.isPresent()) {
            Category oldCategory = optionalCategory.get();
            oldCategory.setCategoryName(category.getCategoryName());
            return oldCategory;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found");
        }
    }
}
