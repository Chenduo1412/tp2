package employeetracker.logic.parser;

import static employeetracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static employeetracker.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static employeetracker.logic.commands.CommandTestUtil.DATE_OF_BIRTH_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.DATE_OF_BIRTH_DESC_BOB;
import static employeetracker.logic.commands.CommandTestUtil.DATE_OF_JOINING_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.DATE_OF_JOINING_DESC_BOB;
import static employeetracker.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static employeetracker.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static employeetracker.logic.commands.CommandTestUtil.INVALID_DATE_OF_BIRTH_DESC;
import static employeetracker.logic.commands.CommandTestUtil.INVALID_DATE_OF_JOINING_DESC;
import static employeetracker.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static employeetracker.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static employeetracker.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static employeetracker.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static employeetracker.logic.commands.CommandTestUtil.INVALID_SALARY_DESC;
import static employeetracker.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static employeetracker.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static employeetracker.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static employeetracker.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static employeetracker.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static employeetracker.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.ROLE_DESC_BOB;
import static employeetracker.logic.commands.CommandTestUtil.SALARY_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.SALARY_DESC_BOB;
import static employeetracker.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static employeetracker.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static employeetracker.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_DATE_OF_JOINING_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_SALARY_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static employeetracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static employeetracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static employeetracker.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static employeetracker.testutil.TypicalEmployees.AMY;
import static employeetracker.testutil.TypicalEmployees.BOB;

import org.junit.jupiter.api.Test;

import employeetracker.logic.commands.AddCommand;
import employeetracker.model.employee.Address;
import employeetracker.model.employee.DateOfBirth;
import employeetracker.model.employee.DateOfJoining;
import employeetracker.model.employee.Email;
import employeetracker.model.employee.Employee;
import employeetracker.model.employee.Name;
import employeetracker.model.employee.Phone;
import employeetracker.model.employee.Role;
import employeetracker.model.employee.Salary;
import employeetracker.model.tag.Tag;
import employeetracker.testutil.EmployeeBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Employee expectedEmployee = new EmployeeBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB
                + SALARY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedEmployee));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB
                + SALARY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedEmployee));

        // multiple roles - last role accepted
        assertParseSuccess(parser, NAME_DESC_BOB + ROLE_DESC_AMY + ROLE_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB
                + SALARY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedEmployee));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_AMY
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
                + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedEmployee));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB
                + SALARY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedEmployee));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
                + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedEmployee));

        // multiple dates of birth - last date of birth accepted
        assertParseSuccess(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_AMY + DATE_OF_BIRTH_DESC_BOB
                + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedEmployee));

        // multiple dates of joining - last date of joining accepted
        assertParseSuccess(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_AMY
                + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedEmployee));

        // multiple salaries - last salary accepted
        assertParseSuccess(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB
                + SALARY_DESC_AMY + SALARY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedEmployee));

        // multiple tags - all accepted
        Employee expectedEmployeeMultipleTags = new EmployeeBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB
                + SALARY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedEmployeeMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Employee expectedEmployee = new EmployeeBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + ROLE_DESC_BOB + ROLE_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DATE_OF_BIRTH_DESC_AMY + DATE_OF_JOINING_DESC_AMY
                + SALARY_DESC_AMY, new AddCommand(expectedEmployee));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB,
                expectedMessage);

        // missing role prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_ROLE_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB,
                expectedMessage);

        // missing date of birth prefix
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + VALID_DATE_OF_BIRTH_BOB + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB,
                expectedMessage);

        // missing date of joining prefix
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + VALID_DATE_OF_JOINING_BOB + SALARY_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VALID_ADDRESS_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB,
                expectedMessage);

        // missing salary prefix
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB + VALID_SALARY_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                + VALID_ADDRESS_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + ROLE_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_ROLE_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Role.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid date of birth
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_DATE_OF_BIRTH_DESC + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, DateOfBirth.MESSAGE_CONSTRAINTS);

        // invalid date of joining
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + INVALID_DATE_OF_JOINING_DESC + SALARY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, DateOfJoining.MESSAGE_CONSTRAINTS);

        // invalid salary
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB + INVALID_SALARY_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Salary.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + ROLE_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB
                + SALARY_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

}
