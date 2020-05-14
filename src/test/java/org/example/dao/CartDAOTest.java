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
class CartDAOTest {
    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private ItemDAO itemDAO;
    private long currentTime = new Date().getTime();
    private  List<Cart> carts;
    private  List<User> users;
    private  List<Item> items;
    private  List<Order> orders;

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

        Cart cart = new Cart(0, userFromDB, currentTime);
        cartDAO.save(cart);
        cart.setClosed(1);
        cartDAO.update(cart);
        Cart cartFromDB = cartDAO.findById(cart.getId());
        assertNotNull(cartFromDB);
        assertEquals(cart.getClosed(), cartFromDB.getClosed());

        cartDAO.delete(cart);
        assertNull(cartDAO.findById(cart.getId()));
        userDAO.delete(user);
    }

    @Test
    void getAllByUserAndPeriodTest() {
        User userOk = new User("login1", "pass1", "first_name1", "last_name ");
        User userNotOk = new User("login2", "pass2", "first_name2", "last_name2");
        userDAO.save(userOk);
        userDAO.save(userNotOk);
        users.add(userOk);
        users.add(userNotOk);
        assertNotNull(userOk.getId());
        assertNotNull(userNotOk.getId());

        Long periodFrom = currentTime - 100;
        Long periodTo = currentTime;
        Long timeOk = currentTime - 50;
        Long timeNotOk = currentTime - 200;

        Cart cartOk = new Cart(0, userOk, timeOk);
        Cart cartNotOk1 = new Cart(0, userOk, timeNotOk);
        Cart cartNotOk2 = new Cart(0, userNotOk, timeOk);

        cartDAO.save(cartOk);
        cartDAO.save(cartNotOk1);
        cartDAO.save(cartNotOk2);
        carts.add(cartOk);
        carts.add(cartNotOk1);
        carts.add(cartNotOk2);
        assertNotNull(cartOk.getId());
        assertNotNull(cartNotOk1.getId());
        assertNotNull(cartNotOk2.getId());

        List<Cart> targetCarts = cartDAO.getAllByUserAndPeriod(userOk, periodFrom, periodTo);
        assertTrue(targetCarts.size() >= 1);

        boolean isInCollectionCartOk = false;
        boolean isInCollectionCartNotOk1 = false;
        boolean isInCollectionCartNotOk2 = false;

        for (Cart each : targetCarts) {
            if ((cartOk.getId()).equals(each.getId())) {
                isInCollectionCartOk = true;
            }
            if ((cartNotOk1.getId()).equals(each.getId())) {
                isInCollectionCartNotOk1 = true;
            }
            if ((cartNotOk2.getId()).equals(each.getId())) {
                isInCollectionCartNotOk2 = true;
            }
        }
        assertTrue(isInCollectionCartOk);
        assertTrue(!isInCollectionCartNotOk1);
        assertTrue(!isInCollectionCartNotOk2);
    }

    @Test
    void getByUserAndOpenStatusTest(){
        User userOk = new User("login2", "pass2", "first_name2", "last_name2");
        User userNotOk = new User("login2", "pass2", "first_name2", "last_name2");
        userDAO.save(userOk);
        userDAO.save(userNotOk);
        users.add(userOk);
        users.add(userNotOk);
        assertNotNull(userOk.getId());
        assertNotNull(userNotOk.getId());

        Cart cartOk = new Cart(0, userOk, currentTime);
        Cart cartNotOk1 = new Cart(1, userOk, currentTime);
        Cart cartNotOk2 = new Cart(0, userNotOk, currentTime);

        cartDAO.save(cartOk);
        cartDAO.save(cartNotOk1);
        cartDAO.save(cartNotOk2);
        carts.add(cartOk);
        carts.add(cartNotOk1);
        carts.add(cartNotOk2);
        assertNotNull(cartOk.getId());
        assertNotNull(cartNotOk1.getId());
        assertNotNull(cartNotOk2.getId());

        Cart targetCart = cartDAO.getByUserAndOpenStatus(userOk);
        assertNotNull(targetCart);
        assertNotNull(targetCart.getUser().getId());
        assertEquals(targetCart.getUser().getId(), userOk.getId());
        assertEquals(targetCart.getId(), cartOk.getId());
    }

    @Test
    void updateStatusTest() {
        User user = new User("login0", "pass0", "first_name0", "last_name0");
        userDAO.save(user);
        users.add(user);
        assertNotNull(user.getId());

        Cart cart = new Cart(0, user, currentTime);
        cartDAO.save(cart);
        carts.add(cart);
        assertNotNull(cart.getId());

        cart.setClosed(1);

        Cart targetCart = cartDAO.updateStatus(cart, 1);
        assertNotNull(targetCart);
        assertEquals(1, targetCart.getClosed());
    }
}