package com.project.myproject.dao;

import com.project.myproject.entity.Category;
import com.project.myproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query( value = "select c from Category c " +
            "join fetch c.tasks t " +
            "where c.name like :name " +
            "and not exists (select th from t.hidden th where th.username like :username)")
    Category findTasksByCategoryNameAndNotExistsUsername(String name, String username);


    @Query(value = "select c from Category c where c.name like :name")
    Category findByName(String name);
}
