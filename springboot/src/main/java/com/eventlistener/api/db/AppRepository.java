package com.eventlistener.api.db;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends MongoRepository<BladeApp, String> {
    public List<BladeApp> findByAppName(String appName);

}
