package com.zahid.paycheck;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.zahid.employee.Employee;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pay_check")
public class PayCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Getter
    @Setter
    @Column(name = "issue_date")
    @Temporal(TemporalType.DATE)
    private Date payPeriodStart;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    @Column(name = "expire_date")
    private Date payPeriodEnd;
    
    @Getter
    @Setter
    @Column(name = "salary")
    private Integer salary;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @Override
    public String toString() {
        return "PayCheck [id=" + id + ", payPeriodStart=" + payPeriodStart + ", payPeriodEnd=" + payPeriodEnd
                + ", salary=" + salary + "]";
    }

}