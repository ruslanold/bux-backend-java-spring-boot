package com.project.myproject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;

@SpringBootApplication
public class MyProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyProjectApplication.class, args);

		ZoneId z = ZoneId.systemDefault();
		System.out.println(z);
	}
}
