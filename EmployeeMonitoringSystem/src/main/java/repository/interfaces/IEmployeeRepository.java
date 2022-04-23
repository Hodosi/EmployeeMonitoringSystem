package repository.interfaces;

import model.Boss;
import model.Employee;

import java.util.List;

public interface IEmployeeRepository extends IRepository<Integer, Employee> {
    Employee findByUsername(String username);
    List<Employee> findAllForBoss(Boss boss);
}
