package service;

import model.User;

public interface UserService {

    User getCurentUser();
    String login(String[] args);
}
