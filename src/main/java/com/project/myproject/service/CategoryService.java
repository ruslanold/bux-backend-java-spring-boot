package com.project.myproject.service;

import com.project.myproject.dao.CategoryRepository;
import com.project.myproject.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryService implements ICategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(int id) {
        if(!categoryRepository.existsById(id)){
            throw new IllegalArgumentException("No category with such id: " + id);
        }
        return categoryRepository.getOne(id);
    }

    @Override
    public Category getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        if (category == null){
            throw new IllegalArgumentException("No category with such name: " + name);
        }
        return category;
    }

    public Category getCategoryWithTasksByNameAndUsername(String name, String username) {
        Category category = categoryRepository.findTasksByCategoryNameAndNotExistsUsername(name, username);
        if (category == null){
            throw new IllegalArgumentException("No category with such name: " + name);
        }
        return category;
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.saveAndFlush(category);
    }

    @Override
    public Category updateCategory(int id, Category category) {
        if(!categoryRepository.existsById(id)){
            throw new IllegalArgumentException("No category with such id: " + id);
        }
        System.out.println(category + "category -> updateCategory");
        category.setId(id);
        return categoryRepository.saveAndFlush(category);
    }

    @Override
    public void deleteCategory(int id) {
        if(!categoryRepository.existsById(id)){
            throw new IllegalArgumentException("No category with such id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
