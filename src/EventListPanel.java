import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel that manages and displays a list of events
 */

public class EventListPanel extends JPanel {
    private ArrayList<Event> events = new ArrayList<>();
    private JPanel displayPanel = new JPanel();

    public EventListPanel() {
        setLayout(new BorderLayout());

        // Setup display area
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(displayPanel);
        add(scrollPane, BorderLayout.CENTER);

        initializeControls();
    }

    /**
     * Adds a new event to the list and updates display
     */
    public void addEvent(Event event) {
        events.add(event);
        refreshDisplay();
    }

    /**
     * Rebuilds the event display
     */
    private void refreshDisplay() {
        displayPanel.removeAll();
        for (Event event : events) {
            displayPanel.add(new EventPanel(event));
        }
        displayPanel.revalidate();
        displayPanel.repaint();
    }

    private void initializeControls() {
        // Will add sorting/filter controls here later
    }
}