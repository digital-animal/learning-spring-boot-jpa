package com.zahid.emailgroup;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.zahid.employee.Employee;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "email_group")
public class EmailGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
    private List<Employee> employees = new ArrayList<>();
    
    @Override
    public String toString() {
        return "EmailGroup [id=" + id + ", name=" + name + "]";
    }
    
    // a method to add new employee to the group
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    // a method to remove an employee from the group
    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
    }
}
