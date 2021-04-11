package employeetracker.model.employee;

import static employeetracker.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SalaryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Salary(null));
    }

    @Test
    public void constructor_invalidSalary_throwsIllegalArgumentException() {
        String invalidSalary = "";
        assertThrows(IllegalArgumentException.class, () -> new Salary(invalidSalary));
    }

    @Test
    public void isValidSalary() {
        // null salary number
        assertThrows(NullPointerException.class, () -> Salary.isValidSalary(null));

        // invalid salary numbers
        assertFalse(Salary.isValidSalary("")); // empty string
        assertFalse(Salary.isValidSalary(" ")); // spaces only
        assertFalse(Salary.isValidSalary("Salary")); // non-numeric
        assertFalse(Salary.isValidSalary("-1000")); // negative number
        assertFalse(Salary.isValidSalary("1000.00")); // decimal number
        assertFalse(Salary.isValidSalary("$6000")); // salary number with dollar sign

        // valid salary numbers
        assertTrue(Salary.isValidSalary("0"));
        assertTrue(Salary.isValidSalary("9000"));
        assertTrue(Salary.isValidSalary("124293842033123")); // long salary number
    }
}
