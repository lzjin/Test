package com.danqiu.myapplication.nio;

import com.danqiu.myapplication.netty.HeartbeatClient;
import com.danqiu.myapplication.utils.MLog;

import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
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
//        try {
//            TimeUnit.SECONDS.sleep(6);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
       // HeartbeatClient.getInstance().sendMsg(msg+"==二次回复");
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
        MLog.e("test", "-------出现异常----"+cause.getMessage());
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
                case READER_IDLE://间隔x秒读取服务器
                    MLog.e("test","--------心跳检测------读取");
                    break;
                case WRITER_IDLE://间隔x秒写入服务器
                    MLog.e("test","--------心跳检测------发送");
                    HeartbeatClient.getInstance().sendMsg("心跳包");
                    break;
                case ALL_IDLE://所有类型的超时时间
                    MLog.e("test","--------心跳检测------所有类型");
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
