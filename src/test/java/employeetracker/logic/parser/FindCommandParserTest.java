package employeetracker.logic.parser;

import static employeetracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static employeetracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static employeetracker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import employeetracker.logic.commands.FindCommand;
import employeetracker.model.employee.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), "n/"));

        assertParseSuccess(parser, "find n/ Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find n/  Alice      Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsRole_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Developer", "Manager"), "r/"));

        assertParseSuccess(parser, "find r/ Developer Manager", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find r/  Developer      Manager  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsAddress_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Block", "Street"), "a/"));

        assertParseSuccess(parser, "find a/ Block Street", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find a/  Block       Street   \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsBirthday_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("1990-01-01"), "b/"));

        assertParseSuccess(parser, "find b/ 1990-01-01", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find b/          1990-01-01   \t", expectedFindCommand);
    }


    @Test
    public void parse_validArgsPhone_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("123456789"), "p/"));

        assertParseSuccess(parser, "find p/ 123456789 ", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find p/ 123456789            \t", expectedFindCommand);
    }
}
