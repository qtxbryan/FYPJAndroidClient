package com.anand.installedapplicationslist;

public class AppPermission {

    private String appId;
    private String title;
    private String url;
    private String devId;

    private String permName;
    private String protectLevel;

    public AppPermission(String appId, String title, String url, String devId, String permName, String protectLevel) {
        this.appId = appId;
        this.title = title;
        this.url = url;
        this.devId = devId;
        this.permName = permName;
        this.protectLevel = protectLevel;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public String getProtectLevel() {
        return protectLevel;
    }

    public void setProtectLevel(String protectLevel) {
        this.protectLevel = protectLevel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }
}
