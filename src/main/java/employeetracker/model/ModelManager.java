package employeetracker.model;

import static employeetracker.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import employeetracker.commons.core.GuiSettings;
import employeetracker.commons.core.LogsCenter;
import employeetracker.model.employee.Employee;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the employee tracker data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final EmployeeTracker employeeTracker;
    private final UserPrefs userPrefs;
    private final FilteredList<Employee> filteredEmployees;

    /**
     * Initializes a ModelManager with the given employeeTracker and userPrefs.
     */
    public ModelManager(ReadOnlyEmployeeTracker employeeTracker, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(employeeTracker, userPrefs);

        logger.fine("Initializing with Employee Tracker: " + employeeTracker + " and user prefs " + userPrefs);

        this.employeeTracker = new EmployeeTracker(employeeTracker);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredEmployees = new FilteredList<>(this.employeeTracker.getEmployeeList());
    }

    public ModelManager() {
        this(new EmployeeTracker(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getEmployeeTrackerFilePath() {
        return userPrefs.getEmployeeTrackerFilePath();
    }

    @Override
    public void setEmployeeTrackerFilePath(Path employeeTrackerFilePath) {
        requireNonNull(employeeTrackerFilePath);
        userPrefs.setEmployeeTrackerFilePath(employeeTrackerFilePath);
    }

    //=========== EmployeeTracker ================================================================================

    @Override
    public void setEmployeeTracker(ReadOnlyEmployeeTracker employeeTracker) {
        this.employeeTracker.resetData(employeeTracker);
    }

    @Override
    public ReadOnlyEmployeeTracker getEmployeeTracker() {
        return employeeTracker;
    }

    @Override
    public boolean hasEmployee(Employee employee) {
        requireNonNull(employee);
        return employeeTracker.hasEmployee(employee);
    }

    @Override
    public void deleteEmployee(Employee target) {
        employeeTracker.removeEmployee(target);
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeTracker.addEmployee(employee);
        updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EMPLOYEES);
    }

    @Override
    public void setEmployee(Employee target, Employee editedEmployee) {
        requireAllNonNull(target, editedEmployee);
        employeeTracker.setEmployee(target, editedEmployee);
    }

    @Override
    public void sortEmployee(String field) {
        employeeTracker.sortEmployee(field);
        updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EMPLOYEES);
    }


    //=========== Filtered Employee List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Employee} backed by the internal list of
     * {@code versionedEmployeeTracker}
     */
    @Override
    public ObservableList<Employee> getFilteredEmployeeList() {
        return filteredEmployees;
    }

    @Override
    public void updateFilteredEmployeeList(Predicate<Employee> predicate) {
        requireNonNull(predicate);
        filteredEmployees.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return employeeTracker.equals(other.employeeTracker)
                && userPrefs.equals(other.userPrefs)
                && filteredEmployees.equals(other.filteredEmployees);
    }
    public String getStatement() {
        String statement = employeeTracker.getStatement();
        return statement;
    }
}
