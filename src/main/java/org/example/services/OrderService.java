package org.example.services;

import org.example.dao.CartDAO;
import org.example.dao.OrderDAO;
import org.example.models.Cart;
import org.example.models.Item;
import org.example.models.Order;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderService {
    @Autowired
    protected OrderDAO orderDAO;
    @Autowired
    protected CartDAO cartDAO;

    public Order creat(Order order){
        if (orderDAO.findById(order.getId()) == null){
            return orderDAO.save(order);
        }
        return null;
    }

    public Order update(Order order){
        if (orderDAO.findById(order.getId()) != null){
            return orderDAO.update(order);
        }
        return null;
    }

    public Order findById(Integer id){
        return  orderDAO.findById(id);
    }

    public void delete(Order order){
        if (orderDAO.findById(order.getId()) != null){
            orderDAO.delete(order);
        }
    }

    public Order createOrderByItemAndUser(Item item, Integer amount, User user) {
        Order order = new Order();
        order.setItem(item);
        order.setAmount(amount);
        Cart cart = new CartService().findOpenCartByUser(user);
        if (cart == null) {
            cart = new CartService().findOpenCartByUser(user);
        }
        order.setCart(cart);
        return orderDAO.save(order);
    }

    public List<Order> getOrdersByCart(Cart cart) {
        return orderDAO.findByCart(cart.getId());
    }

    public Order updateAmount(Order order, Integer amount){
        if (orderDAO.findById(order.getId()) != null){
            order.setAmount(amount);
            return orderDAO.update(order);
        }
        return null;
    }

    public List<Order> getAllByCart(Cart cart){
        if (cartDAO.findById(cart.getId()) != null){
            return orderDAO.getAllByCart(cart);
        }
        return null;
    }

    public Order findOrderByItem(Integer itemId){
        return orderDAO.findOrderByItem(itemId);
    }
}
