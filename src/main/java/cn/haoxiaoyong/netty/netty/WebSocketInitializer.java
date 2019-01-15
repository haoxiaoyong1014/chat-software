package cn.haoxiaoyong.netty.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by haoxy on 2019/1/7.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class WebSocketInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //-------------
        //用于支持 Http协议
        //-----------------

        //websocket基于 http协议,需要有 http 的编解码器
        pipeline.addLast(new HttpServerCodec())
                //对于大数据流的支持
                .addLast(new ChunkedWriteHandler())
                //添加对HTTP请求和响应的聚合器:只要使用Netty进行 http编码都需要使用到
                //对HttpMessage进行聚合,聚合成FullHttpRequest或者FullHttpResponse
                //在 netty 编程总都会使用到Handler
                .addLast(new HttpObjectAggregator(1024*64))
                .addLast(new WebSocketServerProtocolHandler("/ws"))
                //添加Netty空闲超时检查的支持
                //4:读空闲超时,8:写空闲超时,12: 读写空闲超时
                .addLast(new IdleStateHandler(4,8,12))
                .addLast(new HearBeatHandler())
                //添加自定有的 handler
                .addLast(new ChatHandler());




    }
}
