package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getConnection();
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS USERS" +
                    " (id mediumint not null auto_increment, name VARCHAR(50), " +
                    "lastname VARCHAR(50), " +
                    "age tinyint, " +
                    "PRIMARY KEY (id))").executeUpdate();
            transaction.commit();
            System.out.println("Успешно: создание таблицы");
        } catch (HibernateException e) {
            System.out.println("HiberEx " + e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS USERS");
            transaction.commit();
            System.out.println("Успешно: таблица дропнута");
        } catch (HibernateException e) {
            System.out.println("HiberEx " +e );
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName,age));

            transaction.commit();
            System.out.println("Успешно: User "+name+" have been added");
        } catch (HibernateException e) {
            System.out.println("HiberEx " +e );
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("Успешно: user дропнут");
        } catch (HibernateException e) {
            System.out.println("HiberEx " +e );
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User> ul = null;
        try (Session session = sessionFactory.openSession()) {
        CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.from(User.class);
        ul = session.createQuery(criteriaQuery).getResultList();
        } catch (HibernateException e) {
            System.out.println("HiberEx " +e );
        }
        return ul;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            List<User> instances = session.createQuery(criteriaQuery).getResultList();

            for (Object o : instances) {
                session.delete(o);
            }

            session.getTransaction().commit();
            System.out.println("Успешно: таблица очищена");
        } catch (Exception e) {
            System.out.println("HiberEx " +e );
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
