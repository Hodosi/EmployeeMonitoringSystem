package service;

import repository.interfaces.IBossRepository;
import repository.interfaces.ICompanyRepository;
import repository.interfaces.IEmployeeRepository;
import repository.interfaces.ITaskRepository;

public class Service implements IService {
    private ICompanyRepository companyRepository;
    private IBossRepository bossRepository;
    private IEmployeeRepository employeeRepository;
    private ITaskRepository taskRepository;

    public Service(ICompanyRepository companyRepository, IBossRepository bossRepository, IEmployeeRepository employeeRepository, ITaskRepository taskRepository) {
        this.companyRepository = companyRepository;
        this.bossRepository = bossRepository;
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }
}
