package employeetracker.model.employee;

import static employeetracker.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import employeetracker.model.tag.Tag;

/**
 * Represents an Employee in the Employee Tracker.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Employee {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final DateOfBirth dateOfBirth;
    private final DateOfJoining dateOfJoining;

    // Data fields
    private final Address address;
    private final Role role;
    private final Salary salary;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Employee(Name name, Role role, Phone phone, Email email, Address address, DateOfBirth dateOfBirth,
                    DateOfJoining dateOfJoining, Salary salary, Set<Tag> tags) {
        requireAllNonNull(name, role, phone, email, address, dateOfBirth, dateOfJoining, salary, tags);
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.dateOfJoining = dateOfJoining;
        this.salary = salary;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public DateOfJoining getDateOfJoining() {
        return dateOfJoining;
    }

    public Salary getSalary() {
        return salary;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both employees have the same name.
     * This defines a weaker notion of equality between two employees.
     */
    public boolean isSameEmployee(Employee otherEmployee) {
        if (otherEmployee == this) {
            return true;
        }

        if (otherEmployee == null) {
            return false;
        }

        boolean isSameName = otherEmployee.getName().equals(getName());
        boolean isSameDateOfBirth = otherEmployee.getDateOfBirth().equals(getDateOfBirth());
        return isSameName && isSameDateOfBirth;
    }

    /**
     * Returns true if both employees have the same identity and data fields.
     * This defines a stronger notion of equality between two employees.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Employee)) {
            return false;
        }

        Employee otherEmployee = (Employee) other;
        return otherEmployee.getName().equals(getName())
                && otherEmployee.getRole().equals(getRole())
                && otherEmployee.getPhone().equals(getPhone())
                && otherEmployee.getEmail().equals(getEmail())
                && otherEmployee.getAddress().equals(getAddress())
                && otherEmployee.getDateOfBirth().equals(getDateOfBirth())
                && otherEmployee.getDateOfJoining().equals(getDateOfJoining())
                && otherEmployee.getSalary().equals(getSalary())
                && otherEmployee.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, role, phone, email, address, dateOfBirth, dateOfJoining, salary, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Role: ")
                .append(getRole())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress())
                .append("; Date of Birth: ")
                .append(getDateOfBirth())
                .append("; Date of Joining: ")
                .append(getDateOfJoining())
                .append("; Salary: ")
                .append(getSalary());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
