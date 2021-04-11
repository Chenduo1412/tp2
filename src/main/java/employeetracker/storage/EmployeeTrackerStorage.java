package employeetracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import employeetracker.commons.exceptions.DataConversionException;
import employeetracker.model.EmployeeTracker;
import employeetracker.model.ReadOnlyEmployeeTracker;

/**
 * Represents a storage for {@link EmployeeTracker}.
 */
public interface EmployeeTrackerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getEmployeeTrackerFilePath();

    /**
     * Returns EmployeeTracker data as a {@link ReadOnlyEmployeeTracker}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEmployeeTracker> readEmployeeTracker() throws DataConversionException, IOException;

    /**
     * @see #getEmployeeTrackerFilePath()
     */
    Optional<ReadOnlyEmployeeTracker> readEmployeeTracker(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEmployeeTracker} to the storage.
     * @param employeeTracker cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEmployeeTracker(ReadOnlyEmployeeTracker employeeTracker) throws IOException;

    /**
     * @see #saveEmployeeTracker(ReadOnlyEmployeeTracker)
     */
    void saveEmployeeTracker(ReadOnlyEmployeeTracker employeeTracker, Path filePath) throws IOException;

}
