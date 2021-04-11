package employeetracker.logic.commands;

import static java.util.Objects.requireNonNull;

import employeetracker.model.Model;


/**
 * List all stats for all employees in the Employee Tracker to the user.
*/
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    private final String statsField;

    public StatsCommand(String field) {
        this.statsField = field;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String statement = model.getStatement();
        return new CommandResult(statement);
    }


}
