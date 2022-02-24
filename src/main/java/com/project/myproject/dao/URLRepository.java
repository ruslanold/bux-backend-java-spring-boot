package com.project.myproject.dao;

import com.project.myproject.entity.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface URLRepository extends JpaRepository<URL, Long> {

    List<URL> findAllByAddressIn(List<String> urls);

}
