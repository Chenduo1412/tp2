package employeetracker.model.employee;

import static employeetracker.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_DATE_OF_JOINING_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_SALARY_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static employeetracker.testutil.Assert.assertThrows;
import static employeetracker.testutil.TypicalEmployees.ALICE;
import static employeetracker.testutil.TypicalEmployees.BOB;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import employeetracker.testutil.EmployeeBuilder;

public class EmployeeTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Employee employee = new EmployeeBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> employee.getTags().remove(0));
    }

    @Test
    public void isSameEmployee() {
        // same object -> returns true
        assertTrue(ALICE.isSameEmployee(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameEmployee(null));

        // same name and date of birth, all other attributes different -> returns false
        Employee editedAlice = new EmployeeBuilder(ALICE).withRole(VALID_ROLE_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withDateOfJoining(VALID_DATE_OF_JOINING_BOB)
                .withSalary(VALID_SALARY_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(ALICE.isSameEmployee(editedAlice));

        // same name, all other attributes different -> returns false
        editedAlice = new EmployeeBuilder(ALICE).withRole(VALID_ROLE_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withDateOfBirth(VALID_DATE_OF_BIRTH_BOB)
                .withDateOfJoining(VALID_DATE_OF_JOINING_BOB).withSalary(VALID_SALARY_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertFalse(ALICE.isSameEmployee(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new EmployeeBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameEmployee(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Employee editedBob = new EmployeeBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameEmployee(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new EmployeeBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameEmployee(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Employee aliceCopy = new EmployeeBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different employee -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Employee editedAlice = new EmployeeBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different role -> returns false
        editedAlice = new EmployeeBuilder(ALICE).withRole(VALID_ROLE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new EmployeeBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new EmployeeBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new EmployeeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different date of birth -> returns false
        editedAlice = new EmployeeBuilder(ALICE).withDateOfBirth(VALID_DATE_OF_BIRTH_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different date of joining -> returns false
        editedAlice = new EmployeeBuilder(ALICE).withDateOfJoining(VALID_DATE_OF_JOINING_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different salary -> returns false
        editedAlice = new EmployeeBuilder(ALICE).withSalary(VALID_SALARY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new EmployeeBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
