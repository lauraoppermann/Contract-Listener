package com.eventlistener.api.db;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends MongoRepository<BladeModule, String> {
    public List<BladeModule> findByModuleName(String moduleName);

    public List<BladeModule> findByModuleType(String moduleType);
}
