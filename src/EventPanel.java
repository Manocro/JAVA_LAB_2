import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class EventPanel extends JPanel {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a");

    private final Event event;
    private JLabel nameLabel; // Store the name label as a field to update it later

    public EventPanel(Event event) {
        this.event = event;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEtchedBorder());
        buildInfoPanel();
        addControlButtons();
    }

    private void buildInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        // Create the name label and store it as a field
        nameLabel = new JLabel(event.getName());
        JLabel dateLabel = new JLabel("Starts: " + event.getDateTime().format(formatter));

        if (event instanceof Meeting meeting) {
            dateLabel.setText(dateLabel.getText() + " | Duration: " + formatDuration(meeting.getDuration()));
            infoPanel.add(new JLabel("Location: " + meeting.getLocation()));
        }

        infoPanel.add(nameLabel);
        infoPanel.add(dateLabel);
        add(infoPanel, BorderLayout.CENTER);
    }

    private void addControlButtons() {
        JPanel buttonPanel = new JPanel();
        if (event instanceof Completable completable) {
            JButton completeButton = new JButton("Complete");
            completeButton.addActionListener(e -> {
                completable.complete();
                updateCompletionState();
            });
            buttonPanel.add(completeButton);
        }
        add(buttonPanel, BorderLayout.EAST);
    }

    // Fixed duration formatting for Java 8 compatibility
    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60; // Works in Java 8+
        return String.format("%dh %02dm", hours, minutes);
    }

    private void updateCompletionState() {
        // Update the name label to include a check mark if the event is complete
        if (((Completable) event).isComplete()) {
            nameLabel.setText(event.getName() + " ✓"); // Add a check mark
            nameLabel.setForeground(Color.GRAY);
        } else {
            nameLabel.setText(event.getName()); // Remove the check mark
            nameLabel.setForeground(Color.BLACK);
        }

        // Revalidate and repaint the panel to reflect the changes
        revalidate();
        repaint();
    }
}