package com.bpi.userservice.service;

import com.bpi.userservice.dao.UserDao;
import com.bpi.userservice.exception.ValidationException;
import com.bpi.userservice.entity.User;
import com.bpi.userservice.exception.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;

public class UserServiceImpl implements UserService{
    private static final Logger logger =
            LogManager.getLogger(UserServiceImpl.class);
    private  final UserDao userDao ; //= new UserDaoImpl();
public UserServiceImpl (UserDao userDao){
    this.userDao=userDao;
}
    private void validate (User user) {

        if (user == null) {
            throw new ValidationException("Пользователь не может быть null");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            throw new ValidationException("Имя не может быть пустым");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ValidationException("Email не может быть пустым");
        }

        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Некорректный email");
        }

        if (user.getAge() <= 0) {
            throw new ValidationException("Возраст должен быть больше 0");
        }
    }
    @Override
    public void save(User user) {
    validate(user);
        logger.info("Создание пользователя: {}", user.getEmail());
        userDao.save(user);
        logger.info("Пользователь успешно создан, id={}", user.getId());
    }

    @Override
    public User findById(Long id) {
        logger.info("Поиск пользователя id={}", id);

        User user=userDao.findById(id);
    if (user==null){
        logger.warn("Пользователь id={} не найден", id);
        throw new UserNotFoundException( "Пользователь с id " + id + " не найден");
    }
    return user;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void update(User user) {
    validate (user);
        if (user.getId() == null) {
            throw new ValidationException(
                    "Для обновления нужен id пользователя"
            );
        }
    logger.info("Обновление пользователя id={}", user.getId());
userDao.update(user);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new ValidationException("ID не может быть null");
        }

        logger.info("Удаление пользователя id={}", id);
    findById(id);
        userDao.delete(id);
        logger.info("Пользователь id={} удален", id);


    }
}
