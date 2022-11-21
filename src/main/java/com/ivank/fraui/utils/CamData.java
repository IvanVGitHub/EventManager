package com.ivank.fraui.utils;

public class CamData {
    private static CamData instance;
    public static CamData getInstance() {
        return instance;
    }

    private CamData camData;

    public CamData getCamData() {
        return camData;
    }

    public void setCamData(CamData camData) {
        this.camData = camData;
    }

    public String host;
    public String path;
    public String username;
    public String password;
    public int width, height, framerate;
    public String cameraName;

    public String getConnectionUrl(){
        return "rtsp://" + username + ":"+password + "@" + host + path;
    }

    public CamData(String host, String path, String username, String password, int width, int height, int framerate, String cameraName){
        this.host = host;
        this.path = path;
        this.username = username;
        this.password = password;
        this.width = width;
        this.height = height;
        this.framerate = framerate;
        this.cameraName = cameraName;
    }
    public CamData(String host, String path, String username, String password, String cameraName) {
        this(host, path, username, password, 1920, 1080, 25, cameraName);
        instance = this;
    }
    public CamData(String host, String path, String username, String password, int framerate, String cameraName) {
        this(host, path, username, password, 1920, 1080, framerate, cameraName);
        instance = this;
    }
    public CamData(String host, String path, String username, String password, int width, int height, String cameraName) {
        this(host, path, username, password, width, height, 25, cameraName);
        instance = this;
    }
}
