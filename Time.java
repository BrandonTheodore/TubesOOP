import java.time.LocalTime;

public class Time {
    private final int accelerationFactor;
    private final LocalTime startGameTime;
    private final long startRealTimeNano;
    private String state; // Siang dan malam

    public Time(int time, double accelerationFactor) {
        this.startGameTime = LocalTime.of(time, 0);
        this.accelerationFactor = 300;
        this.startRealTimeNano = System.nanoTime();
        this.state = "Siang";
    }

    public LocalTime getCurrentGameTime() {
        long currentRealTimeNano = System.nanoTime();
        double realElapsedSeconds = (currentRealTimeNano - startRealTimeNano) / 1_000_000_000.0;

        long gameElapsedSeconds = (long) (realElapsedSeconds * accelerationFactor);
        return startGameTime.plusSeconds(gameElapsedSeconds);
    }

    public String getState() {
        LocalTime currentTime = getCurrentGameTime();

        LocalTime dayStart = LocalTime.of(06, 00);
        LocalTime dayEnd = LocalTime.of(17, 59);

        if(currentTime.equals(dayStart) || currentTime.equals(dayEnd) || (currentTime.isAfter(dayStart) && currentTime.isBefore(dayEnd))){
            this.state = "Siang";
        } else {
            this.state = "Malam";
        }

        return state;
    }

    public String getFormattedGameTime() {
        LocalTime gameTime = getCurrentGameTime();
        return gameTime.toString();
    }
}