package com.danqiu.myapplication.netty;


import com.danqiu.myapplication.utils.MLog;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * Created by haoxy on 2018/10/17.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * <p>
 * EchoClientHandle继承了 ChannelInboundHandlerAdapter 的一个扩展(SimpleChannelInboundHandler),
 * 而ChannelInboundHandlerAdapter是ChannelInboundHandler的一个实现
 * ChannelInboundHandler提供了可以重写的各种事件处理程序方法
 * 目前，只需继承 SimpleChannelInboundHandler或ChannelInboundHandlerAdapter 而不是自己实现处理程序接口。
 * 我们在这里重写了channelRead0（）事件处理程序方法
 */

public class EchoClientHandle extends SimpleChannelInboundHandler {


    /**
     * 接收到消息的时候触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        CustomProtocol customProtocol = (CustomProtocol) obj;
        //从服务端收到消息时被调用
        MLog.i("test","--------------客户端收到消息:{}"+customProtocol.getContent());
        String content = customProtocol.getContent();
        if (content.contains("关闭")) {
            MLog.i("test","-------------server closed connection , so client will close too");
            ctx.channel().closeFuture();
        }
    }


    /**
     * 每当从服务端接收到新数据时，都会使用收到的消息调用此方法 channelRead0(),在此示例中，接收消息的类型是ByteBuf。
     *
     * @param ctx
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object obj) {
    }
}
