package models;

import java.util.*;

public class Employee {

    private Long id;
    private String email;
    private String password;
    private String status;

    public Employee() {
    }

    public Employee(Long id, String email, String password, String status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(email, employee.email) &&
            Objects.equals(password, employee.password) && Objects.equals(status, employee.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, status);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", status='" + status + '\'' +
            '}';
    }
}
