package employeetracker.logic.commands;

import static employeetracker.logic.commands.CommandTestUtil.DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.DESC_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_DATE_OF_JOINING_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_SALARY_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import employeetracker.testutil.EditEmployeeDescriptorBuilder;

public class EditEmployeeDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditEmployeeDescriptor descriptorWithSameValues = new EditCommand.EditEmployeeDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditCommand.EditEmployeeDescriptor editedAmy = new EditEmployeeDescriptorBuilder(DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different role -> returns false
        editedAmy = new EditEmployeeDescriptorBuilder(DESC_AMY).withRole(VALID_ROLE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditEmployeeDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditEmployeeDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditEmployeeDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different date of birth -> returns false
        editedAmy = new EditEmployeeDescriptorBuilder(DESC_AMY).withDateOfBirth(VALID_DATE_OF_BIRTH_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different date of joining -> returns false
        editedAmy = new EditEmployeeDescriptorBuilder(DESC_AMY).withDateOfJoining(VALID_DATE_OF_JOINING_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different salary -> returns false
        editedAmy = new EditEmployeeDescriptorBuilder(DESC_AMY).withSalary(VALID_SALARY_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditEmployeeDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
