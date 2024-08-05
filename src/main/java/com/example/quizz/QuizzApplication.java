package com.example.quizz;

import com.example.quizz.Entity.User;
import com.example.quizz.Enum.GenderEnum;
import com.example.quizz.Repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

@SpringBootApplication
public class QuizzApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	private final Random random = new Random();

	public static void main(String[] args) {
		SpringApplication.run(QuizzApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker();

		for (int i = 0; i < 10; i++) {
			GenderEnum gender = getRandomGender();

			User user = User.builder()
					.name(faker.name().fullName())
					.age(faker.number().numberBetween(18, 80))
					.gender(gender)
					.email(faker.internet().emailAddress())
					.password(faker.internet().password())
					.refreshToken(faker.internet().uuid())
					.refreshExpireTime(faker.date().future(30, java.util.concurrent.TimeUnit.DAYS).toInstant())
					.createdAt(faker.date().past(30, java.util.concurrent.TimeUnit.DAYS).toInstant())
					.updatedAt(faker.date().past(10, java.util.concurrent.TimeUnit.DAYS).toInstant())
					.deletedAt(faker.date().past(5, java.util.concurrent.TimeUnit.DAYS).toInstant())
					.build();

			userRepository.save(user);
		}
//		for ()
	}

	private GenderEnum getRandomGender() {
		return GenderEnum.values()[random.nextInt(GenderEnum.values().length)];
	}


	}

