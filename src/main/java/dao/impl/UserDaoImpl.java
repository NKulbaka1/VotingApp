package dao.impl;

import dao.UserDao;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserDaoImpl implements UserDao {
    private final List<User> users;
    {
        users = new CopyOnWriteArrayList<>();
    }

    public void add(User user) {
        users.add(user);
    }

    public boolean containsUser(User user) {
        return users.contains(user);
    }
}
