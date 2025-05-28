import java.time.LocalTime;
import java.util.Random;

public class Farm {
    private String name;
    private Map farmMap;
    private Time time;
    private Day day;
    private Season season;
    private Weather weather;
    private int dayCount;
    private int rainyDay;

    public Farm(String farm){
        this.name = farm;
        this.farmMap = new Map();
        this.time = new Time();
        this.day = Day.MONDAY;
        this.season = Season.SPRING;
        this.weather = Weather.SUNNY;
        this.dayCount = 1;
        this.rainyDay = 0;
    }

    public String getName(){
        return name;
    }

    public Map getfarmMap(){
        return farmMap;
    }

    public Time getTime(){
        return time;
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

    public int getRainyDay(){
        return rainyDay;
    }

    public int getDayCount(){
        return dayCount;
    }

    public void changeDay(){
        Random rand = new Random();
        time.changeStartGameTime(LocalTime.of(6, 0));
        time.changeStartRealTime(System.nanoTime()); 
        this.day = day.nextDay();
        this.dayCount++;
        int randomNumber = rand.nextInt(1000);
        if(dayCount % 10 == 1){
            changeSeason();
        }
        if(randomNumber % 2 == 0 || (dayCount > 8 && rainyDay < 2)){
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
        System.out.println("Time : " + time.getFormattedGameTime());
        System.out.println("Today is " + day.toString());
        System.out.println("Current season is " + season.toString());
        System.out.println("Today's weather is " + weather.toString());
    }
    
}



