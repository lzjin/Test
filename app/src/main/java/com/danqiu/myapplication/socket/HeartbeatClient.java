package com.danqiu.myapplication.socket;


import com.danqiu.myapplication.netty.CustomProtocol;
import com.danqiu.myapplication.netty.CustomerHandleInitializer;
import com.danqiu.myapplication.utils.MLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * Created by haoxy on 2018/10/17.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 *
 * https://blog.csdn.net/fjnu_se/article/details/90757870
 */

public class HeartbeatClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(HeartbeatClient.class);

    private EventLoopGroup group = new NioEventLoopGroup();


    private int nettyPort=11211;


    private String host="10.0.255.169";

    private SocketChannel socketChannel;

    int num = 0;


    public void start() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        /*
         * NioSocketChannel用于创建客户端通道，而不是NioServerSocketChannel。
         * 请注意，我们不像在ServerBootstrap中那样使用childOption()，因为客户端SocketChannel没有父服务器。
         */
        bootstrap.group(group).channel(NioSocketChannel.class).handler(new CustomerHandleInitializer());
        /*
         * 启动客户端
         */
        ChannelFuture future = bootstrap.connect(host, nettyPort).sync();
        if (future.isSuccess()) {
            MLog.i("test","-------------启动 Netty 成功");
        }else {
            MLog.i("test","-------------启动 Netty 失败");
        }
        socketChannel = (SocketChannel) future.channel();
        Channel channel = future.channel();
        while (channel.isActive()) {
            if (num < 4) {
                CustomProtocol customProtocol = new CustomProtocol();
                customProtocol.setId("785");
                customProtocol.setContent("客户端发送."+num);
                MLog.i("test","-------------客户端发送消息:"+customProtocol.getContent());
                channel.writeAndFlush(customProtocol);
            }
            num++;

        }

    }
}