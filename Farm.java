import java.util.Random;
import java.util.Scanner;

public class Farm {
    private String name;
    private Player player;
    private Map farmMap;
    private Time time;
    private Day day;
    private Season season;
    private Weather weather;

    public Farm(String farm, Player player){
        this.name = farm;
        this.player = player;
        this.farmMap = new Map();
        this.time = new Time();
        this.day = Day.MONDAY;
        this.season = Season.SPRING;
        this.weather = Weather.SUNNY;
    }

    public String getName(){
        return name;
    }

    public Player getPlayer(){
        return player;
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

    
}



