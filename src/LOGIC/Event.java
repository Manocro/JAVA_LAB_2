package LOGIC;

import java.time.LocalDateTime;

public abstract class Event implements Comparable<Event> {
    protected String name;
    protected LocalDateTime dateTime;

    // Constructor to initialize name and date/time
    public Event(String name, LocalDateTime dateTime) {
        this.name = name;
        this.dateTime = dateTime;
    }

    // Abstract method to get event name (implementation required in subclasses)
    public abstract String getName();

    // Returns the event's date and time
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // Updates the event's date and time
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    // Updates the event's name
    public void setName(String name) {
        this.name = name;
    }

    // Compares events by date/time for sorting
    @Override
    public int compareTo(Event other) {
        return this.dateTime.compareTo(other.getDateTime());
    }
}