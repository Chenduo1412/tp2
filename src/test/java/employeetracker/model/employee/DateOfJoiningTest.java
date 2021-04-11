package employeetracker.model.employee;

import static employeetracker.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DateOfJoiningTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateOfJoining(null));
    }

    @Test
    public void constructor_invalidDateOfJoining_throwsIllegalArgumentException() {
        String invalidDateOfJoining = "";
        assertThrows(IllegalArgumentException.class, () -> new DateOfJoining(invalidDateOfJoining));
    }

    @Test
    public void isValidDateOfJoining() {
        // null date of joining
        assertThrows(NullPointerException.class, () -> DateOfJoining.isValidDateOfJoining(null));

        // invalid date of joining
        assertFalse(DateOfJoining.isValidDateOfJoining("")); // empty string
        assertFalse(DateOfJoining.isValidDateOfJoining(" ")); // spaces only
        assertFalse(DateOfJoining.isValidDateOfJoining("2020/12/10")); // date of joining in yyyy/mm/dd format
        assertFalse(DateOfJoining.isValidDateOfJoining("12-10-2020")); // date of joining in dd-mm-yyyy format
        assertFalse(DateOfJoining.isValidDateOfJoining("20201210")); // date of joining in yyyymmdd format
        assertFalse(DateOfJoining.isValidDateOfJoining("202-12-32")); // date of joining with invalid year
        assertFalse(DateOfJoining.isValidDateOfJoining("2020-13-10")); // date of joining with invalid month
        assertFalse(DateOfJoining.isValidDateOfJoining("2020-12-32")); // date of joining with invalid day

        // valid date of joining
        assertTrue(DateOfJoining.isValidDateOfJoining("2020-12-10"));
    }
}
