package employeetracker.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import employeetracker.commons.core.GuiSettings;
import employeetracker.model.employee.Employee;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Employee> PREDICATE_SHOW_ALL_EMPLOYEES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' Employee Tracker file path.
     */
    Path getEmployeeTrackerFilePath();

    /**
     * Sets the user prefs' Employee Tracker file path.
     */
    void setEmployeeTrackerFilePath(Path employeeTrackerFilePath);

    /**
     * Replaces Employee Tracker data with the data in {@code employeeTracker}.
     */
    void setEmployeeTracker(ReadOnlyEmployeeTracker employeeTracker);

    /** Returns the EmployeeTracker */
    ReadOnlyEmployeeTracker getEmployeeTracker();

    /**
     * Returns true if a employee with the same identity as {@code employee} exists in the Employee Tracker.
     */
    boolean hasEmployee(Employee employee);

    /**
     * Deletes the given employee.
     * The employee must exist in the Employee Tracker.
     */
    void deleteEmployee(Employee target);

    /**
     * Adds the given employee.
     * {@code employee} must not already exist in the Employee Tracker.
     */
    void addEmployee(Employee employee);

    /**
     * Replaces the given employee {@code target} with {@code editedEmployee}.
     * {@code target} must exist in the Employee Tracker.
     * The employee identity of {@code editedEmployee} must not be the same as another existing employee
     * in the Employee Tracker.
     */
    void setEmployee(Employee target, Employee editedEmployee);

    /** Returns an unmodifiable view of the filtered employee list */
    ObservableList<Employee> getFilteredEmployeeList();

    /**
     * Updates the filter of the filtered employee list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEmployeeList(Predicate<Employee> predicate);

    /**
     * Sorts the exist employees list in Employee Tracker.
     * {@code field} must be chart n, s, d, b.
     */
    void sortEmployee(String field);

    String getStatement();
}
