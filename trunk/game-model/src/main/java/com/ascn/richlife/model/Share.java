package com.ascn.richlife.model;

/**
 * 游戏分享信息
 */
public class Share {

    /**
     * 分享标题
     */
    private String title;

    /**
     * 分享文本内容
     */
    private String txt;

    /**
     * 分享图片地址
     */
    private String address;

    /**
     * 分享网址
     */
    private String weburl;

    public Share(){}

    public Share(String title, String txt, String address, String weburl) {
        this.title = title;
        this.txt = txt;
        this.address = address;
        this.weburl = weburl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }
}
