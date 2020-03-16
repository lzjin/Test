package com.danqiu.myapplication.nio;

import com.danqiu.myapplication.netty.HeartbeatClient;
import com.danqiu.myapplication.utils.MLog;

import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by lzj on 2020/3/16
 * Describe ：注释
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {
    private static final int INTERVAL_TIME_SECOND= 1000*5;
    private long lastClickTime=0;

    /**
     * 接收到消息的时候触发
     * Object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) {
//        CustomProtocol objBean = (CustomProtocol) obj;
//        MLog.e("test","--------------0s接收到服务器消息："+objBean.getContent());
//        if (objBean.getContent().contains("关闭")) {
//            ctx.channel().closeFuture();
//        }
        String msg=((ByteBuf)obj).toString(CharsetUtil.UTF_8).trim();
        MLog.e("test","--------------0s接收到服务器消息："+msg);
        HeartbeatClient.getInstance().sendMsg(msg+"==二次回复");

       // ReferenceCountUtil.release(msg);
    }

    /**
     * 接收到服务器发送消息的时候触发
     * byte
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object objMsg) throws Exception {
        String msg=((ByteBuf)objMsg).toString(CharsetUtil.UTF_8).trim();
        MLog.e("test","--------------0b接收到服务器消息："+msg);
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        MLog.e("test", "-------断开连接马上5秒后重连------");
        TimeUnit.SECONDS.sleep(5);
        HeartbeatClient.getInstance().start();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        MLog.e("test", "-----出现异常!"+cause.getMessage());
       // TimeUnit.SECONDS.sleep(5);
        //nettyClient.startNetty();
        cause.printStackTrace();
        //ctx.close();
    }

    /**
     * 心跳检测
     * IdleStateHandler设置超时触发
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
       // super.userEventTriggered(ctx, evt);
        if(evt instanceof IdleStateEvent){
            IdleStateEvent e= (IdleStateEvent) evt;
            switch (e.state()){
                case WRITER_IDLE:
                    long currentTime=System.currentTimeMillis();
                    if(currentTime-lastClickTime>INTERVAL_TIME_SECOND){
                        MLog.e("test","--------------发送心跳包检查数据");
                        lastClickTime=System.currentTimeMillis();
                        ByteBuf byf= Unpooled.wrappedBuffer("心跳包".getBytes(CharsetUtil.UTF_8));
                        ctx.writeAndFlush(byf);
                    }else {
                        MLog.e("test","--------------心跳包时间未到");
                    }
                    break;
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        MLog.e("test", "=====连接成功回调=====");
    }
}
