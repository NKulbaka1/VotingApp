package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Topic {
    private String name;
    private List<Vote> votes;

    public Topic(String name) {
        this.name = name;
        this.votes = new CopyOnWriteArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void addVote(Vote vote) {
        votes.add(vote);
    }

    public void removeVote(Vote vote) {
        votes.remove(vote);
    }
}
