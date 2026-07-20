package com.bpi.userservice.service;

import com.bpi.userservice.dao.UserDao;
import com.bpi.userservice.entity.User;
import com.bpi.userservice.exception.UserNotFoundException;
import com.bpi.userservice.exception.ValidationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userDao);
    }


    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Ivan");
        user.setEmail("ivan@mail.com");
        user.setAge(25);

        return user;
    }


    @Test
    void shouldSaveUser() {

        User user = createUser();

        userService.save(user);

        verify(userDao, times(1))
                .save(user);
    }


    @Test
    void shouldThrowExceptionWhenEmailInvalid() {

        User user = createUser();
        user.setEmail("wrongEmail");


        assertThrows(
                ValidationException.class,
                () -> userService.save(user)
        );


        verify(userDao, never())
                .save(any());
    }


    @Test
    void shouldFindUserById() {

        User user = createUser();


        when(userDao.findById(1L))
                .thenReturn(user);


        User result = userService.findById(1L);


        assertEquals(user, result);

        verify(userDao)
                .findById(1L);
    }


    @Test
    void shouldThrowExceptionWhenUserNotFound() {


        when(userDao.findById(1L))
                .thenReturn(null);


        assertThrows(
                UserNotFoundException.class,
                () -> userService.findById(1L)
        );

    }


    @Test
    void shouldUpdateUser() {

        User user = createUser();


        userService.update(user);


        verify(userDao)
                .update(user);
    }


    @Test
    void shouldFindAndDeleteUser() {

        User user = createUser();


        when(userDao.findById(1L))
                .thenReturn(user);


        userService.delete(1L);


        verify(userDao)
                .findById(1L);

        verify(userDao)
                .delete(1L);
    }


    @Test
    void shouldReturnAllUsers() {

        List<User> users = List.of(createUser());


        when(userDao.findAll())
                .thenReturn(users);


        List<User> result = userService.findAll();


        assertEquals(1, result.size());

        verify(userDao)
                .findAll();
    }
}