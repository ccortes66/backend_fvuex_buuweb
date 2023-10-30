package com.vuex.example.vuex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class VuexApplication {

	public static void main(String[] args) {
		SpringApplication.run(VuexApplication.class, args);
	}

}
