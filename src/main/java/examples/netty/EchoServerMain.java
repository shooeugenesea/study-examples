package examples.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class EchoServerMain {

    public static void main(String[] params) throws Exception {
        new Thread(() -> {
            try {
                new EchoServer(1234).run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.SECONDS.sleep(1);
        new EchoClient("localhost", 1234).connectRunAndExit();
    }

    public static class EchoClient {

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
                b.handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new EchoClientHandler());
                    }
                });

                ChannelFuture f = b.connect(ip, port).sync();

                Channel ch = f.channel();
                System.out.println("scan...");
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextLine()) {
                    String msg = scanner.nextLine();
                    System.out.println("Input:" + msg);
                    ByteBuf msgBuffer = Unpooled.wrappedBuffer(msg.getBytes(CharsetUtil.UTF_8));
                    ch.writeAndFlush(msgBuffer);
                }


                // Wait until the connection is closed.
                f.channel().closeFuture().sync();
            } finally {
                workerGroup.shutdownGracefully().sync();
            }
        }
    }


    public static class EchoServer {
        private int port;

        public EchoServer(int port) {
            this.port = port;
        }

        public void run() throws Exception {
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(group)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline().addLast(new EchoServerHandler());
                            }
                        });

                ChannelFuture f = b.bind(port).sync();
                System.out.println("bind done");
                f.channel().closeFuture().sync();
                System.out.println("close done");
            } finally {
                group.shutdownGracefully().sync();
            }
        }
    }


}