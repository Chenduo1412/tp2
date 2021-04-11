package employeetracker.testutil;

import java.util.HashSet;
import java.util.Set;

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
import employeetracker.model.util.SampleDataUtil;

/**
 * A utility class to help with building Employee objects.
 */
public class EmployeeBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_ROLE = "Developer";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DATE_OF_BIRTH = "1990-10-12";
    public static final String DEFAULT_DATE_OF_JOINING = "2020-01-01";
    public static final String DEFAULT_SALARY = "6000";

    private Name name;
    private Role role;
    private Phone phone;
    private Email email;
    private Address address;
    private DateOfBirth dateOfBirth;
    private DateOfJoining dateOfJoining;
    private Salary salary;
    private Set<Tag> tags;

    /**
     * Creates a {@code EmployeeBuilder} with the default details.
     */
    public EmployeeBuilder() {
        name = new Name(DEFAULT_NAME);
        role = new Role(DEFAULT_ROLE);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        dateOfBirth = new DateOfBirth(DEFAULT_DATE_OF_BIRTH);
        dateOfJoining = new DateOfJoining(DEFAULT_DATE_OF_JOINING);
        salary = new Salary(DEFAULT_SALARY);
        tags = new HashSet<>();
    }

    /**
     * Initializes the EmployeeBuilder with the data of {@code employeeToCopy}.
     */
    public EmployeeBuilder(Employee employeeToCopy) {
        name = employeeToCopy.getName();
        role = employeeToCopy.getRole();
        phone = employeeToCopy.getPhone();
        email = employeeToCopy.getEmail();
        address = employeeToCopy.getAddress();
        dateOfBirth = employeeToCopy.getDateOfBirth();
        dateOfJoining = employeeToCopy.getDateOfJoining();
        salary = employeeToCopy.getSalary();
        tags = new HashSet<>(employeeToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Employee} that we are building.
     */
    public EmployeeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Employee} that we are building.
     */
    public EmployeeBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Employee} that we are building.
     */
    public EmployeeBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Employee} that we are building.
     */
    public EmployeeBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Employee} that we are building.
     */
    public EmployeeBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Employee} that we are building.
     */
    public EmployeeBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code DateOfBirth} of the {@code Employee} that we are building.
     */
    public EmployeeBuilder withDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = new DateOfBirth(dateOfBirth);
        return this;
    }

    /**
     * Sets the {@code DateOfJoining} of the {@code Employee} that we are building.
     */
    public EmployeeBuilder withDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = new DateOfJoining(dateOfJoining);
        return this;
    }

    /**
     * Sets the {@code Salary} of the {@code Employee} that we are building.
     */
    public EmployeeBuilder withSalary(String salary) {
        this.salary = new Salary(salary);
        return this;
    }

    public Employee build() {
        return new Employee(name, role, phone, email, address, dateOfBirth, dateOfJoining, salary, tags);
    }

}
