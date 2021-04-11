package employeetracker.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import employeetracker.commons.core.GuiSettings;
import employeetracker.commons.core.LogsCenter;
import employeetracker.logic.commands.Command;
import employeetracker.logic.commands.CommandResult;
import employeetracker.logic.commands.exceptions.CommandException;
import employeetracker.logic.parser.EmployeeTrackerParser;
import employeetracker.logic.parser.exceptions.ParseException;
import employeetracker.model.Model;
import employeetracker.model.ReadOnlyEmployeeTracker;
import employeetracker.model.employee.Employee;
import employeetracker.storage.Storage;
import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final EmployeeTrackerParser employeeTrackerParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        employeeTrackerParser = new EmployeeTrackerParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = employeeTrackerParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveEmployeeTracker(model.getEmployeeTracker());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyEmployeeTracker getEmployeeTracker() {
        return model.getEmployeeTracker();
    }

    @Override
    public ObservableList<Employee> getFilteredEmployeeList() {
        return model.getFilteredEmployeeList();
    }

    @Override
    public Path getEmployeeTrackerFilePath() {
        return model.getEmployeeTrackerFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
