package org.example.services;

import org.example.dao.UserDAO;
import org.example.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserServiceTest {
    private UserService userService = new UserService();
    private UserDAO userDAO = Mockito.mock(UserDAO.class);

    @BeforeEach
    public  void setBefore() {
        userService.userDAO = userDAO;
    }


    @Test
    void createTest() {
        User user = new User("test_login111", "test_pass", "test_fn", "test_ln");
        when(userDAO.save(any(User.class))).thenReturn(user);
        User user1 = userService.create(user);
        assertNotNull(user1);
        assertEquals(user.getLogin(), user1.getLogin());
        verify(userDAO,times(1)).save(any(User.class));
    }

    @Test
    void getAuthUserSuccessTest() {
        User user = new User("test_login", "test_pass", "test_fn", "test_ln");
        when(userDAO.getByLogin(anyString())).thenReturn(Collections.singletonList(user));
        User user1 = userService.getAuthUser(user.getLogin(), user.getPassword());
        assertNotNull(user1);
        verify(userDAO, times(1)).getByLogin(anyString());
    }

    @Test
    void getAuthUserRejectedTest() {
        User user = new User("test_login", "test_pass", "test_fn", "test_ln");
        when(userDAO.getByLogin(anyString())).thenReturn(Collections.singletonList(user));
        User user1 = userService.getAuthUser(user.getLogin(), "anyPasswordTest");
        assertNull(user1);
        verify(userDAO, times(1)).getByLogin(anyString());
    }

    @Test
    void updateTest(){
        User user = new User("test_login", "test_pass", "test_fn", "test_ln");
        when(userDAO.getByLogin(anyString())).thenReturn(Collections.singletonList(user));
        User user1 = userService.getAuthUser(user.getLogin(), user.getPassword());
        assertNotNull(user1);
        verify(userDAO, times(1)).getByLogin(anyString());
    }
}