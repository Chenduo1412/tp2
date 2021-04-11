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
import static employeetracker.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static employeetracker.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.ROLE_DESC_BOB;
import static employeetracker.logic.commands.CommandTestUtil.SALARY_DESC_AMY;
import static employeetracker.logic.commands.CommandTestUtil.SALARY_DESC_BOB;
import static employeetracker.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static employeetracker.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static employeetracker.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static employeetracker.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_AMY;
import static employeetracker.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_DATE_OF_JOINING_AMY;
import static employeetracker.logic.commands.CommandTestUtil.VALID_DATE_OF_JOINING_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static employeetracker.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static employeetracker.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static employeetracker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static employeetracker.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_SALARY_AMY;
import static employeetracker.logic.commands.CommandTestUtil.VALID_SALARY_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static employeetracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static employeetracker.logic.parser.CliSyntax.PREFIX_TAG;
import static employeetracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static employeetracker.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static employeetracker.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static employeetracker.testutil.TypicalIndexes.INDEX_SECOND_EMPLOYEE;
import static employeetracker.testutil.TypicalIndexes.INDEX_THIRD_EMPLOYEE;

import org.junit.jupiter.api.Test;

import employeetracker.commons.core.index.Index;
import employeetracker.logic.commands.EditCommand;
import employeetracker.logic.commands.EditCommand.EditEmployeeDescriptor;
import employeetracker.model.employee.Address;
import employeetracker.model.employee.DateOfBirth;
import employeetracker.model.employee.DateOfJoining;
import employeetracker.model.employee.Email;
import employeetracker.model.employee.Name;
import employeetracker.model.employee.Phone;
import employeetracker.model.employee.Role;
import employeetracker.model.employee.Salary;
import employeetracker.model.tag.Tag;
import employeetracker.testutil.EditEmployeeDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS); // invalid role
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        // invalid date of birth
        assertParseFailure(parser, "1" + INVALID_DATE_OF_BIRTH_DESC, DateOfBirth.MESSAGE_CONSTRAINTS);
        // invalid date of joining
        assertParseFailure(parser, "1" + INVALID_DATE_OF_JOINING_DESC, DateOfJoining.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_SALARY_DESC, Salary.MESSAGE_CONSTRAINTS); // invalid salary
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Employee} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EMPLOYEE;
        String userInput = targetIndex.getOneBased() + ROLE_DESC_BOB + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DATE_OF_BIRTH_DESC_BOB + DATE_OF_JOINING_DESC_BOB
                + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditCommand.EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder().withName(VALID_NAME_AMY)
                .withRole(VALID_ROLE_BOB).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withDateOfBirth(VALID_DATE_OF_BIRTH_BOB)
                .withDateOfJoining(VALID_DATE_OF_JOINING_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_EMPLOYEE;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditCommand.EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_EMPLOYEE;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditCommand.EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder()
                .withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // role
        userInput = targetIndex.getOneBased() + ROLE_DESC_AMY;
        descriptor = new EditEmployeeDescriptorBuilder().withRole(VALID_ROLE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditEmployeeDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditEmployeeDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditEmployeeDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date of birth
        userInput = targetIndex.getOneBased() + DATE_OF_BIRTH_DESC_AMY;
        descriptor = new EditEmployeeDescriptorBuilder().withDateOfBirth(VALID_DATE_OF_BIRTH_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date of joining
        userInput = targetIndex.getOneBased() + DATE_OF_JOINING_DESC_AMY;
        descriptor = new EditEmployeeDescriptorBuilder().withDateOfJoining(VALID_DATE_OF_JOINING_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // salary
        userInput = targetIndex.getOneBased() + SALARY_DESC_AMY;
        descriptor = new EditEmployeeDescriptorBuilder().withSalary(VALID_SALARY_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditEmployeeDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_EMPLOYEE;
        String userInput = targetIndex.getOneBased() + ROLE_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY
                + EMAIL_DESC_AMY + DATE_OF_JOINING_DESC_AMY + DATE_OF_JOINING_DESC_AMY + SALARY_DESC_AMY
                + TAG_DESC_FRIEND + ROLE_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + DATE_OF_JOINING_DESC_AMY + DATE_OF_JOINING_DESC_AMY + SALARY_DESC_AMY + TAG_DESC_FRIEND
                + ROLE_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
                + DATE_OF_JOINING_DESC_BOB + SALARY_DESC_BOB + TAG_DESC_HUSBAND;

        EditCommand.EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withRole(VALID_ROLE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withDateOfBirth(VALID_DATE_OF_BIRTH_BOB).withDateOfJoining(VALID_DATE_OF_JOINING_BOB)
                .withSalary(VALID_SALARY_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_EMPLOYEE;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + PHONE_DESC_BOB;
        descriptor = new EditEmployeeDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_EMPLOYEE;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
