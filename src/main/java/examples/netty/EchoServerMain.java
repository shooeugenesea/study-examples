package examples.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.lang.ref.Reference;
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
                b.option(ChannelOption.SO_KEEPALIVE, true);
                b.handler(new SimpleClientChannelInitializer());

                // Start the client.
                ChannelFuture f = b.connect(ip, port).sync(); // (5)

                System.out.println("scan...");
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextLine()) {
                    String msg = scanner.nextLine();
                    System.out.println("prepare to send next message:" + msg);
                    ByteBuf msgBuffer = Unpooled.wrappedBuffer(scanner.nextLine().getBytes(CharsetUtil.UTF_8));
                    f.channel().writeAndFlush(msgBuffer);
                }


                // Wait until the connection is closed.
                f.channel().closeFuture().sync();
            } finally {
                workerGroup.shutdownGracefully();
            }
        }
    }


    public static class EchoServer {
        private int port;

        public EchoServer(int port) {
            this.port = port;
        }

        public void run() throws Exception {
            EventLoopGroup parentGroup = new NioEventLoopGroup();
            EventLoopGroup childGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(parentGroup, childGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new SimpleServerChannelInitializer())
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);

                ChannelFuture f = b.bind(port).sync();
                f.channel().closeFuture().sync();
            } finally {
                childGroup.shutdownGracefully();
                parentGroup.shutdownGracefully();
            }
        }
    }


}