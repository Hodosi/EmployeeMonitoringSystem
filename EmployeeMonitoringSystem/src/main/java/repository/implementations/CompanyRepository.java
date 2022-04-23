package repository.implementations;

import model.Company;
import org.hibernate.SessionFactory;
import repository.interfaces.ICompanyRepository;

public class CompanyRepository implements ICompanyRepository {
    private final SessionFactory sessionFactory;

    public CompanyRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Company entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Company entity) {

    }

    @Override
    public Company findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Company> findAll() {
        return null;
    }
}
