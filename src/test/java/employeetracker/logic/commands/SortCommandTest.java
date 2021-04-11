package employeetracker.logic.commands;

import static employeetracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static employeetracker.testutil.TypicalEmployees.getTypicalEmployeeTracker;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import employeetracker.model.Model;
import employeetracker.model.ModelManager;
import employeetracker.model.UserPrefs;



/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
class SortCommandTest {

    private final Model model = new ModelManager(getTypicalEmployeeTracker(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalEmployeeTracker(), new UserPrefs());

    @Test
    public void equals() {

        SortCommand firstSortCommand = new SortCommand("n");
        SortCommand secondSortCommand = new SortCommand("s");
        SortCommand thirdSortCommand = new SortCommand("j");
        SortCommand fourthSortCommand = new SortCommand("b");

        // same object -> returns true
        assertEquals(firstSortCommand, firstSortCommand);

        // same values -> returns true
        SortCommand secondSortCommandCopy = new SortCommand("s");
        assertEquals(secondSortCommandCopy, secondSortCommand);

        // different types -> returns false
        assertNotEquals(thirdSortCommand, 1);

        // null -> returns false
        assertNotEquals(fourthSortCommand, null);

        // different value -> returns false
        assertNotEquals(secondSortCommand, firstSortCommand);
    }

    @Test
    public void execute_validArgsN_success() {
        SortCommand testSortCommand = new SortCommand("n");
        String expectedMessage = SortCommand.MESSAGE_SORT_EMPLOYEE_SUCCESS;
        expectedModel.sortEmployee("n");
        assertCommandSuccess(testSortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validArgsS_success() {
        SortCommand testSortCommand = new SortCommand("s");
        String expectedMessage = SortCommand.MESSAGE_SORT_EMPLOYEE_SUCCESS;
        expectedModel.sortEmployee("s");
        assertCommandSuccess(testSortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validArgsJ_success() {
        SortCommand testSortCommand = new SortCommand("j");
        String expectedMessage = SortCommand.MESSAGE_SORT_EMPLOYEE_SUCCESS;
        expectedModel.sortEmployee("j");
        assertCommandSuccess(testSortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validArgsB_success() {
        SortCommand testSortCommand = new SortCommand("b");
        String expectedMessage = SortCommand.MESSAGE_SORT_EMPLOYEE_SUCCESS;
        expectedModel.sortEmployee("b");
        assertCommandSuccess(testSortCommand, model, expectedMessage, expectedModel);
    }

}
