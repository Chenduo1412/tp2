package employeetracker.model.employee;

import static employeetracker.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static employeetracker.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static employeetracker.logic.parser.CliSyntax.PREFIX_NAME;
import static employeetracker.logic.parser.CliSyntax.PREFIX_PHONE;
import static employeetracker.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.List;
import java.util.function.Predicate;

import employeetracker.commons.util.StringUtil;

/**
 * Tests that a {@code Employee}'s {@code Name, Role} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Employee> {
    private final List<String> keywords;
    private final String findBy;

    /**
     * Tests that NameContainsKeywordsPredicate matches any of the keywords given.
     */
    public NameContainsKeywordsPredicate(List<String> keywords, String findBy) {
        this.keywords = keywords;
        this.findBy = findBy;
    }

    @Override
    public boolean test(Employee employee) {


        if (findBy.equals(String.valueOf(PREFIX_NAME))) {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(employee.getName().fullName, keyword));

        }
        if (findBy.equals(String.valueOf(PREFIX_ROLE))) {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            String.valueOf(employee.getRole()), keyword));

        }
        if (findBy.equals(String.valueOf(PREFIX_ADDRESS))) {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            String.valueOf(employee.getAddress()), keyword));
        }
        if (findBy.equals(String.valueOf(PREFIX_DATE_OF_BIRTH))) {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            String.valueOf(employee.getDateOfBirth()), keyword));
        }
        if (findBy.equals(String.valueOf(PREFIX_PHONE))) {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsPartialWordIgnoreCase(
                             String.valueOf(employee.getPhone()), keyword));
        }




        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
