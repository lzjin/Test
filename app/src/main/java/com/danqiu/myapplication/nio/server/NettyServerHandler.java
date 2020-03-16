package com.danqiu.myapplication.nio.server;

import android.util.Log;

import com.danqiu.myapplication.utils.MLog;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by lzj on 2020/3/16
 * Describe ：注释
 */
public class NettyServerHandler  extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        Log.d("test", "-------检测客服端疑似掉线-----");
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Log.d("test", "-----服务器出现异常!");
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object objMsg) throws Exception {
        String msg=((ByteBuf)objMsg).toString(CharsetUtil.UTF_8).trim();
        MLog.i("test","--------------我是服务器收到客服消息："+msg);
        ByteBuf byf= Unpooled.wrappedBuffer(("我是服务器:"+msg+",回复了").getBytes((CharsetUtil.UTF_8)));
        channelHandlerContext.writeAndFlush(byf);
    }
}
