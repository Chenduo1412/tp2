package employeetracker.model.employee;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import employeetracker.testutil.EmployeeBuilder;

public class NameContainsKeywordsPredicateTest {

    private String findBy;

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(
                firstPredicateKeywordList, findBy);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(
                secondPredicateKeywordList, findBy);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(
                firstPredicateKeywordList, findBy);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different employee -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Collections.singletonList("Alice"), "n/");
        assertTrue(predicate.test(new EmployeeBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), "n/");
        assertTrue(predicate.test(new EmployeeBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"), "n/");
        assertTrue(predicate.test(new EmployeeBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"), "n/");
        assertTrue(predicate.test(new EmployeeBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList(), "n/");
        assertFalse(predicate.test(new EmployeeBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carol"), "n/");
        assertFalse(predicate.test(new EmployeeBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "Main", "Street"), "n/");
        assertFalse(predicate.test(new EmployeeBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_roleContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Collections.singletonList("Finance"), "r/");
        assertTrue(predicate.test(new EmployeeBuilder().withRole("Finance Manager").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Project", "Manager"), "r/");
        assertTrue(predicate.test(new EmployeeBuilder().withRole("Project Manager").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Manager", "Developer"), "r/");
        assertTrue(predicate.test(new EmployeeBuilder().withRole("Project Manager").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("finance", "mAnager"), "r/");
        assertTrue(predicate.test(new EmployeeBuilder().withRole("Finance Manager").build()));
    }

    @Test
    public void test_roleDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList(), "r/");
        assertFalse(predicate.test(new EmployeeBuilder().withRole("Manager").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Developer"), "r/");
        assertFalse(predicate.test(new EmployeeBuilder().withRole("Finance Manager").build()));

        // Keywords match phone, email and address, but does not match role
        predicate = new NameContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "Main", "Street"), "r/");
        assertFalse(predicate.test(new EmployeeBuilder().withRole("Manager").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Collections.singletonList("Woodlands"), "a/");
        assertTrue(predicate.test(new EmployeeBuilder().withAddress("Woodlands Street 41").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Woodlands", "School"), "a/");
        assertTrue(predicate.test(new EmployeeBuilder().withAddress("Woodlands High School").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Woodlands", "School"), "a/");
        assertTrue(predicate.test(new EmployeeBuilder().withAddress("High School").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("WOODLANDS", "schOOL"), "a/");
        assertTrue(predicate.test(new EmployeeBuilder().withAddress("Woodlands School").build()));
    }
    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList(), "a/");
        assertFalse(predicate.test(new EmployeeBuilder().withAddress("Woodlands").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Woodlands"), "a/");
        assertFalse(predicate.test(new EmployeeBuilder().withAddress("High School").build()));

        // Keywords match phone, email and role, but does not match address
        predicate = new NameContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "Manager", "HighSchool"), "a/");
        assertFalse(predicate.test(new EmployeeBuilder().withRole("Manager").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Street").build()));
    }
}
