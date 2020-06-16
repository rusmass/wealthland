package com.ascn.richlife.model.login;

/**
 * 游戏公告
 */
public class GameNotice{

    /**
     * id
     */
    private int id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 类型  0代表文字公告 1代表图文公告
     */
    private int type;

    /**
     * 公告时间
     */
    private String time;

    /**
     * 跳转地址
     */
    private String target_url;

    /**
     * 公告优先级
     */
    private int priority;

    public int getId() {
        return id;
    }

    public GameNotice setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public GameNotice setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public GameNotice setContent(String content) {
        this.content = content;
        return this;
    }

    public int getType() {
        return type;
    }

    public GameNotice setType(int type) {
        this.type = type;
        return this;
    }

    public String getTime() {
        return time;
    }

    public GameNotice setTime(String time) {
        this.time = time;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTarget_url() {
        return target_url;
    }

    public void setTarget_url(String target_url) {
        this.target_url = target_url;
    }

    @Override
    public String toString() {
        return "GameNotice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", time='" + time + '\'' +
                ", target_url='" + target_url + '\'' +
                ", priority=" + priority +
                '}';
    }

}
