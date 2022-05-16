package com.eventlistener.api.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bladeApp")
public class BladeApp {

  @Id
  public String appID;
  @TextIndexed(weight = 3)
  public String appName;
  public String appURL;
  public String appOwnerID;
  public String appVersionID;

  public BladeApp() {
  }

  public BladeApp(String appID, String appName, String appURL, String appOwnerID, String appVersionID) {
    this.appID = appID;
    this.appName = appName;
    this.appURL = appURL;
    this.appOwnerID = appOwnerID;
    this.appVersionID = appVersionID;
  }

  @Override
  public String toString() {
    return String.format(
        "Module[appID=%s, appName=%s appURL='%s', appOwnerID='%s', appVersionID='%s']",
        appID, appName, appURL, appOwnerID, appVersionID);
  }

}