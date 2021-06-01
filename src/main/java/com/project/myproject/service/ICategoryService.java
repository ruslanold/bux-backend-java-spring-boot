package com.project.myproject.service;

import java.util.List;
import com.project.myproject.entity.Category;


public interface ICategoryService {

    List<Category> getCategories();
    Category getCategoryById(int id);
    Category getCategoryByName(String name);
    Category getCategoryWithTasksByNameAndUsername(String name, String username);
    Category createCategory(Category category);
    Category updateCategory(int id, Category category);
    void deleteCategory(int id);

}
