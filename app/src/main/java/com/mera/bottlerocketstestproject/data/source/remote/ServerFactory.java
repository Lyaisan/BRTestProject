package com.mera.bottlerocketstestproject.data.source.remote;


class ServerFactory {
    private static final String PROTOCOL = "http://";
    private static final String SERVER_URL = "sandbox.bottlerocketapps.com/";

    public static String getUrl() {
        return getProtocol() + getServerUrl();
    }

    private static String getProtocol(){
        return PROTOCOL;
    }

    private static String getServerUrl(){
        return SERVER_URL;
    }
}
