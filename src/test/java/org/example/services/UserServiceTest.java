package org.example.services;

import org.example.dao.UserDAO;
import org.example.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


class UserServiceTest {
    private UserService userService = new UserService();
    private UserDAO userDAO = Mockito.mock(UserDAO.class);

    @BeforeEach
    public  void setBefore() {
        userService.userDAO = userDAO;
    }


    @Test
    void create() {

    }

    @Test
    void getAuthUserSuccess() {
        User user = new User("test_login", "test_pass", "test_fn", "test_ln");
        when(userDAO.getByLogin(anyString())).thenReturn(Collections.singletonList(user));
        User user1 = userService.getAuthUser(user.getLogin(), user.getPassword());
        assertNotNull(user1);
        verify(userDAO, times(1)).getByLogin(anyString());
    }

    @Test
    void getAuthUserRejected() {
    }
}