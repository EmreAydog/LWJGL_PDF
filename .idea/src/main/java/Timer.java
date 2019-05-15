public class Timer {

    // What was last loop's time?
    private double lastLoopTime;

    // Initialize the lastLoopTime with the current time
    public void init() {
        lastLoopTime = getTime();
    }

    // Return the time in seconds
    public double getTime() {
        // We divide by 1 billion to get the time in seconds
        return System.nanoTime() / 1_000_000_000.0;
    }

    // Perform calculations to get the elapsed time
    public float getElapsedTime() {

        // Variable that will hold the current time
        double time = getTime();

        // Calculate the elapsed time using the current and last time
        float elapsedTime = (float) (time - lastLoopTime);

        // Update the lastLoopTime to be for this iteration
        lastLoopTime = time;

        return elapsedTime;
    }

    // Getter for the lastLoopTime
    public double getLastLoopTime() {
        return lastLoopTime;
    }
}
