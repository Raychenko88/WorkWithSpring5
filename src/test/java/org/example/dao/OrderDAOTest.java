package org.example.dao;

import org.example.config.SpringConfig;
import org.example.models.Cart;
import org.example.models.Item;
import org.example.models.Order;
import org.example.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringJUnitConfig(SpringConfig.class)
@ActiveProfiles("test")
class OrderDAOTest {
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private ItemDAO itemDAO;

    private  Long currentTime = new Date().getTime();
    private List<Cart> carts;
    private List<User> users;
    private List<Item> items;
    private List<Order> orders;

    @BeforeEach
    void setUp() {
        carts = new ArrayList<>();
        users = new ArrayList<>();
        items = new ArrayList<>();
        orders = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        orders.forEach(it -> orderDAO.delete(it));
        carts.forEach(it -> cartDAO.delete(it));
        users.forEach(it -> userDAO.delete(it));
        items.forEach(it -> itemDAO.delete(it));
    }


    @Test
    void saveAndGetAndDeleteTest() {
        long currentTime = new Date().getTime();

        User user = new User("test_login", "test_pass", "test_fn", "test_ln");
        userDAO.save(user);
        User userFromDB = userDAO.findById(user.getId());

        Cart cart = new Cart(0, userFromDB.getId(), currentTime);
        cartDAO.save(cart);

        Item item = new Item("test_name", "t_code", 123, 1);
        itemDAO.save(item);

        Order order = new Order(item.getId(), cart.getId(), 10);
        orderDAO.save(order);
        order.setAmount(15);
        orderDAO.update(order);
        Order orderFromDB = orderDAO.findById(order.getId());
        assertNotNull(orderFromDB);
        assertEquals(order.getAmount(), orderFromDB.getAmount());

        orderDAO.delete(order);
        assertNull(orderDAO.findById(order.getId()));
        cartDAO.delete(cart);
        userDAO.delete(user);
    }

    @Test
    void getAllByCartTest() {
        User user = new User("login0", "pass0", "first_name0", "last_name0");
        userDAO.save(user);
        users.add(user);
        assertNotNull(user.getId());

        Cart cart = new Cart(0, user.getId(), currentTime);
        Cart cartNotOk = new Cart(0, user.getId(), currentTime);
        cartDAO.save(cart);
        cartDAO.save(cartNotOk);
        carts.add(cart);
        carts.add(cartNotOk);
        assertNotNull(cart.getId());
        assertNotNull(cartNotOk.getId());

        Item item = new Item("name_5", "code_5", 50, 500);
        itemDAO.save(item);
        items.add(item);
        assertNotNull(item.getId());

        Order order1 = new Order(item.getId(), cart.getId(), 50);
        Order order2 = new Order(item.getId(), cart.getId(), 50);
        Order order3 = new Order(item.getId(), cartNotOk.getId(), 50);
        orderDAO.save(order1);
        orderDAO.save(order2);
        orderDAO.save(order3);
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        assertNotNull(order1.getId());
        assertNotNull(order2.getId());
        assertNotNull(order3.getId());

        List<Order> targetOrders = orderDAO.getAllByCart(cart);
        assertTrue(targetOrders.size() >= 2);

        int count = 0;
        for (Order each:targetOrders){
            if ((order1.getId()).equals(each.getId())) {count++;}
            if ((order2.getId()).equals(each.getId())) {count++;}
            if ((order3.getId()).equals(each.getId())) {count++;}
        }
        assertEquals(2,count);

    }

    @Test
    void updateAmountTest() {
        User user = new User("login", "pass", "first_name", "last_name");
        userDAO.save(user);
        users.add(user);
        assertNotNull(user.getId());

        Cart cart = new Cart(0, user.getId(), currentTime);
        cartDAO.save(cart);
        carts.add(cart);
        assertNotNull(cart.getId());

        Item item = new Item("name", "code", 50, 500);
        itemDAO.save(item);
        items.add(item);
        assertNotNull(item.getId());

        Order order = new Order(item.getId(), cart.getId(), 50);
        orderDAO.save(order);
        orders.add(order);
        assertNotNull(order.getId());

        Integer amount=100;
        orderDAO.updateAmount(order, amount);
        Order targetOrder = orderDAO.findById(order.getId());
        assertNotNull(targetOrder);
        int a = targetOrder.getAmount();
        assertEquals(100, targetOrder.getAmount());
    }

    @Test
    void findOrderByItem() {
        User user = new User("login0", "pass0", "first_name0", "last_name0");
        userDAO.save(user);
        users.add(user);
        assertNotNull(user.getId());

        Cart cart = new Cart(0, user.getId(), currentTime);
        cartDAO.save(cart);
        carts.add(cart);
        assertNotNull(cart.getId());

        Item item = new Item("name_5", "code_5", 50, 500);
        itemDAO.save(item);
        items.add(item);
        assertNotNull(item.getId());

        Order order = new Order(item.getId(), cart.getId(), 50);
        orderDAO.save(order);
        orders.add(order);
        assertNotNull(order.getId());

        Order targetOrder = orderDAO.findOrderByItem(item.getId());
        assertNotNull(targetOrder);
        assertEquals(item.getId(), targetOrder.getItemId());
    }

    @Test
    void findByCart(){

    }

}