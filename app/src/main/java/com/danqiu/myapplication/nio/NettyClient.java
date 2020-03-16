package com.danqiu.myapplication.nio;

import android.util.Log;

import com.danqiu.myapplication.netty.CustomerHandleInitializer;
import com.danqiu.myapplication.utils.MLog;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * Created by lzj on 2020/3/16
 * Describe ：注释
 */
public class NettyClient {
    private int nettyPort=11211;
    private String host="10.0.255.169";
    private EventLoopGroup mWorkerGroup;
    private  ChannelFuture future;
    private Channel channel;
    private  boolean isSuccess=false;

   // private static NettyClient INSTANCE;

//    private NettyClient() {
//    }
//
//    public static NettyClient getInstance() {
//        if (INSTANCE == null) {
//            synchronized (NettyClient.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new NettyClient();
//                }
//            }
//        }
//        return INSTANCE;
//    }

    public  void  startNetty() throws InterruptedException {
        MLog.i("test","--------------长连接开始");
        if(start()){
            MLog.i("test","--------------长连接成功");
            ByteBuf byf= Unpooled.wrappedBuffer(("客服端连接成功，发送的测试消息").getBytes((CharsetUtil.UTF_8)));
            channel.writeAndFlush(byf);
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
        ByteBuf byf= Unpooled.wrappedBuffer(msg.getBytes((CharsetUtil.UTF_8)));
        channel.writeAndFlush(byf);
    }

    private boolean start() throws InterruptedException {
        mWorkerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(mWorkerGroup)
                 .channel(NioSocketChannel.class)
                 // .option(ChannelOption.SO_KEEPALIVE,true)
                // .remoteAddress(host,nettyPort)
                 .handler(new CustomerHandleInitializer());
//                 .handler(new ChannelInitializer<Channel>() {
//                     @Override
//                     protected void initChannel(Channel channel) throws Exception {
//                         //channel.pipeline().addLast(new IdleStateHandler(5,0,0));
//                         channel.pipeline().addLast(new NettyClientHandler());
//                     }
//                 });

        /*
         * 启动客户端
         */
        future=bootstrap.connect(host, nettyPort).sync();
        if (future.isSuccess()) {
            MLog.i("test","--------------启动 Netty 成功");
        }
//        channel = future.channel();
//        while (channel.isActive()) {
//            if (num < 4) {
//                CustomProtocol customProtocol = new CustomProtocol();
//                customProtocol.setId("785");
//                customProtocol.setContent("客户端发送."+System.currentTimeMillis());
//                MLog.i("test","--------------客户端发送消息:"+System.currentTimeMillis());
//                TimeUnit.SECONDS.sleep(2);
//                channel.writeAndFlush(customProtocol);
//            }
//            num++;
//
//        }

        if(future!=null&&future.isSuccess()){
            channel= future.channel();
            MLog.i("test","-------------连接服务器  成功");
            return true;
        }else {
            MLog.i("test","-------------连接服务器  失败");
            TimeUnit.SECONDS.sleep(2);
            startNetty();
            return false;
        }

       // return isSuccess;
//        MLog.i("test","-------------连接future="+future);
//        if(future!=null&&future.isSuccess()){
//            socketChannel= (SocketChannel) future.channel();
//            MLog.i("test","-------------连接服务器  成功");
//            return true;
//        }else {
//            MLog.i("test","-------------连接服务器  失败");
//            TimeUnit.SECONDS.sleep(2);
//            startNetty();
//            return false;
//        }


//        try {
//            future=null;
//            future=bootstrap.connect(host, nettyPort).sync();
//
//            MLog.i("test","-------------连接future="+future);
//            if(future!=null&&future.isSuccess()){
//                socketChannel= (SocketChannel) future.channel();
//                MLog.i("test","-------------连接服务器  成功");
//                return true;
//            }else {
//                MLog.i("test","-------------连接服务器  失败");
//                startNetty();
//                return false;
//            }
//        }catch (Exception e){
//            MLog.i("test","-------------无连接  5秒后重试");
//            TimeUnit.SECONDS.sleep(5);
//            startNetty();
//            return false;
//        }

    }

    public void shutDown() {
        if (future != null && future.isSuccess()) {
            future.channel().closeFuture();
            if(mWorkerGroup!=null)
            mWorkerGroup.shutdownGracefully();
        }
    }
}
