package com.eventlistener.api.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Setter;

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

  public BladeApp(String appID) {
    this.appID = appID;
  }

  public BladeApp(String appID, String appName, String appURL, String appOwnerID, String appVersionID) {
    this.appID = appID;
    this.appName = appName;
    this.appURL = appURL;
    this.appOwnerID = appOwnerID;
    this.appVersionID = appVersionID;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getAppURL() {
    return appURL;
  }

  public void setAppURL(String appURL) {
    this.appURL = appURL;
  }

  public String getAppOwnerID() {
    return appOwnerID;
  }

  public void setAppOwnerID(String appOwnerID) {
    this.appOwnerID = appOwnerID;
  }

  public String getAppVersionID() {
    return appVersionID;
  }

  public void setAppVersionID(String appVersionID) {
    this.appVersionID = appVersionID;
  }

  @Override
  public String toString() {
    return String.format(
        "Module[appID=%s, appName=%s appURL='%s', appOwnerID='%s', appVersionID='%s']",
        appID, appName, appURL, appOwnerID, appVersionID);
  }

}