package com.eventlistener.api.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoDBService {
    @Autowired
    private ModuleRepository repository;

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
}
