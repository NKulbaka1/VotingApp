package dao.impl;

import dao.VoteDao;
import model.Vote;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class VoteDaoImpl implements VoteDao {
    private final List<Vote> votes;
    {
        votes = new CopyOnWriteArrayList<>();
    }

    public void add(Vote vote) {
        votes.add(vote);
    }
}
