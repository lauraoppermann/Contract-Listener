package com.eventlistener.api.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

@Service
public class MongoDBService {
    @Autowired
    private AppRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public BladeApp addApp(BladeApp app) {

        repository.save(app);

        return app;
    }

    public List<BladeApp> findAll() {
        return repository.findAll();
    }

    public List<BladeApp> findByAppName(String appName) {
        return repository.findByAppName(appName);
    }

    public List<BladeApp> findByAppType(String appType) {
        return repository.findByAppType(appType);
    }

    public List<BladeApp> fullTextSearch(String searchPhrase) {
        TextCriteria criteria = TextCriteria
                .forLanguage("en")
                .matchingAny(searchPhrase);

        Query query = TextQuery.queryText(criteria).sortByScore();

        List<BladeApp> apps = mongoTemplate.find(query, BladeApp.class);

        return apps;
    }
}
