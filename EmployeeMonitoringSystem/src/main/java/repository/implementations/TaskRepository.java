package repository.implementations;

import model.Employee;
import model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.interfaces.ITaskRepository;

import java.util.List;

public class TaskRepository implements ITaskRepository {
    private final SessionFactory sessionFactory;

    public TaskRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Task entity) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la save Task: " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void delete(Integer integer) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Task task = session.createQuery("from Task where id = :id", Task.class)
                                .setParameter("id", integer)
                                        .setMaxResults(1)
                                                .uniqueResult();

                System.err.println("Stergem taskul" + task.getName());

                session.delete(task);
                tx.commit();

            } catch (RuntimeException ex) {
                System.err.println("Eroare la delete Task: " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void update(Integer integer, Task entity) {

    }

    @Override
    public Task findOne(Integer integer) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Task task = session.createQuery("from Task where id = :id", Task.class)
                        .setParameter("id", integer)
                        .setMaxResults(1)
                        .uniqueResult();

                tx.commit();

                return task;

            } catch (RuntimeException ex) {
                System.err.println("Eroare la findOne Task: " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }

        return null;
    }

    @Override
    public Iterable<Task> findAll() {
        return null;
    }

    @Override
    public Task findByName(String name) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Task task = session.createQuery("from Task where name = :name", Task.class)
                        .setParameter("name", name)
                        .setMaxResults(1)
                        .uniqueResult();

                tx.commit();

                return task;

            } catch (RuntimeException ex) {
                System.err.println("Eroare la findByName Task: " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }

        return null;
    }

    @Override
    public List<Task> findAllForEmployee(Employee employee) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                List<Task> tasks = session.createQuery("from Task where employee_id = :id", Task.class)
                                .setParameter("id", employee.getId())
                                        .list();

                tx.commit();

                return tasks;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la find All for employee "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }

        return null;
    }
}
