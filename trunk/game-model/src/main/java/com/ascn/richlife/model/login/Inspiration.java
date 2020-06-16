package com.ascn.richlife.model.login;

/**
 * 梦想板
 */
public class Inspiration {

    /**
     * id
     */
    private String id;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 发布时间
     */
    private String createTime;

    /**
     * 发布内容
     */
    private String content;

    /**
     * 审核状态
     */
    private int status;

    public Inspiration() {
    }

    public Inspiration(String id, String avatar, String nickName, String createTime, String content, int status) {
        this.id = id;
        this.avatar = avatar;
        this.nickName = nickName;
        this.createTime = createTime;
        this.content = content;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Inspiration{" +
                "id='" + id + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nickName='" + nickName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                '}';
    }
}
