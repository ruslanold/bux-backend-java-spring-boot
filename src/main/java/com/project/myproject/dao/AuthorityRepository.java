package com.project.myproject.dao;

import com.project.myproject.entity.Authority;
import com.project.myproject.enums.EAthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    @Query(value = "select a from Authority a where a.name like :name")
    Authority findByName(EAthority name);
}
