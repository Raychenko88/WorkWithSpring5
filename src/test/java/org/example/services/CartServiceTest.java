package org.example.services;

import org.example.dao.CartDAO;
import org.example.dao.UserDAO;
import org.example.models.Cart;
import org.example.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CartServiceTest {

    private CartService cartService = new CartService();
    private CartDAO cartDAO = Mockito.mock(CartDAO.class);
    private UserService userService = new UserService();
    private UserDAO userDAO = Mockito.mock(UserDAO.class);
    private Long currentTime = new Date().getTime();

    @BeforeEach
    public void setBefore() {
        cartService.cartDAO = cartDAO;
        cartService.userDAO = userDAO;
    }


    @Test
    void creatTest() {
        User user = new User("test_login111", "test_pass", "test_fn", "test_ln");
        Cart cart = new Cart(0, user, currentTime);
        cart.setId(1);
        when(cartDAO.findById(anyInt())).thenReturn(null);
        when(cartDAO.save(cart)).thenReturn(cart);
        Cart cart1 = cartService.creat(cart);
        assertNotNull(cart1);
        verify(cartDAO, times(1)).findById(anyInt());
        verify(cartDAO, times(1)).save(any(Cart.class));

    }

    @Test
    void updateTest() {
        User user = new User("test_login111", "test_pass", "test_fn", "test_ln");
        Cart cart = new Cart(0, user, currentTime);
        cart.setId(1);
        when(cartDAO.findById(anyInt())).thenReturn(cart);
        when(cartDAO.update(cart)).thenReturn(cart);
        Cart cart1 = cartService.update(cart);
        assertNotNull(cart);
        verify(cartDAO, times(1)).findById(anyInt());
        verify(cartDAO, times(1)).update(any(Cart.class));
    }

    @Test
    void createCartForUserSuccessTest() {
        User user = new User("test_login111", "test_pass", "test_fn", "test_ln");
        Cart cart = new Cart(0, user, currentTime);
        when(cartDAO.getByUserAndOpenStatus(any(User.class))).thenReturn(null);
        when(cartDAO.save(any(Cart.class))).thenReturn(cart);
        Cart cart1 = cartService.createCartForUser(user);
        assertNotNull(cart1);
        verify(cartDAO,times(1)).getByUserAndOpenStatus(user);
        verify(cartDAO,times(1)).save(any(Cart.class));
    }

    @Test
    void createCartForUserRejectedTest() {
        User user = new User("test_login111", "test_pass", "test_fn", "test_ln");
        Cart cart = new Cart(0, user, currentTime);
        when(cartDAO.getByUserAndOpenStatus(any(User.class))).thenReturn(cart);
        Cart cart1 = cartService.createCartForUser(user);
        assertNotNull(cart1);
        verify(cartDAO,times(2)).getByUserAndOpenStatus(any(User.class));
    }

    @Test
    void updateStatusTest() {
        User user = new User("test_login111", "test_pass", "test_fn", "test_ln");
        Cart cart = new Cart(0, user, currentTime);
        cart.setId(1);
        when(cartDAO.findById(anyInt())).thenReturn(null);
        when(cartDAO.update(cart)).thenReturn(cart);
        Cart cart1 = cartService.updateStatus(cart, anyInt());
        assertNotNull(cart1);
        verify(cartDAO,times(1)).findById(anyInt());
        verify(cartDAO,times(1)).update(cart);
    }

    @Test
    void getAllByUserAndPeriodTest() {
        User user = new User("test_login", "test_pass", "test_fn", "test_ln");
        user.setId(1);
        Cart cart = new Cart(0, user, currentTime);
        cart.setId(1);
        when(userDAO.findById(anyInt())).thenReturn(user);
        when(cartDAO.getAllByUserAndPeriod(any(User.class), anyLong(), anyLong())).thenReturn(Collections.singletonList(cart));
        List<Cart> list = cartService.getAllByUserAndPeriod(user, (long) 1, currentTime + 10);
        assertNotNull(list);
        assertFalse(list.isEmpty());
        verify(cartService.userDAO,times(1)).findById(user.getId());
        verify(cartDAO,times(1)).getAllByUserAndPeriod(user, (long) 1, currentTime + 10);
    }
}