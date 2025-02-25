package server;

import dao.TopicDao;
import dao.UserDao;
import dao.VoteDao;
import dao.impl.TopicDaoImpl;
import dao.impl.UserDaoImpl;
import dao.impl.VoteDaoImpl;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import service.TopicService;
import service.UserService;
import service.VoteService;
import service.impl.TopicServiceImpl;
import service.impl.UserServiceImpl;
import service.impl.VoteServiceImpl;
import util.RequestParser;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    public ServerInitializer() {
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        UserDao userDao = new UserDaoImpl();
        VoteDao voteDao = new VoteDaoImpl();
        TopicDao topicDao = new TopicDaoImpl();
        UserService userService = new UserServiceImpl(userDao);
        TopicService topicService = new TopicServiceImpl(topicDao, userService);
        VoteService voteService = new VoteServiceImpl(voteDao, topicService);
        RequestParser requestParser = new RequestParser();

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new ServerHandler(userService, voteService, topicService, requestParser));
    }
}
