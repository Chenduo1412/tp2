package employeetracker.storage;

import static employeetracker.testutil.Assert.assertThrows;
import static employeetracker.testutil.TypicalEmployees.ALICE;
import static employeetracker.testutil.TypicalEmployees.HOON;
import static employeetracker.testutil.TypicalEmployees.IDA;
import static employeetracker.testutil.TypicalEmployees.getTypicalEmployeeTracker;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import employeetracker.commons.exceptions.DataConversionException;
import employeetracker.model.EmployeeTracker;
import employeetracker.model.ReadOnlyEmployeeTracker;

public class JsonEmployeeTrackerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonEmployeeTrackerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readEmployeeTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readEmployeeTracker(null));
    }

    private java.util.Optional<ReadOnlyEmployeeTracker> readEmployeeTracker(String filePath) throws Exception {
        return new JsonEmployeeTrackerStorage(Paths.get(filePath))
                .readEmployeeTracker(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEmployeeTracker("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readEmployeeTracker("notJsonFormat.json"));
    }

    @Test
    public void readEmployeeTracker_invalidEmployee_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readEmployeeTracker("invalidEmployee.json"));
    }

    @Test
    public void readEmployeeTracker_invalidAndValidEmployee_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readEmployeeTracker("invalidAndValidEmployee.json"));
    }

    @Test
    public void readAndSaveEmployeeTracker_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempEmployeeTracker.json");
        EmployeeTracker original = getTypicalEmployeeTracker();
        JsonEmployeeTrackerStorage jsonEmployeeTrackerStorage = new JsonEmployeeTrackerStorage(filePath);

        // Save in new file and read back
        jsonEmployeeTrackerStorage.saveEmployeeTracker(original, filePath);
        ReadOnlyEmployeeTracker readBack = jsonEmployeeTrackerStorage.readEmployeeTracker(filePath).get();
        assertEquals(original, new EmployeeTracker(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addEmployee(HOON);
        original.removeEmployee(ALICE);
        jsonEmployeeTrackerStorage.saveEmployeeTracker(original, filePath);
        readBack = jsonEmployeeTrackerStorage.readEmployeeTracker(filePath).get();
        assertEquals(original, new EmployeeTracker(readBack));

        // Save and read without specifying file path
        original.addEmployee(IDA);
        jsonEmployeeTrackerStorage.saveEmployeeTracker(original); // file path not specified
        readBack = jsonEmployeeTrackerStorage.readEmployeeTracker().get(); // file path not specified
        assertEquals(original, new EmployeeTracker(readBack));

    }

    @Test
    public void saveEmployeeTracker_nullEmployeeTracker_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEmployeeTracker(null, "SomeFile.json"));
    }

    /**
     * Saves {@code EmployeeTracker} at the specified {@code filePath}.
     */
    private void saveEmployeeTracker(ReadOnlyEmployeeTracker employeeTracker, String filePath) {
        try {
            new JsonEmployeeTrackerStorage(Paths.get(filePath))
                    .saveEmployeeTracker(employeeTracker, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEmployeeTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEmployeeTracker(new EmployeeTracker(), null));
    }
}
