package service;

import model.Boss;
import model.DTO.EmployeeDTO;
import model.DTO.TaskDTO;
import model.Employee;
import model.Task;

import java.util.List;

public interface IService {
    void login(String username, String password);
    void logout();
    Boss findBossByUsername(String username);
    Employee findEmployeeByUsername(String username);
    Task findTaskByName(String name);
    List<EmployeeDTO> findPresentEmployeesForBoss(Boss boss);
    List<TaskDTO> findAllTasksForEmployee(Employee employee);
    void sendTask(String taskName, String taskDescription, String employeeUsername);
}
