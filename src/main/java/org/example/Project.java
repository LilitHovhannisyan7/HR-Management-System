package org.example;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Project
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private String projectName;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal budget;

    @ManyToMany(mappedBy = "projects", cascade = CascadeType.ALL)
    private List<Employee> employees;

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void addEmployee(Employee employee) {
        if (employee != null) {
            this.employees.add(employee);
            employee.getProjects().add(this);
        }
    }

    public void deleteEmployee(Employee employee) {
        if (employee != null) {
            this.employees.remove(employee);
            employee.getProjects().remove(this);
        }
    }

    public void update(Project project) {
        if (project != null) {
            this.projectName = project.getProjectName();
            this.startDate = project.getStartDate();
            this.endDate = project.getEndDate();
            this.budget = project.getBudget();
        }
    }

    public static Project createProject(String projectName, LocalDate startDate, LocalDate endDate, BigDecimal budget) {
        Project project = new Project();
        project.setProjectName(projectName);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setBudget(budget);
        return project;
    }

    public void deleteProject() {

        for (Employee employee : employees) {
            employee.getProjects().remove(this);
        }

        this.employees.clear();
        System.out.println("Project deleted");

    }

}
