package org.example;

import jakarta.persistence.*;

@Entity
@Table(name = "managers")
public class Manager extends Employee {
    @Enumerated(EnumType.STRING)
    private ManagementLevel managementLevel;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department managedDepartment;

    @OneToOne(mappedBy = "manager", cascade = CascadeType.ALL)
    private Department department;
    public enum ManagementLevel {
        TOP_LEVEL,
        MID_LEVEL,
        FIRST_LINE
    }
    public Manager()
    {

    }

    public ManagementLevel getManagementLevel() {
        return managementLevel;
    }

    public void setManagementLevel(ManagementLevel managementLevel) {
        this.managementLevel = managementLevel;
    }

    public Department getManagedDepartment() {
        return managedDepartment;
    }

    public void setManagedDepartment(Department managedDepartment) {
        this.managedDepartment = managedDepartment;
    }


    public void updateManagementLevel(ManagementLevel newLevel) {
        if (newLevel != null) {
            this.managementLevel = newLevel;
        }
    }
}
