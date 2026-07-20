package com.bpi.userservice.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;;

    private static SessionFactory buildSessionFactory() {

        try {

            Properties properties = new Properties();

            try (InputStream input =
                         HibernateUtil.class
                                 .getClassLoader()
                                 .getResourceAsStream("db.properties")) {
                if (input != null) {
                    properties.load(input);
                }



            }
            properties.putAll(System.getProperties());


            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addProperties(properties)
                    .buildSessionFactory();


        } catch (Exception e) {

            throw new RuntimeException("Ошибка создания SessionFactory", e);
        }
    }


    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }

        return sessionFactory;
    }
}