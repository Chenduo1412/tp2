package employeetracker.logic.parser;

import static employeetracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import employeetracker.logic.commands.StatsCommand;
import employeetracker.logic.parser.exceptions.ParseException;


public class StatsCommandParser implements Parser<StatsCommand> {

    public static final String COMMAND_WORD = "stats";

    /**
     * Parses the given {@code String} of arguments in the context of the StatsCommand
     * and returns an StatsCommand object for execution.
     */
    public StatsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.equals(COMMAND_WORD)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Invalid stats command"));
        }
        return new StatsCommand(trimmedArgs);
    }
}
