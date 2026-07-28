package com.timetable.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.timetable.dao.DepartmentDAO;
import com.timetable.entity.Department;

@Repository
public class DepartmentDAOImpl implements DepartmentDAO {

    private final SessionFactory sessionFactory;

    public DepartmentDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Department save(Department department) {

        getSession().persist(department);

        return department;
    }

    @Override
    public Department update(Department department) {

        return (Department) getSession().merge(department);
    }

    @Override
    public void delete(Long departmentId) {

        Department department = findById(departmentId);

        if (department != null) {
            getSession().remove(department);
        }
    }

    @Override
    public Department findById(Long departmentId) {

        return getSession().get(Department.class, departmentId);
    }

    @Override
    public List<Department> findAll() {

        String hql = "FROM Department ORDER BY departmentName";

        return getSession()
                .createQuery(hql, Department.class)
                .getResultList();
    }

    @Override
    public Department findByDepartmentCode(String departmentCode) {

        String hql =
                "FROM Department WHERE departmentCode = :departmentCode";

        Query<Department> query =
                getSession().createQuery(hql, Department.class);

        query.setParameter("departmentCode", departmentCode);

        return query.uniqueResult();
    }

    @Override
    public Department findByDepartmentName(String departmentName) {

        String hql =
                "FROM Department WHERE departmentName = :departmentName";

        Query<Department> query =
                getSession().createQuery(hql, Department.class);

        query.setParameter("departmentName", departmentName);

        return query.uniqueResult();
    }

    @Override
    public boolean existsByDepartmentCode(String departmentCode) {

        String hql =
                "SELECT COUNT(d) FROM Department d " +
                "WHERE d.departmentCode = :departmentCode";

        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("departmentCode", departmentCode)
                .uniqueResult();

        return count != null && count > 0;
    }

    @Override
    public boolean existsByDepartmentName(String departmentName) {

        String hql =
                "SELECT COUNT(d) FROM Department d " +
                "WHERE d.departmentName = :departmentName";

        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("departmentName", departmentName)
                .uniqueResult();

        return count != null && count > 0;
    }

}