package com.eventlistener.api.services;

import java.util.List;
import java.util.Optional;

import com.eventlistener.api.db.AppRepository;
import com.eventlistener.api.db.BladeApp;

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

    public void updateName(String appID, String appName) {
        Optional<BladeApp> a = repository.findById(appID);
        if (a.isPresent()) {
            BladeApp app = a.get();

            app.setAppName(appName);
            repository.save(app);

        }
    }

    public void updateURL(String appID, String appURL) {
        Optional<BladeApp> a = repository.findById(appID);
        if (a.isPresent()) {
            BladeApp app = a.get();

            app.setAppURL(appURL);
            repository.save(app);

        }
    }

    public void updateOwner(String appID, String appOwnerID) {
        Optional<BladeApp> a = repository.findById(appID);
        if (a.isPresent()) {
            BladeApp app = a.get();
            app.setAppOwnerID(appOwnerID);
            repository.save(app);
        }
    }

    public void updateVersion(String appID, String appVersionID) {
        Optional<BladeApp> a = repository.findById(appID);
        if (a.isPresent()) {
            BladeApp app = a.get();

            app.setAppVersionID(appVersionID);
            repository.save(app);

        }
    }

    public List<BladeApp> findAll() {
        return repository.findAll();
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
