package org.example;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Entity
@Table(name = "departments")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Department
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;
    private String departmentName;
    private String location;
    private String departmentHead;

    @OneToOne(mappedBy = "managedDepartment", cascade = CascadeType.ALL)
    private Manager manager;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees;

    public Department()
    {

    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getLocation() {
        return location;
    }

    public String getDepartmentHead() {
        return departmentHead;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Manager getManagers() {
        return this.manager;
    }

    public void setDepartmentHead(String departmentHead) {
        this.departmentHead = departmentHead;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }


    public void assignManager(Manager manager) {
        if (manager != null) {
            manager.setManagedDepartment(this);
            this.manager = manager;
        }
    }

    public void reassignManager(Manager newManager) {
        if (newManager != null) {
            if (this.manager != null) {
                this.manager.setManagedDepartment(null);
            }
            newManager.setManagedDepartment(this);
            this.manager = newManager;
        }
    }


    public void addEmployee(Employee employee) {
        if (employee != null) {
            employee.setDepartment(this);
            this.employees.add(employee);
        }
    }

    public void deleteEmployee(Employee employee) {
        if (employee != null) {
            employee.setDepartment(null);
            this.employees.remove(employee);
        }
    }

    public void update(Department department) {
        if (department != null) {
            this.departmentName = department.getDepartmentName();
            this.location = department.getLocation();
            this.departmentHead = department.getDepartmentHead();
        }
    }

    public static Department createDepartment(String departmentName, String location, String departmentHead, Manager manager) {
        Department department = new Department();
        department.setDepartmentName(departmentName);
        department.setLocation(location);
        department.setDepartmentHead(departmentHead);
        department.assignManager(manager);
        return department;
    }

    public void deleteDepartment() {

        if (manager != null) {
            manager.setManagedDepartment(null);
        }
        for (Employee employee : employees) {
            employee.setDepartment(null);
        }
        this.manager = null;
        this.employees.clear();
        System.out.println("Department deleted");
    }
}
