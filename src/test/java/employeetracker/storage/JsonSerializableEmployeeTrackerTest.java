package employeetracker.storage;

import static employeetracker.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import employeetracker.commons.exceptions.IllegalValueException;
import employeetracker.commons.util.JsonUtil;
import employeetracker.model.EmployeeTracker;
import employeetracker.testutil.TypicalEmployees;

public class JsonSerializableEmployeeTrackerTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableEmployeeTrackerTest");
    private static final Path TYPICAL_EMPLOYEES_FILE = TEST_DATA_FOLDER.resolve("typicalEmployees.json");
    private static final Path INVALID_EMPLOYEE_FILE = TEST_DATA_FOLDER.resolve("invalidEmployee.json");
    private static final Path DUPLICATE_EMPLOYEE_FILE = TEST_DATA_FOLDER.resolve("duplicateEmployee.json");

    @Test
    public void toModelType_typicalEmployeesFile_success() throws Exception {
        JsonSerializableEmployeeTracker dataFromFile = JsonUtil.readJsonFile(TYPICAL_EMPLOYEES_FILE,
                JsonSerializableEmployeeTracker.class).get();
        EmployeeTracker employeeTrackerFromFile = dataFromFile.toModelType();
        EmployeeTracker typicalEmployeesEmployeeTracker = TypicalEmployees.getTypicalEmployeeTracker();
        assertEquals(employeeTrackerFromFile, typicalEmployeesEmployeeTracker);
    }

    @Test
    public void toModelType_invalidEmployeeFile_throwsIllegalValueException() throws Exception {
        JsonSerializableEmployeeTracker dataFromFile = JsonUtil.readJsonFile(INVALID_EMPLOYEE_FILE,
                JsonSerializableEmployeeTracker.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateEmployees_throwsIllegalValueException() throws Exception {
        JsonSerializableEmployeeTracker dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EMPLOYEE_FILE,
                JsonSerializableEmployeeTracker.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableEmployeeTracker.MESSAGE_DUPLICATE_EMPLOYEE,
                dataFromFile::toModelType);
    }

}
