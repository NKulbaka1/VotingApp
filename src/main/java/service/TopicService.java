package service;

import model.Vote;

public interface TopicService {
    String createTopic(String[] args);

    void addVote(String topicName, Vote vote);
}
