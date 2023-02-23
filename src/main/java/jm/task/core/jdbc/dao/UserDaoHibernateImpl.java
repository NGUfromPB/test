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
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS USERS" +
                    " (id mediumint not null auto_increment, name VARCHAR(50), " +
                    "lastname VARCHAR(50), " +
                    "age tinyint, " +
                    "PRIMARY KEY (id))").executeUpdate();
            transaction.commit();
            System.out.println("Успешно: создание таблицы");
        } catch (HibernateException e) {
            System.out.println("HiberEx " + e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS USERS");
            transaction.commit();
            System.out.println("Успешно: таблица дропнута");
        } catch (HibernateException e) {
            System.out.println("HiberEx " +e );
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName,age));

            transaction.commit();
            System.out.println("Успешно: User "+name+" have been added");
        } catch (HibernateException e) {
            System.out.println("HiberEx " +e );
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("Успешно: user дропнут");
        } catch (HibernateException e) {
            System.out.println("HiberEx " +e );
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User> ul = null;
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            Transaction transaction = session.beginTransaction();
            ul =session.createQuery(criteriaQuery).getResultList();
            transaction.commit();
            return ul;
        } catch (HibernateException e) {
            System.out.println("HiberEx " +e );
        }
        return ul;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("DELETE USERS");
            transaction.commit();
            System.out.println("Успешно: таблица очищена");
        } catch (HibernateException e) {
            System.out.println("HiberEx " +e );
        }
    }
}
