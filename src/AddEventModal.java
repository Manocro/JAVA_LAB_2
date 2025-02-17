import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class AddEventModal extends JDialog {
    private final EventListPanel parentPanel;
    private JTextField nameField;
    private JSpinner dateSpinner;
    private JSpinner endDateSpinner;
    private JTextField locationField;
    private JComboBox<String> typeCombo;

    public AddEventModal(JFrame parent, EventListPanel panel) {
        super(parent, "Add New Event", true);
        this.parentPanel = panel;
        setupUI();
        pack();
        setLocationRelativeTo(parent);
    }

    private void setupUI() {
        JPanel mainPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        // Event type selector
        mainPanel.add(new JLabel("Event Type:"));
        typeCombo = new JComboBox<>(new String[]{"Deadline", "Meeting"});
        mainPanel.add(typeCombo);

        // Common fields
        mainPanel.add(new JLabel("Event Name:"));
        nameField = new JTextField();
        mainPanel.add(nameField);

        mainPanel.add(new JLabel("Start Date/Time:"));
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MM/dd/yyyy HH:mm"));
        mainPanel.add(dateSpinner);

        // Meeting-specific fields
        mainPanel.add(new JLabel("End Date/Time:"));
        endDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "MM/dd/yyyy HH:mm"));
        mainPanel.add(endDateSpinner);
        endDateSpinner.setVisible(false);

        mainPanel.add(new JLabel("Location:"));
        locationField = new JTextField();
        mainPanel.add(locationField);
        locationField.setVisible(false);

        // Dynamic field visibility
        typeCombo.addActionListener(e -> updateFieldVisibility());

        // Submit button
        JButton submitButton = new JButton("Add Event");
        submitButton.addActionListener(e -> createEvent());
        mainPanel.add(submitButton);

        add(mainPanel);
    }

    private void updateFieldVisibility() {
        boolean isMeeting = typeCombo.getSelectedIndex() == 1;
        endDateSpinner.setVisible(isMeeting);
        locationField.setVisible(isMeeting);
    }

    private void createEvent() {
        try {
            LocalDateTime start = LocalDateTime.ofInstant(
                    ((Date) dateSpinner.getValue()).toInstant(),
                    ZoneId.systemDefault()
            );

            if (typeCombo.getSelectedIndex() == 0) {
                parentPanel.addEvent(new Deadline(
                        nameField.getText(),
                        start
                ));
            } else {
                LocalDateTime end = LocalDateTime.ofInstant(
                        ((Date) endDateSpinner.getValue()).toInstant(),
                        ZoneId.systemDefault()
                );
                parentPanel.addEvent(new Meeting(
                        nameField.getText(),
                        start,
                        end,
                        locationField.getText()
                ));
            }
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }
}