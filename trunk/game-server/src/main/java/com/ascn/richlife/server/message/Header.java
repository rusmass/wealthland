package com.ascn.richlife.server.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义消息传输消息头
 *
 * Created by zhangpengxiang on 17/3/10.
 */
public class Header {

    /**
     * 消息类型
     */
    private int type;
    /**
     * 玩家id
     */
    private String playerId;

    /**
     * 附件，保证头可扩展
     */
    private Map<String,Object> attachment = new HashMap<String, Object>();

    public int getType() {
        return type;
    }

    public Header setType(int type) {
        this.type = type;
        return this;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Header setPlayerId(String playerId) {
        this.playerId = playerId;
        return this;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public Header setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
        return this;
    }

    @Override
    public String toString() {
        return "Header{" +
                "type=" + type +
                ", playerId='" + playerId + '\'' +
                ", attachment=" + attachment +
                '}';
    }
}
