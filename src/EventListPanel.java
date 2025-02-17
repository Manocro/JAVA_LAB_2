import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.awt.event.ActionListener;

public class EventListPanel extends JPanel {
    private List<Event> events = new ArrayList<>();
    private JPanel displayPanel = new JPanel();
    private JComboBox<String> sortDropDown;
    private JCheckBox hideCompletedCheck;
    private JCheckBox filterDeadlinesCheck;
    private JCheckBox filterMeetingsCheck;

    public EventListPanel() {
        setLayout(new BorderLayout());
        initializeControls();
        setupDisplayArea();
    }

    private void setupDisplayArea() {
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(displayPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initializeControls() {
        // Sorting controls
        JPanel controlPanel = new JPanel();
        String[] sortOptions = {"Date (asc)", "Date (desc)", "Name (asc)", "Name (desc)"};
        sortDropDown = new JComboBox<>(sortOptions);
        sortDropDown.addActionListener(e -> refreshDisplay());

        // Filtering controls
        hideCompletedCheck = new JCheckBox("Hide Completed");
        filterDeadlinesCheck = new JCheckBox("Deadlines Only");
        filterMeetingsCheck = new JCheckBox("Meetings Only");

        // Add listeners
        ActionListener filterListener = e -> refreshDisplay();
        hideCompletedCheck.addActionListener(filterListener);
        filterDeadlinesCheck.addActionListener(filterListener);
        filterMeetingsCheck.addActionListener(filterListener);

        // Layout
        controlPanel.add(new JLabel("Sort by:"));
        controlPanel.add(sortDropDown);
        controlPanel.add(hideCompletedCheck);
        controlPanel.add(filterDeadlinesCheck);
        controlPanel.add(filterMeetingsCheck);

        add(controlPanel, BorderLayout.NORTH);

        JButton addButton = new JButton("Add Event");
        addButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            new AddEventModal(topFrame, EventListPanel.this).setVisible(true);
        });
        controlPanel.add(addButton);
    }

    public void addEvent(Event event) {
        events.add(event);
        refreshDisplay();
    }

    private void refreshDisplay() {
        List<Event> filtered = applyFilters();
        List<Event> sorted = applySorting(filtered);

        displayPanel.removeAll();
        for (Event event : sorted) {
            displayPanel.add(new EventPanel(event));
        }
        displayPanel.revalidate();
        displayPanel.repaint();
    }

    private List<Event> applyFilters() {
        List<Event> result = new ArrayList<>();
        for (Event event : events) {
            if (shouldShow(event)) result.add(event);
        }
        return result;
    }

    private boolean shouldShow(Event event) {
        // Completion filter
        if (hideCompletedCheck.isSelected() && event instanceof Completable c) {
            if (c.isComplete()) return false;
        }

        // Type filters
        if (filterDeadlinesCheck.isSelected() && !(event instanceof Deadline)) return false;
        if (filterMeetingsCheck.isSelected() && !(event instanceof Meeting)) return false;

        return true;
    }

    private List<Event> applySorting(List<Event> events) {
        Comparator<Event> comparator = switch (sortDropDown.getSelectedIndex()) {
            case 0 -> Comparator.comparing(Event::getDateTime);
            case 1 -> (e1, e2) -> e2.getDateTime().compareTo(e1.getDateTime());
            case 2 -> Comparator.comparing(Event::getName);
            case 3 -> (e1, e2) -> e2.getName().compareToIgnoreCase(e1.getName());
            default -> (e1, e2) -> 0;
        };

        events.sort(comparator);
        return events;
    }
}

