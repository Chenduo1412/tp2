package employeetracker.model.employee;

import static employeetracker.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import employeetracker.model.employee.exceptions.DuplicateEmployeeException;
import employeetracker.model.employee.exceptions.EmployeeNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



/**
 * A list of employees that enforces uniqueness between its elements and does not allow nulls.
 * A employee is considered unique by comparing using {@code Employee#isSameEmployee(Employee)}. As such, adding and
 * updating of employees uses Employee#isSameEmployee(Employee) for equality so as to ensure that the employee being
 * added or updated is unique in terms of identity in the UniqueEmployeeList. However, the removal of a employee uses
 * Employee#equals(Object) so as to ensure that the employee with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Employee#isSameEmployee(Employee)
 */
public class UniqueEmployeeList implements Iterable<Employee> {

    private final ObservableList<Employee> internalList = FXCollections.observableArrayList();
    private final ObservableList<Employee> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent employee as the given argument.
     */
    public boolean contains(Employee toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEmployee);
    }

    /**
     * Adds a employee to the list.
     * The employee must not already exist in the list.
     */
    public void add(Employee toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEmployeeException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the employee {@code target} in the list with {@code editedEmployee}.
     * {@code target} must exist in the list.
     * The employee identity of {@code editedEmployee} must not be the same as another existing employee in the list.
     */
    public void setEmployee(Employee target, Employee editedEmployee) {
        requireAllNonNull(target, editedEmployee);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EmployeeNotFoundException();
        }

        if (!target.isSameEmployee(editedEmployee) && contains(editedEmployee)) {
            throw new DuplicateEmployeeException();
        }

        internalList.set(index, editedEmployee);
    }

    /**
     * Removes the equivalent employee from the list.
     * The employee must exist in the list.
     */
    public void remove(Employee toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EmployeeNotFoundException();
        }
    }

    public void setEmployees(UniqueEmployeeList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code employees}.
     * {@code employees} must not contain duplicate employees.
     */
    public void setEmployees(List<Employee> employees) {
        requireAllNonNull(employees);
        if (!employeesAreUnique(employees)) {
            throw new DuplicateEmployeeException();
        }

        internalList.setAll(employees);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Employee> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Employee> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEmployeeList // instanceof handles nulls
                        && internalList.equals(((UniqueEmployeeList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code employees} contains only unique employees.
     */
    private boolean employeesAreUnique(List<Employee> employees) {
        for (int i = 0; i < employees.size() - 1; i++) {
            for (int j = i + 1; j < employees.size(); j++) {
                if (employees.get(i).isSameEmployee(employees.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sort the employees list by field.
     * The employee must exist in the list.
     */
    public void sortEmployee(String field) {
        switch (field.toLowerCase()) {
        case "n":
            internalList.sort((s1, s2) -> s1.getName().toString().compareToIgnoreCase(s2.getName().toString()));
            break;
        case "s":
            internalList.sort(Comparator.comparingInt(s -> Integer.parseInt(s.getSalary().value)));
            break;
        case "j":
            internalList.sort((s1, s2) -> s1.getDateOfJoining().value.compareToIgnoreCase(s2.getDateOfJoining().value));
            break;
        case "b":
            internalList.sort((s1, s2) -> s1.getDateOfBirth().value.compareToIgnoreCase(s2.getDateOfBirth().value));
            break;
        default:
            assert false : "Sort field Parameters: TYPE (must be the letter n, s, j or b)";
        }
    }

    /**
     * Gets the number of employees in the list.
     */
    public int getNoOfEmployees() {
        return this.internalList.size();
    }

    /**
     * Gets the total Salary expenses for all employee in the list.
     */
    public double getTotalSalary() {
        double totalSalary = 0;
        for (int i = 0; i < internalList.size(); i++) {

            totalSalary += Double.parseDouble(internalList.get(i).getSalary().value);
        }
        return totalSalary;
    }

    /**
     * Gets the Highest Salary employee and employee's name in the list.
     */
    public String getHighestSalary() {
        double highestSalary = 0;
        String highestSalaryEmployee = "";
        String highestSalaryResult = "";
        for (int i = 0; i < internalList.size(); i++) {
            if (highestSalary < Double.parseDouble(internalList.get(i).getSalary().value)) {
                highestSalary = Double.parseDouble(internalList.get(i).getSalary().value);
                highestSalaryEmployee = internalList.get(i).getName().fullName;
            } else if (highestSalary == Double.parseDouble(internalList.get(i).getSalary().value)) {
                highestSalaryEmployee += ", " + internalList.get(i).getName().fullName;
            }
        }
        highestSalaryResult = String.format("%.2f", highestSalary) + " (" + highestSalaryEmployee + ")";
        return highestSalaryResult;
    }

    /**
     * Gets the lowest Salary employee and employee's name in the list.
     */
    public String getLowestSalary() {
        double lowestSalary = Double.parseDouble(internalList.get(0).getSalary().value);
        String lowestSalaryEmployee = internalList.get(0).getName().fullName;
        String lowestSalaryResult = "";
        for (int i = 1; i < internalList.size(); i++) {
            if (lowestSalary > Double.parseDouble(internalList.get(i).getSalary().value)) {
                lowestSalary = Double.parseDouble(internalList.get(i).getSalary().value);
                lowestSalaryEmployee = internalList.get(i).getName().fullName;
            } else if (lowestSalary == Double.parseDouble(internalList.get(i).getSalary().value)) {
                lowestSalaryEmployee += ", " + internalList.get(i).getName().fullName;
            }
        }
        lowestSalaryResult = String.format("%.2f", lowestSalary) + " (" + lowestSalaryEmployee + ")";
        return lowestSalaryResult;
    }

    /**
     * Gets the average Salary expenses for all employee in the list.
     */
    public double getAverageSalary() {
        double averageSalary;
        averageSalary = getTotalSalary() / internalList.size();
        return averageSalary;
    }

    /**
     * Gets the Longest Tenure employee and employee's name in the list.
     */
    public String getLongestTenure() {
        Date todaysDate = new Date();
        Date dateOfJoin;
        long longest = 0;
        long diffInMillies;
        long yearMiniSec = Long.parseLong("31536000000");
        long dayMiniSec = Long.parseLong("86400000");
        String employeeName = "";

        try {
            for (int i = 0; i < internalList.size(); i++) {
                dateOfJoin = new SimpleDateFormat("yyyy-MM-dd").parse(internalList.get(i).getDateOfJoining().value);
                diffInMillies = todaysDate.getTime() - dateOfJoin.getTime();
                if (longest < diffInMillies) {
                    longest = diffInMillies;
                    employeeName = internalList.get(i).getName().fullName;
                } else if (longest == diffInMillies) {
                    employeeName += ", " + internalList.get(i).getName().fullName;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        int totalYears = (int) (longest / yearMiniSec);
        int days = (int) (longest / dayMiniSec - (totalYears * 365));

        return totalYears + " Year(s) " + days + " Day(s) (" + employeeName + ")";

    }

    /**
     * Gets the Shortest Tenure employee and employee's name in the list.
     */
    public String getShortestTenure() {
        int totalYears = 0;
        int days = 0;
        String employeeName = internalList.get(0).getName().fullName;
        try {
            Date todaysDate = new Date();
            Date dateOfJoin;
            long yearMiniSec = Long.parseLong("31536000000");
            long dayMiniSec = Long.parseLong("86400000");
            long shortest =
                    new SimpleDateFormat("yyyy-MM-dd").parse(internalList.get(0).getDateOfJoining().value).getTime();
            long diffInMillies;

            for (int i = 1; i < internalList.size(); i++) {
                dateOfJoin = new SimpleDateFormat("yyyy-MM-dd").parse(internalList.get(i).getDateOfJoining().value);
                diffInMillies = todaysDate.getTime() - dateOfJoin.getTime();
                if (shortest > diffInMillies) {
                    shortest = diffInMillies;
                    employeeName = internalList.get(i).getName().fullName;
                } else if (shortest == diffInMillies) {
                    employeeName += ", " + internalList.get(i).getName().fullName;
                }
            }


            totalYears = (int) (shortest / yearMiniSec);
            days = (int) (shortest / dayMiniSec - (totalYears * 365));


        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return totalYears + " Year(s) " + days + " Day(s) (" + employeeName + ")";
    }

    /**
     * Gets the Average Tenure for all employee in the list.
     */
    public String getAverageTenure() {
        Date todaysDate = new Date();
        Date dateOfJoin;
        long diffInMillies;
        long yearMiniSec = Long.parseLong("31536000000");
        long dayMiniSec = Long.parseLong("86400000");
        long totalMillies = 0;
        long averageTenure = 0;

        try {
            for (int i = 0; i < internalList.size(); i++) {
                dateOfJoin = new SimpleDateFormat("yyyy-MM-dd").parse(internalList.get(i).getDateOfJoining().value);
                diffInMillies = todaysDate.getTime() - dateOfJoin.getTime();
                totalMillies += diffInMillies;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        averageTenure = totalMillies / internalList.size();
        int totalYears = (int) (averageTenure / yearMiniSec);
        int days = (int) (averageTenure / dayMiniSec - (totalYears * 365));
        return totalYears + " Year(s) " + days + " Day(s)";
    }

}
