package com.project.myproject.dao;

import com.project.myproject.entity.Task;
import com.project.myproject.enums.ECountry;

import java.util.List;

public interface TaskRepositoryCustom {

    List<Task> findAllByGeoFilterByCountriesAndNotExistsUsername(ECountry country, String username);

}
