package employeetracker.storage;

import static employeetracker.testutil.TypicalEmployees.getTypicalEmployeeTracker;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import employeetracker.commons.core.GuiSettings;
import employeetracker.model.EmployeeTracker;
import employeetracker.model.ReadOnlyEmployeeTracker;
import employeetracker.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonEmployeeTrackerStorage employeeTrackerStorage = new JsonEmployeeTrackerStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(employeeTrackerStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void employeeTrackerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonEmployeeTrackerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonEmployeeTrackerStorageTest} class.
         */
        EmployeeTracker original = getTypicalEmployeeTracker();
        storageManager.saveEmployeeTracker(original);
        ReadOnlyEmployeeTracker retrieved = storageManager.readEmployeeTracker().get();
        assertEquals(original, new EmployeeTracker(retrieved));
    }

    @Test
    public void getEmployeeTrackerFilePath() {
        assertNotNull(storageManager.getEmployeeTrackerFilePath());
    }

}
