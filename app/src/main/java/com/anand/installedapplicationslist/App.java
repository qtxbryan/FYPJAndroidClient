package com.anand.installedapplicationslist;

public class App {

    private String appId;
    private String title;
    private String url;
    private String devId;

    public App(String appId, String title, String url, String devId){

        this.appId = appId;
        this.title = title;
        this.url = url;
        this.devId = devId;

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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
