package examples.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class EchoClient {

    private final String ip;
    private final int port;

    EchoClient(String ip, int port) {
        this.port = port;
        this.ip = ip;
    }

    public void connectRunAndExit() throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ctx.writeAndFlush(Unpooled.wrappedBuffer("ABC\n".getBytes(CharsetUtil.UTF_8)));
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            System.out.println("Client:" + ((ByteBuf)msg).toString(Charset.defaultCharset()));
                            ctx.writeAndFlush(Unpooled.wrappedBuffer("exit\n".getBytes(CharsetUtil.UTF_8)));
                            ctx.close();
                        }
                    });
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(ip, port).sync(); // (5)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }


}
