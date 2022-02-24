package com.project.myproject.dao;

import com.project.myproject.entity.Country;
import com.project.myproject.enums.ECountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query(value = "select c from Country c where c.name in(:countries) ")
    List<Country> findAllByNameIn(List<ECountry> countries);
}
