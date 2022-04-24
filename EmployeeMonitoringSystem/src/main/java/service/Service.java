package service;

import exception.SystemException;
import model.Boss;
import model.DTO.EmployeeDTO;
import model.DTO.TaskDTO;
import model.Employee;
import model.Task;
import repository.interfaces.IBossRepository;
import repository.interfaces.ICompanyRepository;
import repository.interfaces.IEmployeeRepository;
import repository.interfaces.ITaskRepository;

import java.util.ArrayList;
import java.util.List;

public class Service implements IService {
    private final ICompanyRepository companyRepository;
    private final IBossRepository bossRepository;
    private final IEmployeeRepository employeeRepository;
    private final ITaskRepository taskRepository;

    public Service(ICompanyRepository companyRepository, IBossRepository bossRepository, IEmployeeRepository employeeRepository, ITaskRepository taskRepository) {
        this.companyRepository = companyRepository;
        this.bossRepository = bossRepository;
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void login(String username, String password) {
        Boss boss = bossRepository.findByUsername(username);
        if(boss != null) {
            if(!boss.getPassword().equals(password)){
                throw new SystemException("wrong password for boss");
            }
        }
        else{
            Employee employee = employeeRepository.findByUsername(username);
            if(employee != null){
                if(!employee.getPassword().equals(password)){
                    throw new SystemException("wrong password for employee");
                }
            }
            else {
                throw new SystemException("wrong username");
            }
        }

    }

    @Override
    public void logout() {

    }

    @Override
    public Boss findBossByUsername(String username) {
        return bossRepository.findByUsername(username);
    }

    @Override
    public Employee findEmployeeByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    @Override
    public Task findTaskByName(String name) {
        return taskRepository.findByName(name);
    }

    @Override
    public List<EmployeeDTO> findPresentEmployeesForBoss(Boss boss) {
        List<Employee> employees = employeeRepository.findAllForBoss(boss);
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for(Employee employee : employees){
            EmployeeDTO employeeDTO = new EmployeeDTO(employee.getId(), employee.getUsername(), employee.getLoginTime().toString());
            employeeDTOs.add(employeeDTO);
        }

        return employeeDTOs;
    }

    @Override
    public List<TaskDTO> findAllTasksForEmployee(Employee employee) {
        List<Task> tasks = taskRepository.findAllForEmployee(employee);
        List<TaskDTO> taskDTOs = new ArrayList<>();
        for(Task task : tasks){
            TaskDTO taskDTO = new TaskDTO(task.getId(), task.getName(), task.getDescription());
            taskDTOs.add(taskDTO);
        }

        return taskDTOs;
    }

    @Override
    public void sendTask(String taskName, String taskDescription, String employeeUsername) {
        Employee employee = employeeRepository.findByUsername(employeeUsername);
        if(employee == null){
            throw new SystemException("employee doesn't exists");
        }
        Task task = new Task(taskName, taskDescription);
        task.setEmployee(employee);

        taskRepository.save(task);
    }
}
