package employeetracker.model;

import employeetracker.model.employee.Employee;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an employee tracker
 */
public interface ReadOnlyEmployeeTracker {

    /**
     * Returns an unmodifiable view of the employees list.
     * This list will not contain any duplicate employees.
     */
    ObservableList<Employee> getEmployeeList();

}
