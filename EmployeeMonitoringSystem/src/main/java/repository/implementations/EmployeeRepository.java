package repository.implementations;

import model.Boss;
import model.Employee;
import model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.interfaces.IEmployeeRepository;

import java.util.Date;
import java.util.List;

public class EmployeeRepository implements IEmployeeRepository {
    private final SessionFactory sessionFactory;

    public EmployeeRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Employee entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Employee entity) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();

                session.update(entity);

                tx.commit();

            } catch(RuntimeException ex){
                System.err.println("Eroare la update Employee "+ex);
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Employee findOne(Integer integer) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Employee employee = session.createQuery("from Employee where id = :id", Employee.class)
                        .setParameter("id", integer)
                        .setMaxResults(1)
                        .uniqueResult();

                tx.commit();

                return employee;

            } catch (RuntimeException ex) {
                System.err.println("Eroare la findOne Employee: " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }

        return null;
    }

    @Override
    public Iterable<Employee> findAll() {
        return null;
    }

    @Override
    public Employee findByUsername(String username) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Employee employee = session.createQuery("from Employee where username = :username", Employee.class)
                        .setParameter("username", username)
                        .setMaxResults(1)
                        .uniqueResult();

                tx.commit();

                return employee;

            } catch (RuntimeException ex) {
                System.err.println("Eroare la findByName Employee: " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }

        return null;
    }

    @Override
    public List<Employee> findAllForBoss(Boss boss) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                List<Employee> employees = session.createQuery("from Employee where boss_id = :id", Employee.class)
                        .setParameter("id", boss.getId())
                        .list();

                tx.commit();

                return employees;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la find all for Boss "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }

        return null;
    }
}
