package cn.haoxiaoyong.netty.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by haoxy on 2019/1/14.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class HearBeatHandler extends ChannelInboundHandlerAdapter {

    //触发用户事件
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {//读空闲
                //检测到读空闲不做任何的操作
                System.out.println("读空闲事件触发...");
            } else if (idleStateEvent.state() == IdleState.WRITER_IDLE) {//写空闲
                //检测到写空闲不做任何的操作
                System.out.println("写空闲事件触发...");
            } else if (idleStateEvent.state() == IdleState.ALL_IDLE) {//读写空闲
                System.out.println("--------------");
                System.out.println("读写空闲事件触发");
                System.out.println("关闭通道资源");
                ctx.channel().close();
            }
        }
    }
}
