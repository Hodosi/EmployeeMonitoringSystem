package repository.interfaces;

import model.Employee;
import model.Task;

import java.util.List;

public interface ITaskRepository extends IRepository<Integer, Task> {
    Task findByName(String name);
    List<Task> findAllForEmployee(Employee employee);
}
