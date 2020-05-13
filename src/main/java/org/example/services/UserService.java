package org.example.services;


import org.example.dao.UserDAO;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public User create(User user){
        return userDAO.save(user);
    }

    public User getAuthUser(String login, String password){
        List<User> users = userDAO.getByLogin(login);
        for (User user : users)
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


}
