package com.example.quizz;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class QuizzApplication implements CommandLineRunner {
//	private final com.example.quizz.UserFaker userFaker;

//	public QuizzApplication(com.example.quizz.UserFaker userFaker) {
//		this.userFaker = userFaker;
//	}
	@Autowired
	public static void main(String[] args) {
		SpringApplication.run(QuizzApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		userFaker.generateData();

	}

}



