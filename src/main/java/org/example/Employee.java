package org.example;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long employeeId;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate hireDate;
    private String jobTitle;


    @ManyToOne
    private Department department;

    @ManyToMany(mappedBy = "employees", cascade = CascadeType.ALL)
    private List<Project> projects;

    public Employee() {

    }

    public Employee(Long employeeId, String name, String email, String phoneNumber, LocalDate hireDate, String jobTitle) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.jobTitle = jobTitle;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }


    public void update(Employee updatedEmployee) {
        if (updatedEmployee != null) {

            this.setName(updatedEmployee.getName());
            this.setEmail(updatedEmployee.getEmail());
            this.setPhoneNumber(updatedEmployee.getPhoneNumber());
            this.setHireDate(updatedEmployee.getHireDate());
            this.setJobTitle(updatedEmployee.getJobTitle());

            if (updatedEmployee.getDepartment() != null) {
                this.setDepartment(updatedEmployee.getDepartment());
            }

            if (updatedEmployee.getProjects() != null) {
                this.getProjects().clear();
                this.getProjects().addAll(updatedEmployee.getProjects());
            }
        }
    }
}
