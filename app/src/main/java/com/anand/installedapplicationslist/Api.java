package com.anand.installedapplicationslist;

public class Api {

    private static final String ROOT_URL = "http://192.168.13.128/HeroApi/Api/Api.php?apicall=";

    //Read the app details for the particular app id
    public static final String URL_READ_HEROES = ROOT_URL + "getheroes&id=";
    //Read the existing permission for the particular app id
    public static final String URL_READ_HEROESID = ROOT_URL + "deletehero&id=";

    public static final String URL_READ_PERMISSION = ROOT_URL + "getpermission";

    public static final String URL_READ_METHOD = ROOT_URL + "getmethod&id=";


}
