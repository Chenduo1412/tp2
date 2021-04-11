package employeetracker.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import employeetracker.commons.exceptions.IllegalValueException;
import employeetracker.model.EmployeeTracker;
import employeetracker.model.ReadOnlyEmployeeTracker;
import employeetracker.model.employee.Employee;

/**
 * An Immutable EmployeeTracker that is serializable to JSON format.
 */
@JsonRootName(value = "employeetracker")
class JsonSerializableEmployeeTracker {

    public static final String MESSAGE_DUPLICATE_EMPLOYEE = "Employees list contains duplicate employee(s).";

    private final List<JsonAdaptedEmployee> employees = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEmployeeTracker} with the given employees.
     */
    @JsonCreator
    public JsonSerializableEmployeeTracker(@JsonProperty("employees") List<JsonAdaptedEmployee> employees) {
        this.employees.addAll(employees);
    }

    /**
     * Converts a given {@code ReadOnlyEmployeeTracker} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEmployeeTracker}.
     */
    public JsonSerializableEmployeeTracker(ReadOnlyEmployeeTracker source) {
        employees.addAll(source.getEmployeeList().stream().map(JsonAdaptedEmployee::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Employee Tracker into the model's {@code EmployeeTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EmployeeTracker toModelType() throws IllegalValueException {
        EmployeeTracker employeeTracker = new EmployeeTracker();
        for (JsonAdaptedEmployee jsonAdaptedEmployee : employees) {
            Employee employee = jsonAdaptedEmployee.toModelType();
            if (employeeTracker.hasEmployee(employee)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EMPLOYEE);
            }
            employeeTracker.addEmployee(employee);
        }
        return employeeTracker;
    }

}
