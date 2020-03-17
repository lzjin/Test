package com.danqiu.myapplication.netty;


import android.util.Log;

import com.danqiu.myapplication.nio.NettyClientHandler;
import com.danqiu.myapplication.utils.MLog;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;


/**
 * Created by haoxy on 2018/10/17.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */

public class HeartbeatClient {
    private EventLoopGroup mWorkerGroup = new NioEventLoopGroup();
    private int nettyPort=11211;
    //private String host="10.0.255.169";
    private String host="10.0.255.228";
    private  Channel channel;
    private  ChannelFuture future;

    private static HeartbeatClient INSTANCE;

    private HeartbeatClient() {
    }

    public static HeartbeatClient getInstance() {
        if (INSTANCE == null) {
            synchronized (HeartbeatClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HeartbeatClient();
                }
            }
        }
        return INSTANCE;
    }

    public void start() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(mWorkerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new IdleStateHandler(3,5,8));
                        //channel.pipeline().addLast(new ObjectDecoder(1024 * 1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                        // 设置发送消息编码器
                        //  channel.pipeline().addLast(new ObjectEncoder());
                        channel.pipeline().addLast(new NettyClientHandler());
                    }
                });
        /*
         * 启动客户端
         */
        try {
            future = bootstrap.connect(host, nettyPort).sync();
            if (future.isSuccess()) {
                MLog.e("test", "--------------启动 Netty 成功");
                channel = future.channel();
                sendMsg("首次连接");
            } else {
                MLog.i("test", "-------------启动 Netty 失败,5秒后重试");
                TimeUnit.SECONDS.sleep(5);
                start();
            }
        }catch (Exception e){
            MLog.i("test", "-------------连接异常,拒绝访问  5秒后重试");
            TimeUnit.SECONDS.sleep(5);
            start();
        }

    }

    public void sendMsg(String msg){
        if (channel == null) {
            Log.e("test", "send: channel is null");
            return;
        }

        if (!channel.isWritable()) {
            Log.e("test", "send: channel is not Writable");
            return;
        }

        if (!channel.isActive()) {
            Log.e("test", "send: channel is not active!");
            return;
        }
        Log.e("test", "------------客服端发送："+msg);
        ByteBuf byf= Unpooled.wrappedBuffer(msg.getBytes((CharsetUtil.UTF_8)));
        channel.writeAndFlush(byf);

//        CustomProtocol customProtocol = new CustomProtocol("1234","我是客服端发送："+msg);
//        channel.writeAndFlush(customProtocol);
    }


    public void shutDown() {
        if (future != null && future.isSuccess()) {
            future.channel().closeFuture();
            if(mWorkerGroup!=null)
                mWorkerGroup.shutdownGracefully();
        }
    }
}