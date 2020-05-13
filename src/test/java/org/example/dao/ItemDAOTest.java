package org.example.dao;

import org.example.config.SpringConfig;
import org.example.models.Cart;
import org.example.models.Item;
import org.example.models.Order;
import org.example.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(SpringConfig.class)
@ActiveProfiles("test")
class ItemDAOTest {
    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private UserDAO userDAO;
    private long current_time = new Date().getTime();

    @Test
    void saveAndGetAndDeleteTest() {
        Item item = new Item("test_name", "t_code", 123, 1);
        itemDAO.save(item);
        item.setCode("test_code");
        itemDAO.update(item);
        Item itemFromDB = itemDAO.findById(item.getId());
        assertNotNull(itemFromDB);
        assertEquals(item.getCode(), itemFromDB.getCode());

        itemDAO.delete(item);
        assertNull(itemDAO.findById(item.getId()));
    }

    @Test
    void findByItemCodeTest() {
        Item item = new Item("test_name", "t_code", 123, 1);
        itemDAO.save(item);
        List<Item> list = itemDAO.findByItemCode(item.getCode());
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertNotNull(list.get(0));
        assertEquals(list.get(0).getCode(), item.getCode());
        itemDAO.delete(item);
    }

    @Test
    void getAllTest() {
        Item item1 = new Item("test_name", "test_code", 123, 1);
        Item item2 = new Item("t_name", "t_code", 321, 2);
        itemDAO.save(item1);
        itemDAO.save(item2);
        List<Item> list = itemDAO.getAll();
        assertNotNull(list);
        assertFalse(list.isEmpty());
        itemDAO.delete(item1);
        itemDAO.delete(item2);
    }

    @Test
    void getAllByCartTest() {
        Item item1 = new Item("test_name", "test_code", 123, 1);
        itemDAO.save(item1);
        User user = new User("test_login1", "test_pass1", "test_fn", "test_ln");
        userDAO.save(user);
        Cart cart = new Cart(0, user.getId(), current_time);
        cartDAO.save(cart);
        Order order = new Order(item1.getId(), cart.getId(), 5);
        orderDAO.save(order);
        List<Item> list = itemDAO.getAllByCart(cart);
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertNotNull(list.get(0));
        assertEquals(list.get(0).getName(), item1.getName());
        orderDAO.delete(order);
        cartDAO.delete(cart);
        itemDAO.delete(item1);
        userDAO.delete(user);
    }


    @Test
    void getAllAvialable() {
        Item item1 = new Item("test_name", "test_code", 123, 1);
        Item item2 = new Item("t_name", "t_code", 321, 2);
        itemDAO.save(item1);
        itemDAO.save(item2);
        List<Item> list = itemDAO.getAllAvialable();
        assertNotNull(list);
        assertFalse(list.isEmpty());
        itemDAO.delete(item1);
        itemDAO.delete(item2);
    }
}