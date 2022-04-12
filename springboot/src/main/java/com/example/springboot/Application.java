package com.example.springboot;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import su.interference.exception.InternalException;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws NoSuchMethodException, InternalException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, IOException, Exception {
		SpringApplication.run(Application.class, args);

		// I.O. Config as DB --> seems to be not as known

		// Instance instance = Instance.getInstance();
		// Session session = Session.getSession();
		// session.setUserId(1); // seems incorrect and insecure?
		// instance.startupInstance(session);

		// Session s = Session.getSession();
		// AppEntity app = (AppEntity) session.newEntity(AppEntity.class, new Object[]
		// {});
		// app.setAppName("TestName", session);
		// app.setAppType("test", session);
		// app.setAppId(1, session);
		// s.persist(app);
		// s.commit();

	}

}