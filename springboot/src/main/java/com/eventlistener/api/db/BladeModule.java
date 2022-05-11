package com.eventlistener.api.db;

import org.springframework.data.annotation.Id;

public class BladeModule {

  @Id
  public String id;
  public String address;
  public String moduleName;
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