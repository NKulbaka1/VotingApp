package service.impl;

import dao.VoteDao;
import model.Vote;
import service.TopicService;
import service.VoteService;

public class VoteServiceImpl implements VoteService {
    private final VoteDao voteDao;

    private final TopicService topicService;

    public VoteServiceImpl(VoteDao voteDao, TopicService topicService) {
        this.voteDao = voteDao;
        this.topicService = topicService;
    }

    @Override
    public String createVote(String topicName, Vote vote) {
        topicService.addVote(topicName, vote);
        voteDao.add(vote);
        return "Vote created: " + vote.getName();
    }
}
