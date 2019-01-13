package cn.haoxiaoyong.netty.netty;

import cn.haoxiaoyong.netty.model.ChatRecord;
import cn.haoxiaoyong.netty.service.ChatRecordService;
import cn.haoxiaoyong.netty.util.SpringUtil;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by haoxy on 2019/1/7.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:MM");

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String content = textWebSocketFrame.text();
        System.out.println("接收到的数据: " + content);

        Message message = JSON.parseObject(content, Message.class);
        ChatRecordService chatRecordService = SpringUtil.getBean(ChatRecordService.class);
        switch (message.getType()) {
            //处理客户端链接的消息
            case 0:
                //建立用户和通道之间的关系
                UserChannelMap.put(message.getChatRecord().getUserid(), channelHandlerContext.channel());
                System.out.println(message.getChatRecord().getUserid() + "与" + channelHandlerContext.channel().id() + "建立了关联");
                UserChannelMap.print();
                break;
            case 1:
                //将消息保存到数据库
                ChatRecord chatRecord = message.getChatRecord();
                chatRecordService.insert(chatRecord);
                //查看此好友是否在线，如果在线就将消息发送给此好友
                //1.根据好友id,查询此通道是否存在
                Channel channel = UserChannelMap.get(chatRecord.getFriendid());
                if (channel != null) {
                    channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
                } else {
                    System.out.println("用户" + chatRecord.getFriendid() + "不在线");
                }
                break;
        }
        /*//将接收的消息发送所有的客户端
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame(sdf.format(new Date()) + ":" + content));
        }*/
    }

    //当有新的客户端连接服务器之后,就会自动调用这个方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
    }

    //出现异常是关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //根据通道id取消用户和通道的关系
        UserChannelMap.removeByChannelId(ctx.channel().id().asLongText());
        ctx.channel().close();
        System.out.println("出现异常.....关闭通道!");
    }

    //当客户端关闭链接时关闭通道
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("关闭通道");
        UserChannelMap.removeByChannelId(ctx.channel().id().asLongText());
        ctx.channel().close();
        UserChannelMap.print();
    }
}
