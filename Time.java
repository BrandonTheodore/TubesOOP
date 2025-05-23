import java.time.LocalTime;

public class Time {
    private final int accelerationFactor;
    private final LocalTime startGameTime;
    private final long startRealTimeNano;

    public Time(int time, double accelerationFactor) {
        this.startGameTime = LocalTime.of(time, 0);
        this.accelerationFactor = 300;
        this.startRealTimeNano = System.nanoTime();
    }

    public LocalTime getCurrentGameTime() {
        long currentRealTimeNano = System.nanoTime();
        double realElapsedSeconds = (currentRealTimeNano - startRealTimeNano) / 1_000_000_000.0;

        long gameElapsedSeconds = (long) (realElapsedSeconds * accelerationFactor);
        return startGameTime.plusSeconds(gameElapsedSeconds);
    }

    public String getFormattedGameTime() {
        LocalTime gameTime = getCurrentGameTime();
        return gameTime.toString();
    }
}