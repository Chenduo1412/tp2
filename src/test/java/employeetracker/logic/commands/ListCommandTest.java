package employeetracker.logic.commands;

import static employeetracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static employeetracker.logic.commands.CommandTestUtil.showEmployeeAtIndex;
import static employeetracker.testutil.TypicalEmployees.getTypicalEmployeeTracker;
import static employeetracker.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import employeetracker.model.Model;
import employeetracker.model.ModelManager;
import employeetracker.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEmployeeTracker(), new UserPrefs());
        expectedModel = new ModelManager(model.getEmployeeTracker(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showEmployeeAtIndex(model, INDEX_FIRST_EMPLOYEE);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
