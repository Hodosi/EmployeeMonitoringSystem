package service;

import model.Boss;
import model.DTO.EmployeeDTO;
import model.DTO.TaskDTO;
import model.Employee;
import model.Task;
import utils.observer.Observer;

import java.util.List;

public interface IService {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void login(String username, String password);
    void logoutEmployee(Employee employee);
    Boss findBossByUsername(String username);
    Employee findEmployeeByUsername(String username);
    Task findTaskByName(String name);
    List<EmployeeDTO> findPresentEmployeesForBoss(Boss boss);
    List<TaskDTO> findAllTasksForEmployee(Employee employee);
    void sendTask(String taskName, String taskDescription, String employeeUsername);
    void presentAction(Integer hh, Integer mm, Employee employee);
    void finishTask(Integer id);
}
