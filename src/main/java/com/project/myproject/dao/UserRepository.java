package com.project.myproject.dao;

import com.project.myproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select u from User u join fetch u.roles where u.username like :username")
    User findByUsername(String username);

    @Query(value = "select u from User u where u.email = :email")
    User findByEmail(String email);

    Boolean existsByEmail(String email);
}
