package com.ascn.richlife.server.codec;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ascn.richlife.server.message.NettyMessage;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 信息编码
 *
 * Created by zhangpengxiang on 17/3/10.
 */
public class MessageEncoder extends MessageToMessageEncoder<NettyMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {
        if (msg == null) {
            return;
        }
        String data = JSONObject.toJSONString(msg, SerializerFeature.DisableCircularReferenceDetect);
        String dataEncode = data+"$_";
        out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(dataEncode), Charset.forName("UTF-8")));
    }

    enum State{
        READ_STATE,

        READ_VALUE

    }

}
