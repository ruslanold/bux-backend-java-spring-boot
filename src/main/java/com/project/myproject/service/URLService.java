package com.project.myproject.service;

import com.project.myproject.dao.URLRepository;
import com.project.myproject.entity.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class URLService implements IURLService{

    @Autowired
    private URLRepository urlRepository;


    @Override
    public List<URL> createAllUrls(List<String> urls) {
        List<URL> foundUrls = urlRepository.findAllByAddressIn(urls);

        List<URL> urlsList = urls.stream().map(a -> {
            URL url = new URL();
            url.setAddress(a);
            return url;
        }).collect(Collectors.toList());

        if(foundUrls.isEmpty()) {
            return urlRepository.saveAll(urlsList);
        }

        List<String> urlAddresses = foundUrls.stream().map(URL::getAddress).collect(Collectors.toList());

        List<URL> filteredUrls = urlsList.stream().filter(u -> !urlAddresses.contains(u.getAddress())).collect(Collectors.toList());

        List<URL> resultList = new ArrayList<>();
        resultList.addAll(foundUrls);

        if(!filteredUrls.isEmpty()) {
            List<URL> savedUrls = urlRepository.saveAll(filteredUrls);
            resultList.addAll(savedUrls);
        }

        return resultList;
    }



}
