package dao.impl;

import dao.TopicDao;
import model.Topic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TopicDaoImpl implements TopicDao {
    private final Map<String, Topic> topics;
    {
        topics = new ConcurrentHashMap<>();
    }

    @Override
    public void add(Topic topic) {
        topics.put(topic.getName(), topic);
    }

    @Override
    public boolean containsTopic(Topic topic) {
        return topics.containsKey(topic.getName());
    }

    @Override
    public Topic get(String name) {
        return topics.get(name);
    }
}
