package employeetracker.logic;

import static employeetracker.commons.core.Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX;
import static employeetracker.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static employeetracker.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.DATE_OF_BIRTH_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.DATE_OF_JOINING_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.SALARY_DESC_AMY;
import static employeetracker.testutil.Assert.assertThrows;
import static employeetracker.testutil.TypicalEmployees.AMY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import employeetracker.logic.commands.AddCommand;
import employeetracker.logic.commands.CommandResult;
import employeetracker.logic.commands.ListCommand;
import employeetracker.logic.commands.exceptions.CommandException;
import employeetracker.logic.parser.exceptions.ParseException;
import employeetracker.model.Model;
import employeetracker.model.ModelManager;
import employeetracker.model.ReadOnlyEmployeeTracker;
import employeetracker.model.UserPrefs;
import employeetracker.model.employee.Employee;
import employeetracker.storage.JsonEmployeeTrackerStorage;
import employeetracker.storage.JsonUserPrefsStorage;
import employeetracker.storage.StorageManager;
import employeetracker.testutil.EmployeeBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonEmployeeTrackerStorage employeeTrackerStorage =
                new JsonEmployeeTrackerStorage(temporaryFolder.resolve("employeeTracker.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(employeeTrackerStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonEmployeeTrackerIoExceptionThrowingStub
        JsonEmployeeTrackerStorage employeeTrackerStorage =
                new JsonEmployeeTrackerIoExceptionThrowingStub(
                        temporaryFolder.resolve("ioExceptionEmployeeTracker.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(employeeTrackerStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + ROLE_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + DATE_OF_BIRTH_DESC_AMY + DATE_OF_JOINING_DESC_AMY + SALARY_DESC_AMY + ADDRESS_DESC_AMY;
        Employee expectedEmployee = new EmployeeBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addEmployee(expectedEmployee);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredEmployeeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredEmployeeList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getEmployeeTracker(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonEmployeeTrackerIoExceptionThrowingStub extends JsonEmployeeTrackerStorage {
        private JsonEmployeeTrackerIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveEmployeeTracker(ReadOnlyEmployeeTracker employeeTracker, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
