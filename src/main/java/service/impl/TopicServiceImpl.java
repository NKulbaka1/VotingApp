package service.impl;

import dao.TopicDao;
import model.Topic;
import model.Vote;
import service.TopicService;
import service.UserService;

public class TopicServiceImpl implements TopicService {
    private TopicDao topicDao;

    private UserService userService;

    public TopicServiceImpl(TopicDao topicDao, UserService userService) {
        this.topicDao = topicDao;
        this.userService = userService;
    }

    @Override
    public String createTopic(String[] args) {
        if (userService.getCurentUser() == null) return "You must login first.";

        if (args.length < 2 || !args[0].equals("topic") || !args[1].startsWith("-n="))
            return "Usage: create topic -n=<topic>";

        String topicName = args[1].substring(3);
        Topic topic = new Topic(topicName);
        if (topicDao.containsTopic(topic))
            return "Topic already exists: " + topicName;
        else
            topicDao.add(topic);
        return "Topic created: " + topicName;
    }

    @Override
    public void addVote(String topicName, Vote vote) {
        Topic topic = topicDao.get(topicName);
        topic.addVote(vote);
    }
}
