import java.time.LocalTime;
import java.util.*;

public class Time {
    private final int accelerationFactor;
    private LocalTime startGameTime;
    private long startRealTimeNano;
    private String state;
    private Day day;
    private Weather weather;
    private Season season;
    private int dayCount;

    public Time(Day day, Weather weather, Season season) {
        this.startGameTime = LocalTime.of(6, 0);
        this.accelerationFactor = 300;
        this.startRealTimeNano = System.nanoTime();
        this.state = "DAY";
        this.day = day;
        this.weather = weather;
        this.season = season;
        this.dayCount = 1;
    }

    public int getDayCount(){
        return dayCount;
    }

    public Day getDay(){
        return day;
    }

    public Season getSeason(){
        return season;
    }

    public Weather getWeather(){
        return weather;
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

    public void changeDay(){
        Random rand = new Random();
        this.startGameTime = LocalTime.of(6, 0);
        this.startRealTimeNano = System.nanoTime();
        this.day = day.nextDay();
        this.dayCount++;
        int randomNumber = rand.nextInt(1000);
        if(dayCount % 10 == 1){
            changeSeason();
        }
        if(randomNumber % 2 == 0){
            changeWeather();
        }
        System.out.println("Day has changed!");
    }

    public void changeSeason(){
        this.season = season.nextSeason();
        System.out.println("Season has changed!");
    }

    public void changeWeather(){
        this.weather = weather.nextWeather();
        System.out.println("Today is a " + weather.toString() + " day");
    }

    public void showTime(){
        System.out.println("Time : " + getFormattedGameTime());
        System.out.println("Today is " + day.toString());
        System.out.println("Current season is " + season.toString());
        System.out.println("Today's weather is " + weather.toString());
    }
    public static void main(String[] args){
        Time waktu = new Time(Day.MONDAY, Weather.SUNNY, Season.SPRING);

        Scanner input = new Scanner(System.in);

        while(true){
            String x = input.nextLine();
            switch(x){
                case "a" :
                    waktu.showTime();
                    break;
                case "b" :
                    System.out.println(waktu.getState());
                    break;
                case "c" :
                    waktu.changeDay();
                    break;
                case "d" :
                    waktu.changeSeason();
                    break;
            }
        }
        
    }
}