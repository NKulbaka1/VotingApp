package server;

import dao.TopicDao;
import dao.UserDao;
import dao.VoteDao;
import dao.impl.TopicDaoImpl;
import dao.impl.UserDaoImpl;
import dao.impl.VoteDaoImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import service.TopicService;
import service.UserService;
import service.VoteService;
import service.impl.TopicServiceImpl;
import service.impl.UserServiceImpl;
import service.impl.VoteServiceImpl;
import util.RequestParser;

public class Server {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        ServerInitializer serverInitializer = new ServerInitializer();

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(10);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(serverInitializer);

            ChannelFuture f = b.bind(port).sync();
            System.out.println("Server started on port " + port);
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new Server(port).run();
    }
}