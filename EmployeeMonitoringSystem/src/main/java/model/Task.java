package model;

public class Task {
    private int id;
    private String name;
    private String description;

    // relation
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // relation

    public Task(){

    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.employee = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
