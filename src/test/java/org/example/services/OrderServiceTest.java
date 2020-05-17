package org.example.services;

import org.example.dao.CartDAO;
import org.example.dao.OrderDAO;
import org.example.models.Cart;
import org.example.models.Item;
import org.example.models.Order;
import org.example.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Date;
import java.util.List;

class OrderServiceTest {

    private OrderService orderService = new OrderService();
    private OrderDAO orderDAO = Mockito.mock(OrderDAO.class);
    private CartService cartService = new CartService();
    private CartDAO cartDAO = Mockito.mock(CartDAO.class);
    private Long currentTime = new Date().getTime();

    @BeforeEach
    public void setBefore() {
        orderService.orderDAO = orderDAO;
        orderService.cartDAO = cartDAO;
    }


    @Test
    void creatTest() {
        User user = new User("test_login", "test_pass", "test_fn", "test_ln");
        Cart cart = new Cart(0, user, currentTime);
        Item item = new Item("test_name", "t_code", 123, 1);
        Order order = new Order(item, cart, 10);
        when(orderDAO.findById(anyInt())).thenReturn(null);
        when(orderDAO.save(any(Order.class))).thenReturn(order);
        Order order1 = orderService.creat(order);
        assertNotNull(order1);
        verify(orderDAO,times(1)).findById(order.getId());
        verify(orderDAO,times(1)).save(order);
    }

    @Test
    void updateTest() {
        User user = new User("test_login", "test_pass", "test_fn", "test_ln");
        Cart cart = new Cart(0, user, currentTime);
        Item item = new Item("test_name", "t_code", 123, 1);
        Order order = new Order(item, cart, 10);
        order.setId(1);
        when(orderDAO.findById(anyInt())).thenReturn(order);
        when(orderDAO.update(any(Order.class))).thenReturn(order);
        Order order1 = orderService.update(order);
        assertNotNull(order1);
        verify(orderDAO,times(1)).findById(order.getId());
        verify(orderDAO,times(1)).update(order);
    }


    @Test
    void updateAmountTest() {
        User user = new User("test_login", "test_pass", "test_fn", "test_ln");
        Cart cart = new Cart(0, user, currentTime);
        Item item = new Item("test_name", "t_code", 123, 1);
        Order order = new Order(item, cart, 10);
        order.setId(1);
        when(orderDAO.findById(anyInt())).thenReturn(order);
        when(orderDAO.update(any(Order.class))).thenReturn(order);
        Order order1 = orderService.updateAmount(order, anyInt());
        assertNotNull(order1);
        verify(orderDAO,times(1)).findById(order.getId());
        verify(orderDAO,times(1)).update(order);
    }

    @Test
    void getAllByCartTest() {
        User user = new User("test_login", "test_pass", "test_fn", "test_ln");
        Cart cart = new Cart(0, user, currentTime);
        cart.setId(1);
        Item item = new Item("test_name", "t_code", 123, 1);
        Order order = new Order(item, cart, 10);
        order.setId(1);
        when(cartDAO.findById(anyInt())).thenReturn(cart);
        when(orderDAO.getAllByCart(any(Cart.class))).thenReturn(Collections.singletonList(order));
        List<Order> orders = orderService.getAllByCart(cart);
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        verify(cartDAO,times(1)).findById(cart.getId());
        verify(orderDAO,times(1)).getAllByCart(cart);
    }
}