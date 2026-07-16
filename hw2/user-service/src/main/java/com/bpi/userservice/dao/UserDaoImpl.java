package com.bpi.userservice.dao;

import com.bpi.userservice.entity.User;
import com.bpi.userservice.exception.DatabaseException;
import com.bpi.userservice.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger logger =
            LogManager.getLogger(UserDaoImpl.class);

    @Override
    public void save(User user) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            session.persist(user);

            transaction.commit();
            logger.info("Создан пользователь id={}, email={}",
                    user.getId(),
                    user.getEmail());

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
                logger.error("Ошибка сохранения пользователя", e);
                throw new DatabaseException(
                        "Ошибка при сохранении пользователя",
                        e
                );
            }
        }
    }
    @Override
    public User findById(Long id) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.find(User.class, id);
        } catch (Exception e) {

            logger.error("Ошибка поиска пользователя id={}", id, e);

            throw new DatabaseException(
                    "Ошибка поиска пользователя",
                    e
            );

        }
    }


    @Override
    public List<User> findAll() {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session
                    .createQuery("from User", User.class)
                    .getResultList();
        } catch (Exception e) {

            logger.error("Ошибка получения пользователей", e);

            throw new DatabaseException(
                    "Ошибка получения пользователей",
                    e
            );
        }
    }


    @Override
    public void update(User user) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            session.merge(user);

            transaction.commit();
            logger.info("Обновлен пользователь id={}",
                    user.getId());

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Ошибка обновления пользователя id={}",
                    user.getId(),
                    e);

            throw new DatabaseException(
                    "Ошибка обновления пользователя",
                    e
            );
        }
    }


    @Override
    public void delete(Long id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            User user = session.find(User.class, id);

            if (user != null) {
                session.remove(user);
            }

            transaction.commit();
            logger.info("Удален пользователь id={}", id);

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Ошибка удаления пользователя id={}",
                    id,
                    e);
            throw new DatabaseException(
                    "Ошибка удаления пользователя",
                    e
            );
        }
    }

}