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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds employee records that have any of "
            + "the given keywords (case-insensitive) in their name/role/address/date of birth/phone number.\n"
            + "Parameters: TYPE (must be n, r, a, b or p)/KEYWORD...\n"
            + "Example: " + COMMAND_WORD + " n/Roy Irfan"
            + " OR "
            + COMMAND_WORD + " r/Developer"
            + " OR "
            + COMMAND_WORD + " a/Serangoon"
            + " OR "
            + COMMAND_WORD + " b/1992-05-28"
            + " OR "
            + COMMAND_WORD + " p/910 (at least 3 digits long)";

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
