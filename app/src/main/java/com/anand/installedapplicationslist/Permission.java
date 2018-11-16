package com.anand.installedapplicationslist;

public class Permission {

    private String appId;
    private String permName;
    private String protectLevel;

    public Permission(String appId, String permName, String protectLevel) {
        this.appId = appId;
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
}
