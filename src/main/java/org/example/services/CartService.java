package org.example.services;

import org.example.dao.CartDAO;
import org.example.dao.UserDAO;
import org.example.models.Cart;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private UserDAO userDAO;

    public Cart creat(Cart cart){
        if (cartDAO.findById(cart.getId()) == null){
            return cartDAO.save(cart);
        }
        return null;
    }

    public Cart update(Cart cart){
        if (cartDAO.findById(cart.getId()) != null){
            return cartDAO.update(cart);
        }
        return null;
    }

    public Cart findById(Integer id){
        return cartDAO.findById(id);
    }

    public void delete(Cart cart){
        if (cartDAO.findById(cart.getId()) != null){
            cartDAO.delete(cart);
        }
    }

    public Cart createCartForUser(User user){
        if (cartDAO.getByUserAndOpenStatus(user) == null){
            Cart cart = new Cart();
            cart.setCreationTime(new Date().getTime());
            cart.setClosed(0);
            cart.setUser(user);
            return cartDAO.save(cart);
        }else {
          return cartDAO.getByUserAndOpenStatus(user);
        }

    }

    public Cart updateStatus(Cart cart, Integer closed){
        if (cartDAO.findById(cart.getId()) == null){
            cart.setClosed(closed);
            return cartDAO.update(cart);
        }
        return null;
    }

    public List<Cart> getAllByUserAndPeriod(User user, Long timeFrom, Long timeTo){
        if (userDAO.findById(user.getId()) != null){
            return cartDAO.getAllByUserAndPeriod(user, timeFrom, timeTo);
        }
        return null;
    }

    public Cart findOpenCartByUser(User user){
        return cartDAO.getByUserAndOpenStatus(user);
    }
}
