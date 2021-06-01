package com.project.myproject.dao;

import com.project.myproject.entity.Role;
import com.project.myproject.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = "select r from Role r where r.name like :name")
    Role findByName(ERole name);
}
