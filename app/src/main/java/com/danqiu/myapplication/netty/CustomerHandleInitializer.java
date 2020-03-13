package com.danqiu.myapplication.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by haoxy on 2018/10/17.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class CustomerHandleInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ObjectDecoder(1024 * 1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
        // 设置发送消息编码器
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new EchoClientHandle());
    }
}
