package employeetracker.ui;

import java.util.Comparator;

import employeetracker.model.employee.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Employee}.
 */
public class EmployeeCard extends UiPart<Region> {

    private static final String FXML = "EmployeeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     */

    public final Employee employee;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label role;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label dateOfBirth;
    @FXML
    private Label dateOfJoining;
    @FXML
    private Label salary;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code EmployeeCode} with the given {@code Employee} and index to display.
     */
    public EmployeeCard(Employee employee, int displayedIndex) {
        super(FXML);
        this.employee = employee;
        id.setText(displayedIndex + ". ");
        name.setText(employee.getName().fullName);
        role.setText("\uD83D\uDC77 " + employee.getRole().value);
        phone.setText("\uD83D\uDD81 " + employee.getPhone().value);
        address.setText("\uD83C\uDFE0 " + employee.getAddress().value);
        email.setText("\uD83D\uDCE7 " + employee.getEmail().value);
        dateOfBirth.setText("\uD83D\uDC76 " + employee.getDateOfBirth().value);
        dateOfJoining.setText("\uD83D\uDC66 " + employee.getDateOfJoining().value);
        salary.setText("\uD83D\uDCB2 " + String.format("%,d", Integer.parseInt(employee.getSalary().value)));
        employee.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmployeeCard)) {
            return false;
        }

        // state check
        EmployeeCard card = (EmployeeCard) other;
        return id.getText().equals(card.id.getText())
                && employee.equals(card.employee);
    }
}
