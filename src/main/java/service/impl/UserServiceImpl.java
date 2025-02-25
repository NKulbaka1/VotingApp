package service.impl;

import dao.UserDao;
import model.User;
import service.UserService;

public class UserServiceImpl implements UserService {

    private User currentUser;
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getCurentUser() {
        return currentUser;
    }

    @Override
    public String login(String[] args) {
        if (args.length < 1 || !args[0].startsWith("-u=")) return "Usage: login -u=<username>";


        String username = args[0].substring(3);
        currentUser = new User(username);
        if (!userDao.containsUser(currentUser)) userDao.add(currentUser);

        return "Logged in as " + username;
    }
}
