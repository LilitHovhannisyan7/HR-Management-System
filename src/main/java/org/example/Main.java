package org.example;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("name");
    private static final EntityManager em = emf.createEntityManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            printMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addDepartment();
                    break;
                case 2:
                    updateDepartment();
                    break;
                case 3:
                    listDepartments();
                    break;
                case 4:
                    deleteDepartment();
                    break;
                case 5:
                    addEmployee();
                    break;
                case 6:
                    updateEmployee();
                    break;
                case 7:
                    listEmployees();
                    break;
                case 8:
                    deleteEmployee();
                    break;
                case 9:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void printMenu() {
        System.out.println("Main Menu");
        System.out.println("1. Add Department");
        System.out.println("2. Update Department");
        System.out.println("3. List Departments");
        System.out.println("4. Delete Department");
        System.out.println("5. Add Employee");
        System.out.println("6. Update Employee");
        System.out.println("7. List Employees");
        System.out.println("8. Delete Employee");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    private static void addDepartment() {
        System.out.println("Enter department name:");
        String name = scanner.nextLine();

        System.out.println("Enter department location:");
        String location = scanner.nextLine();

        System.out.println("Enter department head:");
        String head = scanner.nextLine();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Manager manager = new Manager();
        manager.setManagementLevel(Manager.ManagementLevel.TOP_LEVEL);

        Department department = Department.createDepartment(name, location, head, manager);

        em.persist(manager);
        em.persist(department);

        transaction.commit();
        System.out.println("Department added");
    }

    private static void updateDepartment() {
        System.out.println("Enter department ID to update:");
        Long departmentId = Long.parseLong(scanner.nextLine());

        System.out.println("Enter new department name:");
        String name = scanner.nextLine();

        System.out.println("Enter new department location:");
        String location = scanner.nextLine();

        System.out.println("Enter new department head:");
        String head = scanner.nextLine();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Department department = em.find(Department.class, departmentId);
        department.update(new Department());

        transaction.commit();
        System.out.println("Department updated");
    }

    private static void listDepartments() {
        List<Department> departments = em.createQuery("SELECT d FROM Department d", Department.class).getResultList();

        System.out.println("Departments");
        for (Department department : departments) {
            System.out.println(department.getDepartmentName() + " - " + department.getLocation());
        }
    }

    private static void deleteDepartment() {
        System.out.println("Enter department ID to delete:");
        Long departmentId = Long.parseLong(scanner.nextLine());

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Department department = em.find(Department.class, departmentId);
        department.deleteDepartment();

        em.remove(department);

        transaction.commit();
        System.out.println("Department deleted successfully.");
    }

    private static void addEmployee() {
        System.out.println("Enter employee name:");
        String name = scanner.nextLine();

        System.out.println("Enter employee email:");
        String email = scanner.nextLine();

        System.out.println("Enter employee phone number:");
        String phoneNumber = scanner.nextLine();

        System.out.println("Enter employee hire date (yyyy-mm-dd):");
        LocalDate hireDate = LocalDate.parse(scanner.nextLine());

        System.out.println("Enter employee job title:");
        String jobTitle = scanner.nextLine();

        System.out.println("Enter department ID for the employee:");
        Long departmentId = Long.parseLong(scanner.nextLine());

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Department department = em.find(Department.class, departmentId);
        Employee employee = new Employee(null, name, email, phoneNumber, hireDate, jobTitle);
        department.addEmployee(employee);

        em.persist(employee);

        transaction.commit();
        System.out.println("Employee added successfully.");
    }

    private static void updateEmployee() {
        System.out.println("Enter employee ID to update:");
        Long employeeId = Long.parseLong(scanner.nextLine());

        System.out.println("Enter new employee name:");
        String name = scanner.nextLine();

        System.out.println("Enter new employee email:");
        String email = scanner.nextLine();

        System.out.println("Enter new employee phone number:");
        String phoneNumber = scanner.nextLine();

        System.out.println("Enter new employee hire date (yyyy-mm-dd):");
        LocalDate hireDate = LocalDate.parse(scanner.nextLine());

        System.out.println("Enter new employee job title:");
        String jobTitle = scanner.nextLine();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Employee employee = em.find(Employee.class, employeeId);
        employee.update(new Employee());

        transaction.commit();
        System.out.println("Employee updated successfully.");
    }

    private static void listEmployees() {
        List<Employee> employees = em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();

        System.out.println("==== Employees ====");
        for (Employee employee : employees) {
            System.out.println(employee.getName() + " - " + employee.getDepartment().getDepartmentName());
        }
    }

    private static void deleteEmployee() {
        System.out.println("Enter employee ID to delete:");
        Long employeeId = Long.parseLong(scanner.nextLine());

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Employee employee = em.find(Employee.class, employeeId);
        Department department = em.find(Department.class, employeeId);
        department.deleteEmployee(employee);

        em.remove(employee);

        transaction.commit();
        System.out.println("Employee deleted successfully.");
    }
}
