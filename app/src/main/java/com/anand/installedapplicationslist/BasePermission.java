package com.anand.installedapplicationslist;

public class BasePermission {

    private String permId;
    private String permName;
    private String protectLevel;

    public BasePermission(String permId, String permName, String protectLevel) {
        this.permId = permId;
        this.permName = permName;
        this.protectLevel = protectLevel;
    }

    public String getPermId() {
        return permId;
    }

    public void setPermId(String permId) {
        this.permId = permId;
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
