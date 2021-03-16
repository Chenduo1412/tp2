package employeetracker.logic.commands;

import static java.util.Objects.requireNonNull;

import employeetracker.commons.core.Messages;
import employeetracker.model.Model;
import employeetracker.model.employee.NameContainsKeywordsPredicate;

/**
 * Finds and lists all employees in Employee Tracker whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TYPE (must be n or r)/KEYWORD...\n"
            + "Example: " + COMMAND_WORD + " n/ Roy Irfan"
            + " OR "
            + COMMAND_WORD + " r/ Developer";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }





    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEmployeeList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EMPLOYEES_LISTED_OVERVIEW, model.getFilteredEmployeeList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
