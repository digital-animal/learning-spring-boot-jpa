package com.zahid.accesscard;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.zahid.employee.Employee;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "access_card")
public class AccessCard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Getter
    @Setter
    @Column(name = "issue_date")
    private Date issueDate;

    @Getter
    @Setter
    @Column(name = "is_active")
    private Boolean isActive;

    @Getter
    @Setter
    @Column(name = "firmware_version")
    private String firmwareVersion;

    @Getter
    @Setter
    @OneToOne(mappedBy = "accessCard")
    private Employee employee;

    @Override
    public String toString() {
        return "AccessCard [id=" + id + ", issueDate=" + issueDate + ", isActive=" + isActive + ", firmwareVersion="
                + firmwareVersion + "]";
    }
}
