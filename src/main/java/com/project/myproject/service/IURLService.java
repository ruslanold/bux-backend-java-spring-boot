package com.project.myproject.service;

import com.project.myproject.entity.URL;
import java.util.List;

public interface IURLService {

    List<URL> createAllUrls(List<String> urls);

}
