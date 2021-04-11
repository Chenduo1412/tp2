package employeetracker.logic.commands;

import static employeetracker.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static employeetracker.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static employeetracker.logic.parser.CliSyntax.PREFIX_DATE_OF_JOINING;
import static employeetracker.logic.parser.CliSyntax.PREFIX_EMAIL;
import static employeetracker.logic.parser.CliSyntax.PREFIX_NAME;
import static employeetracker.logic.parser.CliSyntax.PREFIX_PHONE;
import static employeetracker.logic.parser.CliSyntax.PREFIX_ROLE;
import static employeetracker.logic.parser.CliSyntax.PREFIX_SALARY;
import static employeetracker.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import employeetracker.logic.commands.exceptions.CommandException;
import employeetracker.model.Model;
import employeetracker.model.employee.Employee;

/**
 * Adds a employee to the Employee Tracker.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an employee to Employee Tracker. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_DATE_OF_BIRTH + "DATE_OF_BIRTH "
            + PREFIX_DATE_OF_JOINING + "DATE_OF_JOINING "
            + PREFIX_SALARY + "SALARY "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_ROLE + "Developer "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_DATE_OF_BIRTH + "1990-12-25 "
            + PREFIX_DATE_OF_JOINING + "2020-07-01 "
            + PREFIX_SALARY + "3000 "
            + PREFIX_TAG + "Founder "
            + PREFIX_TAG + "l33tCoder";

    public static final String MESSAGE_SUCCESS = "New employee added: %1$s";
    public static final String MESSAGE_DUPLICATE_EMPLOYEE = "This employee already exists in Employee Tracker";

    private final Employee toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Employee}
     */
    public AddCommand(Employee employee) {
        requireNonNull(employee);
        toAdd = employee;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEmployee(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EMPLOYEE);
        }

        model.addEmployee(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
