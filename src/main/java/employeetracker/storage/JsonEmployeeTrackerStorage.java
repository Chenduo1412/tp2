package employeetracker.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import employeetracker.commons.core.LogsCenter;
import employeetracker.commons.exceptions.DataConversionException;
import employeetracker.commons.exceptions.IllegalValueException;
import employeetracker.commons.util.FileUtil;
import employeetracker.commons.util.JsonUtil;
import employeetracker.model.ReadOnlyEmployeeTracker;

/**
 * A class to access EmployeeTracker data stored as a json file on the hard disk.
 */
public class JsonEmployeeTrackerStorage implements EmployeeTrackerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonEmployeeTrackerStorage.class);

    private Path filePath;

    public JsonEmployeeTrackerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEmployeeTrackerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEmployeeTracker> readEmployeeTracker() throws DataConversionException {
        return readEmployeeTracker(filePath);
    }

    /**
     * Similar to {@link #readEmployeeTracker()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEmployeeTracker> readEmployeeTracker(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableEmployeeTracker> jsonEmployeeTracker = JsonUtil.readJsonFile(
                filePath, JsonSerializableEmployeeTracker.class);
        if (!jsonEmployeeTracker.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEmployeeTracker.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveEmployeeTracker(ReadOnlyEmployeeTracker employeeTracker) throws IOException {
        saveEmployeeTracker(employeeTracker, filePath);
    }

    /**
     * Similar to {@link #saveEmployeeTracker(ReadOnlyEmployeeTracker)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveEmployeeTracker(ReadOnlyEmployeeTracker employeeTracker, Path filePath) throws IOException {
        requireNonNull(employeeTracker);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEmployeeTracker(employeeTracker), filePath);
    }

}
