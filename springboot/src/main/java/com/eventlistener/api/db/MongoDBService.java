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
    private ModuleRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public BladeModule addModule(BladeModule module) {

        repository.save(module);

        return module;
    }

    public List<BladeModule> findAll() {
        return repository.findAll();
    }

    public List<BladeModule> findByModuleName(String moduleName) {
        return repository.findByModuleName(moduleName);
    }

    public List<BladeModule> findByModuleType(String moduleType) {
        return repository.findByModuleType(moduleType);
    }

    public List<BladeModule> fullTextSearch(String searchPhrase) {
        TextCriteria criteria = TextCriteria
                .forLanguage("en")
                .matchingAny(searchPhrase);

        Query query = TextQuery.queryText(criteria).sortByScore();

        List<BladeModule> modules = mongoTemplate.find(query, BladeModule.class);

        return modules;
    }
}
