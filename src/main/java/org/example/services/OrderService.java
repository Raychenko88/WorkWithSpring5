package org.example.services;

import org.example.dao.OrderDAO;
import org.example.models.Cart;
import org.example.models.Item;
import org.example.models.Order;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderService {
    @Autowired
    private OrderDAO orderDAO;

    public Order createOrderByItemAndUser(Item item, Integer amount, User user) {
        Order order = new Order();
        order.setItemId(item.getId());
        order.setAmount(amount);
        Cart cart = new CartService().findOpenCartByUser(user);
        if (cart == null) {
            cart = new CartService().findOpenCartByUser(user);
        }
        order.setCartId(cart.getId());
        return orderDAO.save(order);
    }

    public List<Order> getOrdersByCart(Cart cart) {
        return orderDAO.findByCart(cart.getId());
    }
}
