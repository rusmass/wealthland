package com.ascn.richlife.model.card;

/**
 * Created by Administrator on 2018/6/26 0026.
 */
public class CardCharity {

    /**
     * 慈善卡牌id
     */
    private int id;

    /**
     * 慈善卡牌
     */
    private String type;

    /**
     * 慈善卡牌内容
     */
    private String content;

    /**
     * 创建时间
     */
    private String create_time;

    /**
     * 修改时间
     */
    private String modify_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }
}
