package employeetracker.testutil;

import static employeetracker.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static employeetracker.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_AMY;
import static employeetracker.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_DATE_OF_JOINING_AMY;
import static employeetracker.logic.commands.CommandTestUtil.VALID_DATE_OF_JOINING_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static employeetracker.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static employeetracker.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static employeetracker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static employeetracker.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static employeetracker.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static employeetracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import employeetracker.model.EmployeeTracker;
import employeetracker.model.employee.Employee;

/**
 * A utility class containing a list of {@code Employee} objects to be used in tests.
 */
public class TypicalEmployees {

    public static final Employee ALICE = new EmployeeBuilder().withName("Alice Pauline").withRole("Finance Manager")
            .withPhone("94351253").withEmail("alice@example.com").withAddress("123, Jurong West Ave 6, #08-111")
            .withDateOfBirth("1988-09-14").withDateOfJoining("2020-02-01").withSalary("6000")
            .withTags("friends").build();
    public static final Employee BENSON = new EmployeeBuilder().withName("Benson Meier").withRole("Project Manager")
            .withPhone("98765432").withEmail("johnd@example.com").withAddress("311, Clementi Ave 2, #02-25")
            .withDateOfBirth("1989-11-14").withDateOfJoining("2020-02-01").withSalary("6000")
            .withTags("owesMoney", "friends").build();
    public static final Employee CARL = new EmployeeBuilder().withName("Carl Kurz").withRole("System Analyst")
            .withPhone("95352563").withEmail("heinz@example.com").withAddress("wall street").withSalary("6000")
            .withDateOfBirth("1979-09-01").withDateOfJoining("2020-02-01").build();
    public static final Employee DANIEL = new EmployeeBuilder().withName("Daniel Meier").withRole("Account Manager")
            .withPhone("87652533").withEmail("cornelia@example.com").withAddress("10th street").withSalary("6000")
            .withDateOfBirth("1977-01-05").withDateOfJoining("2021-02-01").withTags("friends").build();
    public static final Employee ELLE = new EmployeeBuilder().withName("Elle Meyer").withRole("Developer")
            .withPhone("9482224").withEmail("werner@example.com").withAddress("michegan ave").withSalary("5000")
            .withDateOfBirth("2001-04-11").withDateOfJoining("2021-02-01").build();
    public static final Employee FIONA = new EmployeeBuilder().withName("Fiona Kunz").withRole("Developer")
            .withPhone("9482427").withEmail("lydia@example.com").withAddress("little tokyo").withSalary("5000")
            .withDateOfBirth("2000-08-29").withDateOfJoining("2021-02-01").build();
    public static final Employee GEORGE = new EmployeeBuilder().withName("George Best").withRole("Developer")
            .withPhone("9482442").withEmail("anna@example.com").withAddress("4th street").withSalary("5000")
            .withDateOfBirth("1981-03-16").withDateOfJoining("2020-03-01").build();

    // Manually added
    public static final Employee HOON = new EmployeeBuilder().withName("Hoon Meier").withRole("Developer")
            .withPhone("8482424").withEmail("stefan@example.com").withAddress("little india").withSalary("6000")
            .withDateOfBirth("2002-01-01").withDateOfJoining("2020-02-01").build();
    public static final Employee IDA = new EmployeeBuilder().withName("Ida Mueller").withRole("Project Manager")
            .withPhone("8482131").withEmail("hans@example.com").withAddress("chicago ave").withSalary("6000")
            .withDateOfBirth("1974-03-09").withDateOfJoining("2020-02-01").build();

    // Manually added - Employee's details found in {@code CommandTestUtil}
    public static final Employee AMY = new EmployeeBuilder().withName(VALID_NAME_AMY).withRole(VALID_ROLE_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withDateOfBirth(VALID_DATE_OF_BIRTH_AMY).withDateOfJoining(VALID_DATE_OF_JOINING_AMY)
            .withSalary("6000").withTags(VALID_TAG_FRIEND).build();
    public static final Employee BOB = new EmployeeBuilder().withName(VALID_NAME_BOB).withRole(VALID_ROLE_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withDateOfBirth(VALID_DATE_OF_BIRTH_BOB).withDateOfJoining(VALID_DATE_OF_JOINING_BOB)
            .withSalary("6500").withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalEmployees() {} // prevents instantiation

    /**
     * Returns an {@code EmployeeTracker} with all the typical employees.
     */
    public static EmployeeTracker getTypicalEmployeeTracker() {
        EmployeeTracker ab = new EmployeeTracker();
        for (Employee employee : getTypicalEmployees()) {
            ab.addEmployee(employee);
        }
        return ab;
    }

    public static List<Employee> getTypicalEmployees() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
