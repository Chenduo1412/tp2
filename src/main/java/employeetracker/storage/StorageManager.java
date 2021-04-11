package employeetracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import employeetracker.commons.core.LogsCenter;
import employeetracker.commons.exceptions.DataConversionException;
import employeetracker.model.ReadOnlyEmployeeTracker;
import employeetracker.model.ReadOnlyUserPrefs;
import employeetracker.model.UserPrefs;

/**
 * Manages storage of EmployeeTracker data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private EmployeeTrackerStorage employeeTrackerStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code EmployeeTrackerStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(EmployeeTrackerStorage employeeTrackerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.employeeTrackerStorage = employeeTrackerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ EmployeeTracker methods ==============================

    @Override
    public Path getEmployeeTrackerFilePath() {
        return employeeTrackerStorage.getEmployeeTrackerFilePath();
    }

    @Override
    public Optional<ReadOnlyEmployeeTracker> readEmployeeTracker() throws DataConversionException, IOException {
        return readEmployeeTracker(employeeTrackerStorage.getEmployeeTrackerFilePath());
    }

    @Override
    public Optional<ReadOnlyEmployeeTracker> readEmployeeTracker(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return employeeTrackerStorage.readEmployeeTracker(filePath);
    }

    @Override
    public void saveEmployeeTracker(ReadOnlyEmployeeTracker employeeTracker) throws IOException {
        saveEmployeeTracker(employeeTracker, employeeTrackerStorage.getEmployeeTrackerFilePath());
    }

    @Override
    public void saveEmployeeTracker(ReadOnlyEmployeeTracker employeeTracker, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        employeeTrackerStorage.saveEmployeeTracker(employeeTracker, filePath);
    }

}
