package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import jm.task.core.jdbc.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    SessionFactory sessionFactory = new Util().getSessionFactory();


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            String sql = "CREATE TABLE IF NOT EXISTS USERS" +
                    "  id       BIGINT       PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "  name     VARCHAR(250) DEFAULT NULL," +
                    "  lastname VARCHAR(250) DEFAULT NULL," +
                    "  age      TINYINT      DEFAULT NULL)";
            session.beginTransaction();
            session.createNativeQuery(sql);
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS USERS");
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("DELETE USERS WHERE id = :id").setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            userList = session.createQuery("from USERS order by name").list();
            session.getTransaction().commit();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete USERS").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
