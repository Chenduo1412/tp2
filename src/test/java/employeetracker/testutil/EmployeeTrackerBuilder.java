package employeetracker.testutil;

import employeetracker.model.EmployeeTracker;
import employeetracker.model.employee.Employee;

/**
 * A utility class to help with building EmployeeTracker objects.
 * Example usage: <br>
 *     {@code EmployeeTracker ab = new EmployeeTrackerBuilder().withEmployee("John", "Doe").build();}
 */
public class EmployeeTrackerBuilder {

    private EmployeeTracker employeeTracker;

    public EmployeeTrackerBuilder() {
        employeeTracker = new EmployeeTracker();
    }

    public EmployeeTrackerBuilder(EmployeeTracker employeeTracker) {
        this.employeeTracker = employeeTracker;
    }

    /**
     * Adds a new {@code Employee} to the {@code EmployeeTracker} that we are building.
     */
    public EmployeeTrackerBuilder withEmployee(Employee employee) {
        employeeTracker.addEmployee(employee);
        return this;
    }

    public EmployeeTracker build() {
        return employeeTracker;
    }
}
