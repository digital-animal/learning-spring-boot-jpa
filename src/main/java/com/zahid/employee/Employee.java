package com.zahid.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.zahid.accesscard.AccessCard;
import com.zahid.emailgroup.EmailGroup;
import com.zahid.paycheck.PayCheck;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee")
public class Employee {
    
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    
    @Getter
    @Setter
    private String name;
    
    @Getter
    @Setter
    private Integer age;
    
    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    // @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dob;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private EmployeeType type;
    
    @Transient
    private String message;
    
    @Getter
    @Setter
    // @Column(unique = true, length = 10, nullable = false, updatable = false)
    @Column(unique = true, nullable = false, updatable = false)
    private String ssn;
    
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "access_card_id", referencedColumnName = "id")
    private AccessCard accessCard;
    
    @Getter
    @Setter
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PayCheck> payCheckList = new ArrayList<PayCheck>();
    
    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "employee_emailgroup_join_table", 
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<EmailGroup> groups = new ArrayList<EmailGroup>();

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", age=" + age + ", dob=" + dob + ", type=" + type + ", ssn="
                + ssn + "]";
    }

    // a method to add PayCheck to the payChecList
    public void addPayCheck(PayCheck payCheck) {
        this.payCheckList.add(payCheck);
    }

    // a method to remove PayCheck from the payChecList
    public void removePayCheck(PayCheck payCheck) {
        this.payCheckList.remove(payCheck);
    }

    // a method to add an employee to an email group
    public void joinEmailGroup(EmailGroup emailGroup) {
        this.groups.add(emailGroup);
    }

    // a method to remove an employee from an email group
    public void leaveEmailGroup(EmailGroup emailGroup) {
        this.groups.remove(emailGroup);
    }
}
