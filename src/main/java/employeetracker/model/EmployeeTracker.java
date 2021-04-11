package employeetracker.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import employeetracker.model.employee.Employee;
import employeetracker.model.employee.UniqueEmployeeList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the employee-tracker level
 * Duplicates are not allowed (by .isSameEmployee comparison)
 */
public class EmployeeTracker implements ReadOnlyEmployeeTracker {

    private final UniqueEmployeeList employees;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        employees = new UniqueEmployeeList();
    }

    public EmployeeTracker() {}

    /**
     * Creates an EmployeeTracker using the Employees in the {@code toBeCopied}
     */
    public EmployeeTracker(ReadOnlyEmployeeTracker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the employee list with {@code employees}.
     * {@code employees} must not contain duplicate employees.
     */
    public void setEmployees(List<Employee> employees) {
        this.employees.setEmployees(employees);
    }

    /**
     * Resets the existing data of this {@code EmployeeTracker} with {@code newData}.
     */
    public void resetData(ReadOnlyEmployeeTracker newData) {
        requireNonNull(newData);

        setEmployees(newData.getEmployeeList());
    }

    //// employee-level operations

    /**
     * Returns true if a employee with the same identity as {@code employee} exists in the Employee Tracker.
     */
    public boolean hasEmployee(Employee employee) {
        requireNonNull(employee);
        return employees.contains(employee);
    }

    /**
     * Adds a employee to the Employee Tracker.
     * The employee must not already exist in the Employee Tracker.
     */
    public void addEmployee(Employee p) {
        employees.add(p);
    }

    /**
     * Replaces the given employee {@code target} in the list with {@code editedEmployee}.
     * {@code target} must exist in the Employee Tracker.
     * The employee identity of {@code editedEmployee} must not be the same as another existing employee in the
     * Employee Tracker.
     */
    public void setEmployee(Employee target, Employee editedEmployee) {
        requireNonNull(editedEmployee);

        employees.setEmployee(target, editedEmployee);
    }

    /**
     * Removes {@code key} from this {@code EmployeeTracker}.
     * {@code key} must exist in the Employee Tracker.
     */
    public void sortEmployee(String field) {
        employees.sortEmployee(field);
    }

    /**
     * Removes {@code key} from this {@code EmployeeTracker}.
     * {@code key} must exist in the Employee Tracker.
     */
    public void removeEmployee(Employee key) {
        employees.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return employees.asUnmodifiableObservableList().size() + " employees";
        // TODO: refine later
    }

    @Override
    public ObservableList<Employee> getEmployeeList() {
        return employees.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeTracker // instanceof handles nulls
                && employees.equals(((EmployeeTracker) other).employees));
    }

    @Override
    public int hashCode() {
        return employees.hashCode();
    }

    /**
     *  Print the user stats statements.
     */
    public String getStatement() {
        String statement = "";
        statement += "Total Employees: " + employees.getNoOfEmployees() + "\n";
        statement += "Total Salary: $" + String.format("%.2f", employees.getTotalSalary()) + "\n";
        statement += "Highest Salary: $" + employees.getHighestSalary() + "\n";
        statement += "Lowest Salary: $" + employees.getLowestSalary() + "\n";
        statement += "Average Salary: $" + String.format("%.2f", employees.getAverageSalary()) + "\n";
        statement += "Longest Tenure: " + employees.getLongestTenure() + "\n";
        statement += "Shortest Tenure: " + employees.getShortestTenure() + "\n";
        statement += "Average Tenure: " + employees.getAverageTenure();
        return statement;
    }
}
