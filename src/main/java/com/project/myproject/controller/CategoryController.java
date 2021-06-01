package com.project.myproject.controller;

import com.project.myproject.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
}
