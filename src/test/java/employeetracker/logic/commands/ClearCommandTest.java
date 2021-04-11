package employeetracker.logic.commands;

import static employeetracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static employeetracker.testutil.TypicalEmployees.getTypicalEmployeeTracker;

import org.junit.jupiter.api.Test;

import employeetracker.model.EmployeeTracker;
import employeetracker.model.Model;
import employeetracker.model.ModelManager;
import employeetracker.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyEmployeeTracker_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyEmployeeTracker_success() {
        Model model = new ModelManager(getTypicalEmployeeTracker(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalEmployeeTracker(), new UserPrefs());
        expectedModel.setEmployeeTracker(new EmployeeTracker());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
