package examples.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class SimpleServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        ch.writeAndFlush("Press exit to leave");
        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
            @Override
            public void channelActive(ChannelHandlerContext ctx) {
                System.out.println("connection join:" + ctx);
            }

            @Override
            public void channelInactive(ChannelHandlerContext ctx) {
                System.out.println("connection leave:" + ctx);
            }

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                ByteBuf in = (ByteBuf) msg;
                try {
                    String data = in.toString(CharsetUtil.UTF_8);
                    System.out.println("Server:" + data);
                    ctx.writeAndFlush(Unpooled.wrappedBuffer(data.getBytes(CharsetUtil.UTF_8)));
                    if (data.trim().equals("exit")) {
                        ctx.close();
                    }
                } finally {
                    ReferenceCountUtil.release(msg);
                }
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                cause.printStackTrace();
                ctx.close();
            }
        });
    }
}
