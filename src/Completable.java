public interface Completable {
    // Marks the event as completed
    void complete();

    // Checks if the event is completed
    boolean isComplete();
}
