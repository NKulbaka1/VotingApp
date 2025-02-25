package service;

import model.Vote;

public interface VoteService {

    String createVote(String topicName, Vote vote);
}
