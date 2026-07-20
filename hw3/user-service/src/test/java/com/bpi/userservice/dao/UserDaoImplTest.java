package com.bpi.userservice.dao;

import com.bpi.userservice.entity.User;
import com.bpi.userservice.util.HibernateUtil;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers

class UserDaoImplTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:17")
                    .withDatabaseName("test_db")
                    .withUsername("test")
                    .withPassword("test");


    private UserDao userDao;

    @BeforeAll
    static void setup() {

        System.setProperty(
                "hibernate.connection.url",
                postgres.getJdbcUrl()
        );

        System.setProperty(
                "hibernate.connection.username",
                postgres.getUsername()
        );

        System.setProperty(
                "hibernate.connection.password",
                postgres.getPassword()
        );
    }


    @BeforeEach
    void init() {
        userDao = new UserDaoImpl();
    }


    @Test
    void saveAndFindByIdTest() {

        User user = new User();
        user.setName("Ivan");
        user.setEmail("ivan@test.com");
        user.setAge(25);

        userDao.save(user);

        assertNotNull(user.getId());

        User found = userDao.findById(user.getId());

        assertNotNull(found);
        assertEquals("Ivan", found.getName());
    }

    @Test
    void findAllTest() {

        User user = new User();
        user.setName("Ivan");
        user.setEmail("ivan@test.com");
        user.setAge(25);

        userDao.save(user);

        List<User> users = userDao.findAll();

        assertFalse(users.isEmpty());
    }


    @Test
    void updateTest() {

        User user = new User();
        user.setName("Ivan");
        user.setEmail("ivan@test.com");
        user.setAge(25);

        userDao.save(user);

        user.setName("Updated");

        userDao.update(user);

        User updated =
                userDao.findById(user.getId());

        assertEquals("Updated", updated.getName());
    }


    @Test
    void deleteTest() {

        User user = new User();
        user.setName("Ivan");
        user.setEmail("ivan@test.com");
        user.setAge(25);

        userDao.save(user);

        Long id = user.getId();

        userDao.delete(id);

        User deleted =
                userDao.findById(id);

        assertNull(deleted);
    }


    @AfterAll
    static void close() {
        HibernateUtil.getSessionFactory().close();
    }
}