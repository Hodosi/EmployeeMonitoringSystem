package repository.implementations;

import model.Boss;
import model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.interfaces.IBossRepository;

public class BossRepository implements IBossRepository {
    private final SessionFactory sessionFactory;

    public BossRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Boss entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Boss entity) {

    }

    @Override
    public Boss findOne(Integer integer) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Boss boss = session.createQuery("from Boss where id = :id", Boss.class)
                        .setParameter("id", integer)
                        .setMaxResults(1)
                        .uniqueResult();

                tx.commit();

                return boss;

            } catch (RuntimeException ex) {
                System.err.println("Eroare la findOne Boss: " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }

        return null;
    }

    @Override
    public Iterable<Boss> findAll() {
        return null;
    }

    @Override
    public Boss findByUsername(String username) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Boss boss = session.createQuery("from Boss where username = :username", Boss.class)
                        .setParameter("username", username)
                        .setMaxResults(1)
                        .uniqueResult();

                tx.commit();

                return boss;

            } catch (RuntimeException ex) {
                System.err.println("Eroare la findByName Boss: " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }

        return null;
    }
}
