package com.danqiu.myapplication.nio.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by lzj on 2020/3/16
 * Describe ：注释
 */
public class NettyServer {
    private int port;

    public NettyServer(int port) throws InterruptedException {
        this.port = port;
        bind();
    }

    private void bind() throws InterruptedException {
        EventLoopGroup mBoss = new NioEventLoopGroup();
        EventLoopGroup mWorkerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(mBoss, mWorkerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new NettyServerHandler());
                    }
                });
        ChannelFuture future=bootstrap.bind(port).sync();
        if(future.isSuccess()){
            //MLog.i("test","--------------服务器启动成功");
        }

    }

    public static void main(String []args) throws InterruptedException{
        NettyServer nettyServer=new NettyServer(11211);
    }

}
