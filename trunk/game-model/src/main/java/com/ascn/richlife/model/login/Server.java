package com.ascn.richlife.model.login;


/**
 * 服务器模型
 */
public class Server {

    /**
     * 服务器ID
     */
    private String ServerId;

    /**
     * 服务器名字
     */
    private String ServerName;

    /**
     * 服务器版本
     */
    private String versionCode;

    public Server(){}

    public Server(String serverId, String serverName, String versionCode) {
        ServerId = serverId;
        ServerName = serverName;
        this.versionCode = versionCode;
    }

    public String getServerId() {
        return ServerId;
    }

    public void setServerId(String serverId) {
        ServerId = serverId;
    }

    public String getServerName() {
        return ServerName;
    }

    public void setServerName(String serverName) {
        ServerName = serverName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    @Override
    public String toString() {
        return "Server{" +
                "ServerId='" + ServerId + '\'' +
                ", ServerName='" + ServerName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                '}';
    }
}
