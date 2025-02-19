package LOGIC;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * This class represents a meeting event with duration and location
 */

public class Meeting extends Event implements Completable {
    private LocalDateTime endDateTime;
    private String location;
    private boolean isComplete;

    // Creates a new meeting with name, start/end times, and location
    public Meeting(String name, LocalDateTime start, LocalDateTime end, String location) {
        super(name, start);
        this.endDateTime = end;
        this.location = location;
        this.isComplete = false;
    }

    // Returns meeting name
    @Override
    public String getName() {
        return name;
    }

    // Calculates duration between start and end times
    public Duration getDuration() {
        return Duration.between(dateTime, endDateTime);
    }

    // Returns the location of the meeting
    public String getLocation() {
        return location;
    }

    // Returns the end time of the meeting
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    // Updates the end time of the meeting
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    // Updates the location of the meeting
    public void setLocation(String location) {
        this.location = location;
    }

    // Will mark the meeting as complete
    @Override
    public void complete() {
        isComplete = true;
    }

    // Returns the status of completion
    @Override
    public boolean isComplete() {
        return isComplete;
    }
}