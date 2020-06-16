package com.ascn.richlife.model.card;

/**
 * Created by Administrator on 2018/6/25 0025.
 */
public class CardStudy {

    /**
     * 学习卡牌id
     */
    private int id;

    /**
     * 学习卡牌标题
     */
    private String type;

    /**
     * 学习卡牌内容
     */
    private String content;

    /**
     * 学习卡牌创建时间
     */
    private String create_time;

    /**
     * 学习卡牌修改时间
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

    @Override
    public String toString() {
        return "CardStudy{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", create_time='" + create_time + '\'' +
                ", modify_time='" + modify_time + '\'' +
                '}';
    }
}
