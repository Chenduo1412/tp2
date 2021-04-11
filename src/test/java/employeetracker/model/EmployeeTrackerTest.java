package employeetracker.model;

import static employeetracker.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static employeetracker.testutil.Assert.assertThrows;
import static employeetracker.testutil.TypicalEmployees.ALICE;
import static employeetracker.testutil.TypicalEmployees.getTypicalEmployeeTracker;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import employeetracker.model.employee.Employee;
import employeetracker.model.employee.exceptions.DuplicateEmployeeException;
import employeetracker.testutil.EmployeeBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmployeeTrackerTest {

    private final EmployeeTracker employeeTracker = new EmployeeTracker();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), employeeTracker.getEmployeeList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> employeeTracker.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyEmployeeTracker_replacesData() {
        EmployeeTracker newData = getTypicalEmployeeTracker();
        employeeTracker.resetData(newData);
        assertEquals(newData, employeeTracker);
    }

    @Test
    public void resetData_withDuplicateEmployees_throwsDuplicateEmployeeException() {
        // Two employees with the same identity fields
        Employee editedAlice = new EmployeeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Employee> newEmployees = Arrays.asList(ALICE, editedAlice);
        EmployeeTrackerStub newData = new EmployeeTrackerStub(newEmployees);

        assertThrows(DuplicateEmployeeException.class, () -> employeeTracker.resetData(newData));
    }

    @Test
    public void hasEmployee_nullEmployee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> employeeTracker.hasEmployee(null));
    }

    @Test
    public void hasEmployee_employeeNotInEmployeeTracker_returnsFalse() {
        assertFalse(employeeTracker.hasEmployee(ALICE));
    }

    @Test
    public void hasEmployee_employeeInEmployeeTracker_returnsTrue() {
        employeeTracker.addEmployee(ALICE);
        assertTrue(employeeTracker.hasEmployee(ALICE));
    }

    @Test
    public void hasEmployee_employeeWithSameIdentityFieldsInEmployeeTracker_returnsTrue() {
        employeeTracker.addEmployee(ALICE);
        Employee editedAlice = new EmployeeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(employeeTracker.hasEmployee(editedAlice));
    }

    @Test
    public void getEmployeeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> employeeTracker.getEmployeeList().remove(0));
    }

    /**
     * A stub ReadOnlyEmployeeTracker whose employees list can violate interface constraints.
     */
    private static class EmployeeTrackerStub implements ReadOnlyEmployeeTracker {
        private final ObservableList<Employee> employees = FXCollections.observableArrayList();

        EmployeeTrackerStub(Collection<Employee> employees) {
            this.employees.setAll(employees);
        }

        @Override
        public ObservableList<Employee> getEmployeeList() {
            return employees;
        }
    }

}
