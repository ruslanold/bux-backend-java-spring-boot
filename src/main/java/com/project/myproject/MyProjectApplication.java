package com.project.myproject;

import com.project.myproject.dao.UserRepository;
import com.project.myproject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyProjectApplication.class, args);
	}
}
