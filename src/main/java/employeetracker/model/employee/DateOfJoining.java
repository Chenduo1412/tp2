package employeetracker.model.employee;

import static employeetracker.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents an Employee's date of joining in the Employee Tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfJoining(String)}
 */
public class DateOfJoining {

    public static final String MESSAGE_CONSTRAINTS = "Date of joining should be in yyyy-mm-dd format";
    public static final String VALIDATION_REGEX = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
    public final String value;

    /**
     * Constructs a {@code DateOfJoining}.
     *
     * @param dateOfJoining A valid date of joining.
     */
    public DateOfJoining(String dateOfJoining) {
        requireNonNull(dateOfJoining);
        checkArgument(isValidDateOfJoining(dateOfJoining), MESSAGE_CONSTRAINTS);
        value = dateOfJoining;
    }

    /**
     * Returns true if a given string is a valid date of joining.
     */
    public static boolean isValidDateOfJoining(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        assert(value != null);
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateOfJoining // instanceof handles nulls
                && value.equals(((DateOfJoining) other).value)); // state check
    }

    @Override
    public int hashCode() {
        assert(value != null);
        return value.hashCode();
    }

}
