package model;

import java.util.Set;

public class Company {
    private int id;
    private String name;

    // relation
    private Set<Boss> bosses;

    public Set<Boss> getBosses() {
        return bosses;
    }

    public void setBosses(Set<Boss> bosses) {
        this.bosses = bosses;
    }
    // relation

    public Company(){

    }

    public Company(String name) {
        this.name = name;
        this.bosses = null;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
