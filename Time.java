import java.time.LocalTime;
import java.util.*;
import java.lang.*;
public class Time {
    private final int accelerationFactor;
    private LocalTime startGameTime;
    private long startRealTimeNano;
    private String state;
    private boolean isStopped = false;
    private LocalTime stoppedGameTime;
    private long stoppedRealTimeNano;

    public Time() {
        this.startGameTime = LocalTime.of(6, 0);
        this.accelerationFactor = 300;
        this.startRealTimeNano = System.nanoTime();
        this.state = "DAY";
    }

    public LocalTime getCurrentGameTime() {
        if (isStopped) {
            return stoppedGameTime;
        }
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

    public void stopTime() {
        if (!isStopped) {
            stoppedGameTime = getCurrentGameTime();
            stoppedRealTimeNano = System.nanoTime();
            isStopped = true;
        }
    }

    public void resumeTime() {
        if (isStopped) {
            long currentNano = System.nanoTime();
            long pausedDuration = currentNano - stoppedRealTimeNano;
            this.startGameTime = stoppedGameTime;
            this.startRealTimeNano = System.nanoTime();
            isStopped = false;
        }
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

    public void addTime(int minutes) {
        LocalTime currentGameTime = getCurrentGameTime();

        LocalTime newGameTime = currentGameTime.plusMinutes(minutes);

        this.startGameTime = newGameTime;
        this.startRealTimeNano = System.nanoTime();
    }

    public void setTime(LocalTime time) {
        this.startGameTime = time;
        this.startRealTimeNano = System.nanoTime();
    }
    public void update(){
        while(true){
            boolean first = true;
            while(true){
                LocalTime time = getCurrentGameTime();
                if(time.isAfter(LocalTime.of(00, 00)) && time.isBefore(LocalTime.of(01, 00)) && first){
                    System.out.println("Day Changed");
                    first = false;
                }
                if(time.isAfter(LocalTime.of(01, 00))){
                    break;
                }
            }
        }
        
    }

    public void runThread(){
        Runnable task = () -> {
            update();
        };

        Thread thread = new Thread(task);
        thread.start();
    }


    public static void main(String[] args) throws InterruptedException {
        Time time = new Time();
        Scanner scan = new Scanner(System.in);
        time.runThread();
        while(true){
            String input = scan.nextLine();
            switch(input){
                case "t" :
                    System.out.println(time.getFormattedGameTime());
                    break;
                case "s" :
                    time.stopTime();
                    break;
                case "r" :
                    time.resumeTime();
                case "a" :
                    time.addTime(60);
            }

        }
    }
}