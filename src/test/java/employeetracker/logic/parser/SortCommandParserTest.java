package employeetracker.logic.parser;

import static employeetracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static employeetracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static employeetracker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import employeetracker.logic.commands.SortCommand;

class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgsN_returnsSortCommand() {
        SortCommand sortCommand = new SortCommand("n");
        assertParseSuccess(parser, "n", sortCommand);
    }

    @Test
    public void parse_validArgsS_returnsSortCommand() {
        SortCommand sortCommand = new SortCommand("s");
        assertParseSuccess(parser, "s", sortCommand);
    }

    @Test
    public void parse_validArgsJ_returnsSortCommand() {
        SortCommand sortCommand = new SortCommand("j");
        assertParseSuccess(parser, "j", sortCommand);
    }

    @Test
    public void parse_validArgsB_returnsSortCommand() {
        SortCommand sortCommand = new SortCommand("b");
        assertParseSuccess(parser, "b", sortCommand);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
