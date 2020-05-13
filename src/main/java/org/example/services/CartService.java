package org.example.services;

import org.example.dao.CartDAO;
import org.example.dao.UserDAO;
import org.example.models.Cart;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartService {
    @Autowired
    private CartDAO cartDAO;

    public Cart createCartForUser(Integer userId){
        Cart cart = new Cart();
        cart.setCreationTime(new Date().getTime());
        cart.setClosed(0);
        cart.setUserId(userId);
        return cartDAO.save(cart);
    }

    public Cart findOpenCartByUser(User user){
        return cartDAO.getByUserAndOpenStatus(user);
    }
}
