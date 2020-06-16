package com.ascn.richlife.server.codec;

import com.alibaba.fastjson.JSONObject;
import com.ascn.richlife.server.message.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 信息解码
 *
 * Created by zhangpengxiang on 17/3/10.
 */
public class MessageDecoder extends MessageToMessageDecoder<ByteBuf> {
    @SuppressWarnings("deprecation")
	@Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        if (msg.toString(Charset.defaultCharset()) == null || msg.toString(Charset.defaultCharset()).length() == 0) {
            return;
        }
        NettyMessage nettyMessage = JSONObject.parseObject( msg.toString(Charset.forName("UTF-8")), NettyMessage.class);
        out.add(nettyMessage);

    }
}
