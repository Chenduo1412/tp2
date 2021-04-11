package employeetracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import employeetracker.commons.exceptions.DataConversionException;
import employeetracker.model.ReadOnlyEmployeeTracker;
import employeetracker.model.ReadOnlyUserPrefs;
import employeetracker.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends EmployeeTrackerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getEmployeeTrackerFilePath();

    @Override
    Optional<ReadOnlyEmployeeTracker> readEmployeeTracker() throws DataConversionException, IOException;

    @Override
    void saveEmployeeTracker(ReadOnlyEmployeeTracker employeeTracker) throws IOException;

}
