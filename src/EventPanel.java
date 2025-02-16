import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class EventPanel extends JPanel {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a");

    private final Event event;

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

        JLabel nameLabel = new JLabel(event.getName());
        JLabel dateLabel = new JLabel("Starts: " +
                event.getDateTime().format(formatter));

        if (event instanceof Meeting meeting) {
            dateLabel.setText(dateLabel.getText() + " | Duration: " +
                    formatDuration(meeting.getDuration()));
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
        long minutes = duration.toMinutes() % 60;  // Works in Java 8+
        return String.format("%dh %02dm", hours, minutes);
    }

    private void updateCompletionState() {
        // Visual feedback implementation
        revalidate();
        repaint();
    }
}