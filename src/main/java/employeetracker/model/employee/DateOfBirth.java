package employeetracker.model.employee;

import static employeetracker.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Employee's date of birth in the Employee Tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfBirth(String)}
 */
public class DateOfBirth {

    public static final String MESSAGE_CONSTRAINTS = "Date of birth should be in yyyy-mm-dd format";
    public static final String DATE_CONSTRAINT = "Date of birth should not be in the future";
    public static final String VALIDATION_REGEX = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
    public final String value;

    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param dateOfBirth A valid date of birth.
     */
    public DateOfBirth(String dateOfBirth) {
        requireNonNull(dateOfBirth);
        checkArgument(isValidDateOfBirth(dateOfBirth), MESSAGE_CONSTRAINTS);
        checkArgument(!isFutureDate(dateOfBirth), DATE_CONSTRAINT);
        value = dateOfBirth;
    }

    /**
     * Returns true if a given string is a valid date of birth.
     */
    public static boolean isValidDateOfBirth(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a future date.
     */
    public static boolean isFutureDate(String test) {
        assert(test != null);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentDate = dtf.format(LocalDate.now());
        return test.compareTo(currentDate) > 0 ? true : false;
    }

    @Override
    public String toString() {
        assert(value != null);
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateOfBirth // instanceof handles nulls
                && value.equals(((DateOfBirth) other).value)); // state check
    }

    @Override
    public int hashCode() {
        assert(value != null);
        return value.hashCode();
    }

}
