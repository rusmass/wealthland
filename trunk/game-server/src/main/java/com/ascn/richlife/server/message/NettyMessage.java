package com.ascn.richlife.server.message;

import java.util.Map;

/**
 * netty消息模型
 *
 * Created by zhangpengxiang on 17/3/10.
 */
public class NettyMessage {

    /**
     * 消息头
     */
    private Header header;

    /**
     * 消息体
     */
    private Map<String,Object> body;

    public Header getHeader() {
        return header;
    }

    public NettyMessage setHeader(Header header) {
        this.header = header;
        return this;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public NettyMessage setBody(Map<String, Object> body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }

}

