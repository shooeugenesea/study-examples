package examples.netty.streaming;

import examples.netty.echo.EchoClientHandler;
import examples.netty.echo.EchoServerHandler;
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

public class StreamingServerMain {

    public static void main(String[] params) throws Exception {
        new Thread(() -> {
            try {
                new StreamingServer(1234).run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.SECONDS.sleep(1);
        new StreamingClient("localhost", 1234).connectRun();
    }

    public static class StreamingClient {

        private final String ip;
        private final int port;

        StreamingClient(String ip, int port) {
            this.port = port;
            this.ip = ip;
        }

        public void connectRun() throws InterruptedException {
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(workerGroup);
                b.channel(NioSocketChannel.class);
                b.handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new StreamingClientHandler());
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


    public static class StreamingServer {
        private int port;

        public StreamingServer(int port) {
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
                                socketChannel.pipeline().addLast(new StreamingServerHandler());
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