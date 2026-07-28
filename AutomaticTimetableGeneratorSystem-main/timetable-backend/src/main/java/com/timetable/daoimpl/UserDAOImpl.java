package com.timetable.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.timetable.dao.UserDAO;
import com.timetable.entity.User;

@Repository

public class UserDAOImpl implements UserDAO {

    private final SessionFactory sessionFactory;

    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public User save(User user) {

        getSession().persist(user);

        return user;
    }

    @Override
    public User update(User user) {

        return (User) getSession().merge(user);
    }

    @Override
    public void delete(Long userId) {

        User user = findById(userId);

        if (user != null) {
            getSession().remove(user);
        }
    }

    @Override
    public User findById(Long userId) {

        return getSession().get(User.class, userId);
    }

    @Override
    public List<User> findAll() {

        String hql = "FROM User";

        return getSession()
                .createQuery(hql, User.class)
                .getResultList();
    }

    @Override
    public User findByUsername(String username) {

        String hql = "FROM User WHERE username = :username";

        Query<User> query = getSession().createQuery(hql, User.class);

        query.setParameter("username", username);

        return query.uniqueResult();
    }

    @Override
    public User findByEmail(String email) {

        String hql = "FROM User WHERE email = :email";

        Query<User> query = getSession().createQuery(hql, User.class);

        query.setParameter("email", email);

        return query.uniqueResult();
    }

    @Override
    public User findByMobile(String mobile) {

        String hql = "FROM User WHERE mobile = :mobile";

        Query<User> query = getSession().createQuery(hql, User.class);

        query.setParameter("mobile", mobile);

        return query.uniqueResult();
    }

    @Override
    public boolean existsByUsername(String username) {

        String hql = "SELECT COUNT(u) FROM User u WHERE u.username = :username";

        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("username", username)
                .uniqueResult();

        return count != null && count > 0;
    }

    @Override
    public boolean existsByEmail(String email) {

        String hql = "SELECT COUNT(u) FROM User u WHERE u.email = :email";

        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("email", email)
                .uniqueResult();

        return count != null && count > 0;
    }

    @Override
    public boolean existsByMobile(String mobile) {

        String hql = "SELECT COUNT(u) FROM User u WHERE u.mobile = :mobile";

        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("mobile", mobile)
                .uniqueResult();

        return count != null && count > 0;
    }

}