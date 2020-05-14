package org.example.services;


import org.example.dao.UserDAO;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    protected UserDAO userDAO;

    public User create(User user){
        if (userDAO.findById(user.getId()) == null){
            return userDAO.save(user);
        }else {
            return null;
        }
    }

    public User getAuthUser(String login, String password){
        List<User> users = userDAO.getByLogin(login);
        for (User user : users)
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public  List<User> getByLogin (String login){
        return userDAO.getByLogin(login);
    }

    public List<User> getAll(){
        return userDAO.getAll();
    }

    public List<User> findByLogAndPass(String login, String password){
        return userDAO.findByLogAndPass(login,password);
    }

    public User update(User user){
       if (userDAO.findById(user.getId()) != null){
           return userDAO.update(user);
       }
       return null;
    }

    public User findById(User user){
        return userDAO.findById(user.getId());
    }

    public void delete(User user){
        if (userDAO.findById(user.getId()) != null){
            userDAO.delete(user);
        }
    }
}
