package com.eventlistener.api;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, IOException, Exception {
		SpringApplication.run(Application.class, args);
	}

}