package com.example.springboot;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import su.interference.core.Instance;
import su.interference.exception.InternalException;
import su.interference.persistent.Session;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws NoSuchMethodException, InternalException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, IOException, Exception {
		SpringApplication.run(Application.class, args);
		Instance instance = Instance.getInstance();
		Session session = Session.getSession();
		session.setUserId(1); // seems incorrect and insecure?
		instance.startupInstance(session);
	}

}