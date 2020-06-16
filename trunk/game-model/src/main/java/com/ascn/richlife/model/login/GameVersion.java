package com.ascn.richlife.model.login;


/**
 * 游戏版本
 */
public class GameVersion {

    /**
     * 版本id
     */
    private int version_id;

    /**
     * 当前版本号
     */
    private String versionCode;

    /**
     * 兼容版本号
     */
    private String compatible;

    /**
     * 下载地址
     */
    private String androidDownload;

    /**
     * ios下载
     */
    private String iosDownload;

    /**
     * 服务器ip
     */
    private String serverIP;

    /**
     * 服务器端口
     */
    private String serverPort;

    /**
     * 是否更新
     */
    private int isUpdate;

    /**
     * 创建时间
     */
    private String create_time;

    /**
     * 状态
     */
    private int status;

    public int getVersion_id() {
        return version_id;
    }

    public void setVersion_id(int version_id) {
        this.version_id = version_id;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public String getCompatible() {
        return compatible;
    }

    public void setCompatible(String compatible) {
        this.compatible = compatible;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getAndroidDownload() {
        return androidDownload;
    }

    public void setAndroidDownload(String androidDownload) {
        this.androidDownload = androidDownload;
    }

    public String getIosDownload() {
        return iosDownload;
    }

    public void setIosDownload(String iosDownload) {
        this.iosDownload = iosDownload;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    @Override
    public String toString() {
        return "GameVersion{" +
                "version_id=" + version_id +
                ", versionCode='" + versionCode + '\'' +
                ", compatible='" + compatible + '\'' +
                ", androidDownload='" + androidDownload + '\'' +
                ", iosDownload='" + iosDownload + '\'' +
                ", serverIP='" + serverIP + '\'' +
                ", serverPort='" + serverPort + '\'' +
                ", isUpdate=" + isUpdate +
                ", create_time='" + create_time + '\'' +
                ", status=" + status +
                '}';
    }
}
