package com.bpi.userservice;

import com.bpi.userservice.dao.UserDao;
import com.bpi.userservice.dao.UserDaoImpl;
import com.bpi.userservice.menu.ConsoleMenu;
import com.bpi.userservice.service.UserService;
import com.bpi.userservice.service.UserServiceImpl;
import com.bpi.userservice.util.HibernateUtil;


public class Main {

    public static void main(String[] args) {

        try {


            UserDao userDao = new UserDaoImpl();
            UserService userService = new UserServiceImpl(userDao);
            ConsoleMenu menu = new ConsoleMenu(userService);
            menu.start();
        }finally {
            HibernateUtil.getSessionFactory().close();
        }

    }
}