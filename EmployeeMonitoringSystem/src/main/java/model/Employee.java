package model;

import java.time.LocalTime;
import java.util.Set;

public class Employee {
    private int id;
    private String password;
    private String username;
    private String name;
    private LocalTime loginTime;

    // relation
    private Boss boss;

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }
    // relation
    private Set<Task> tasks;

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
    // relation

    public Employee(){

    }

    public Employee(String password, String username, String name) {
        this.password = password;
        this.username = username;
        this.name = name;
        this.boss = null;
        this.tasks = null;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public LocalTime getLoginTime() {
        return loginTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLoginTime(LocalTime loginTime) {
        this.loginTime = loginTime;
    }
}
