import java.time.LocalTime;
import java.util.*;

public class Time {
    private final int accelerationFactor;
    private LocalTime startGameTime;
    private long startRealTimeNano;
    private String state;

    public Time() {
        this.startGameTime = LocalTime.of(6, 0);
        this.accelerationFactor = 300;
        this.startRealTimeNano = System.nanoTime();
        this.state = "DAY";
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
            this.state = "DAY";
        } else {
            this.state = "NIGHT";
        }

        return state;
    }



    public String getFormattedGameTime() {
        LocalTime gameTime = getCurrentGameTime();
        return gameTime.toString();
    }

    public void changeStartGameTime(LocalTime time){
        this.startGameTime = time;
    }

    public void changeStartRealTime(long time){
        this.startRealTimeNano = time;
    }
}