package employeetracker.storage;

import static employeetracker.storage.JsonAdaptedEmployee.MISSING_FIELD_MESSAGE_FORMAT;
import static employeetracker.testutil.Assert.assertThrows;
import static employeetracker.testutil.TypicalEmployees.BENSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import employeetracker.commons.exceptions.IllegalValueException;
import employeetracker.model.employee.Address;
import employeetracker.model.employee.DateOfBirth;
import employeetracker.model.employee.DateOfJoining;
import employeetracker.model.employee.Email;
import employeetracker.model.employee.Name;
import employeetracker.model.employee.Phone;
import employeetracker.model.employee.Role;
import employeetracker.model.employee.Salary;

public class JsonAdaptedEmployeeTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_ROLE = " ";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_DATE_OF_BIRTH = "30-12-1999";
    private static final String INVALID_DATE_OF_JOINING = "30-12-2019";
    private static final String INVALID_SALARY = "1000.00";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_ROLE = BENSON.getRole().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_DATE_OF_BIRTH = BENSON.getDateOfBirth().toString();
    private static final String VALID_DATE_OF_JOINING = BENSON.getDateOfJoining().toString();
    private static final String VALID_SALARY = BENSON.getSalary().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validEmployeeDetails_returnsEmployee() throws Exception {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(BENSON);
        assertEquals(BENSON, employee.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(INVALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE_OF_BIRTH, VALID_DATE_OF_JOINING, VALID_SALARY, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(null, VALID_ROLE, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE_OF_BIRTH, VALID_DATE_OF_JOINING, VALID_SALARY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_NAME, INVALID_ROLE, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE_OF_BIRTH, VALID_DATE_OF_JOINING, VALID_SALARY, VALID_TAGS);
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_NAME, null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE_OF_BIRTH, VALID_DATE_OF_JOINING, VALID_SALARY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_NAME, VALID_ROLE, INVALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE_OF_BIRTH, VALID_DATE_OF_JOINING, VALID_SALARY, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_NAME, VALID_ROLE, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE_OF_BIRTH, VALID_DATE_OF_JOINING, VALID_SALARY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_NAME, VALID_ROLE, VALID_PHONE, INVALID_EMAIL,
                VALID_ADDRESS, VALID_DATE_OF_BIRTH, VALID_DATE_OF_JOINING, VALID_SALARY, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_NAME, VALID_ROLE, VALID_PHONE, null,
                VALID_ADDRESS, VALID_DATE_OF_BIRTH, VALID_DATE_OF_JOINING, VALID_SALARY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL,
                INVALID_ADDRESS, VALID_DATE_OF_BIRTH, VALID_DATE_OF_JOINING, VALID_SALARY, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL,
                null, VALID_DATE_OF_BIRTH, VALID_DATE_OF_JOINING, VALID_SALARY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidDateOfBirth_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, INVALID_DATE_OF_BIRTH, VALID_DATE_OF_JOINING, VALID_SALARY, VALID_TAGS);
        String expectedMessage = DateOfBirth.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullDateOfBirth_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_DATE_OF_JOINING, VALID_SALARY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateOfBirth.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidDateOfJoining_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE_OF_BIRTH, INVALID_DATE_OF_JOINING, VALID_SALARY, VALID_TAGS);
        String expectedMessage = DateOfJoining.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullDateOfJoining_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE_OF_BIRTH, null, VALID_SALARY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateOfJoining.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidSalary_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE_OF_BIRTH, VALID_DATE_OF_JOINING, INVALID_SALARY, VALID_TAGS);
        String expectedMessage = Salary.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullSalary_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE_OF_BIRTH, VALID_DATE_OF_JOINING, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Salary.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_NAME, VALID_ROLE, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATE_OF_BIRTH, VALID_DATE_OF_JOINING, VALID_SALARY, invalidTags);
        assertThrows(IllegalValueException.class, employee::toModelType);
    }

}
