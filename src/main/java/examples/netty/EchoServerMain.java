package examples.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class EchoServerMain {

    public static void main(String[] params) throws Exception {
        new EchoServerMain(1234).run();
    }

    private int port;

    public EchoServerMain(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
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
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind(port).sync(); // (7)
            f.channel().closeFuture().sync();
        } finally {
            childGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }
    }

}