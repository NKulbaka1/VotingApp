package dao;

import model.Topic;
import model.User;
import service.TopicService;

public interface TopicDao {
    void add(Topic topic);

    boolean containsTopic(Topic topic);

    Topic get(String name);
}
