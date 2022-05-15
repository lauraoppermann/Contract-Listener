package com.eventlistener.api.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bladeModule")
public class BladeModule {

  @Id
  public String id;
  public String address;
  @TextIndexed(weight = 3)
  public String moduleName;
  @TextIndexed(weight = 1)
  public String moduleType;

  public BladeModule() {
  }

  public BladeModule(String address, String moduleName, String moduleType) {
    this.address = address;
    this.moduleName = moduleName;
    this.moduleType = moduleType;
  }

  @Override
  public String toString() {
    return String.format(
        "Module[id=%s, address=%s moduleName='%s', moduleType='%s']",
        id, address, moduleName, moduleType);
  }

}