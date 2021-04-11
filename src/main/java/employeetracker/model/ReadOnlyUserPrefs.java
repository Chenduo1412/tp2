package employeetracker.model;

import java.nio.file.Path;

import employeetracker.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getEmployeeTrackerFilePath();

}
