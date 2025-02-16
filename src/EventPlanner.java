import javax.swing.*;
import java.time.LocalDateTime;
/**
 * Holds the event panel
 */
public class EventPlanner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Event Planner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            EventListPanel eventList = new EventListPanel();
            addDefaultEvents(eventList);  // Add sample data

            frame.add(eventList);
            frame.setVisible(true);
        });
    }

    /**
     * Adds demonstration events to the list
     * @param eventsPanel Target panel to receive events
     */
    public static void addDefaultEvents(EventListPanel eventsPanel) {
        eventsPanel.addEvent(new Deadline("Submit Lab 2",
                LocalDateTime.of(2025, 2, 18, 23, 59)));

        eventsPanel.addEvent(new Meeting("Team Sync",
                LocalDateTime.of(2025, 2, 17, 14, 0),
                LocalDateTime.of(2025, 2, 17, 15, 0),
                "Zoom Room 5"));
    }
}