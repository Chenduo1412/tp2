package employeetracker.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import employeetracker.model.EmployeeTracker;
import employeetracker.model.ReadOnlyEmployeeTracker;
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

/**
 * Contains utility methods for populating {@code EmployeeTracker} with sample data.
 */
public class SampleDataUtil {
    public static Employee[] getSampleEmployees() {
        return new Employee[] {
            new Employee(new Name("Alex Yeoh"), new Role("Senior Developer"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new DateOfBirth("1990-12-10"), new DateOfJoining("2019-05-01"), new Salary("8000"),
                    getTagSet("CoFounder")),

            new Employee(new Name("June Chong"), new Role("Marketing Manager"), new Phone("92811738"),
                    new Email("junechong@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new DateOfBirth("1991-03-02"), new DateOfJoining("2019-05-01"), new Salary("8000"),
                    getTagSet("CoFounder", "HalfBakedHrMgr")),

            new Employee(new Name("Bernice Yu"), new Role("Developer"), new Phone("92272758"),
                    new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new DateOfBirth("1988-03-10"), new DateOfJoining("2020-01-02"), new Salary("5000"),
                    getTagSet("Mentor", "l33tCoder")),

            new Employee(new Name("Charlotte Oliveiro"), new Role("System Analyst"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new DateOfBirth("1991-11-01"), new DateOfJoining("2020-01-15"), new Salary("6000"),
                    getTagSet("ComputerWizard")),

            new Employee(new Name("David Li"), new Role("Finance Manager"), new Phone("91031282"),
                    new Email("davidli@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new DateOfBirth("1992-05-28"), new DateOfJoining("2020-02-01"), new Salary("6000"),
                    getTagSet("AmateurCoder")),

            new Employee(new Name("Irfan Ibrahim"), new Role("Account Manager"), new Phone("92492021"),
                    new Email("irfanbrahim@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new DateOfBirth("1984-08-20"), new DateOfJoining("2020-02-01"), new Salary("6000"),
                    getTagSet("BusiestEmployee")),

            new Employee(new Name("Roy Balakrishnan"), new Role("Project Manager"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new DateOfBirth("1979-01-14"), new DateOfJoining("2020-03-01"), new Salary("7000"),
                    getTagSet("ScrumMaster")),

            new Employee(new Name("Ferry Harsanto"), new Role("System Engineer"), new Phone("97161923"),
                    new Email("tanmengli@example.com"), new Address("Blk 738 Yishun Street 72, #08-137"),
                    new DateOfBirth("1989-08-25"), new DateOfJoining("2020-03-15"), new Salary("6000"),
                    getTagSet("HardwareGuy")),

            new Employee(new Name("Yuki Hirose"), new Role("Business Analyst"), new Phone("87261283"),
                    new Email("yuki@example.com"), new Address("Blk 85 Tanglin Halt, #25-370"),
                    new DateOfBirth("1991-04-11"), new DateOfJoining("2020-07-01"), new Salary("5500"),
                    getTagSet("IronLady")),

            new Employee(new Name("Le Yao"), new Role("Receptionist"), new Phone("81674919"),
                    new Email("leyao@example.com"), new Address("Blk 358 Admiralty Drive, #07-181"),
                    new DateOfBirth("2000-03-10"), new DateOfJoining("2020-07-01"), new Salary("3000"),
                    getTagSet("MsSunshine")),

            new Employee(new Name("Chen Wei"), new Role("Penetration Tester"), new Phone("87159172"),
                    new Email("chenwei@example.com"), new Address("Blk 589D Montreal Drive, #02-501"),
                    new DateOfBirth("1996-01-27"), new DateOfJoining("2020-07-01"), new Salary("5000"),
                    getTagSet("Hax0r")),

            new Employee(new Name("Tan Meng Li"), new Role("Finance Assistant"), new Phone("91817282"),
                    new Email("tanmengli@example.com"), new Address("Blk 730 Woodlands Ring, #11-103"),
                    new DateOfBirth("1995-03-17"), new DateOfJoining("2021-02-01"), new Salary("3000"),
                    getTagSet("NewestEmployee"))
        };
    }

    public static ReadOnlyEmployeeTracker getSampleEmployeeTracker() {
        EmployeeTracker sampleAb = new EmployeeTracker();
        for (Employee sampleEmployee : getSampleEmployees()) {
            sampleAb.addEmployee(sampleEmployee);
        }
        return sampleAb;
    }
    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
