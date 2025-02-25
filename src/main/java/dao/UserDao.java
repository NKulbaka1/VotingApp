package dao;

import model.User;

public interface UserDao {
    void add(User user);

    boolean containsUser(User user);
}
