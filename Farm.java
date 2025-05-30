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
    private int seasonCount;
    private int rainyDay;

    public Farm(String farm, Time time){
        this.name = farm;
        this.farmMap = new Map();
        this.time = time;
        this.day = Day.MONDAY;
        this.season = Season.SPRING;
        this.weather = Weather.SUNNY;
        this.dayCount = 0;
        this.seasonCount = 0;
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

    public void setDay(Day day){
        this.day = day;
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

    public void setDayCount(int day){
        this.dayCount = day;
    }

    public int getSeasonCount(){
        return this.seasonCount;
    }

    public void changeDay(){
        Random rand = new Random();
        time.changeStartRealTime(System.nanoTime()); 
        int randomNumber = rand.nextInt(1000);
        if(dayCount % 10 == 1){
            changeSeason();
        }
        if(randomNumber % 2 == 0 || (dayCount > 8 && rainyDay < 2)){
            changeWeather();
        }
        if(time.getCurrentGameTime().isAfter(LocalTime.of(05, 59))){
            System.out.println("Day has changed!");
        }
    }

    public void updateDay(Player player){
        while(true){
            boolean first = true;
            while(true){
                LocalTime waktu = time.getCurrentGameTime();
                if(waktu.isAfter(LocalTime.of(00, 00)) && waktu.isBefore(LocalTime.of(01, 00)) && first){
                    System.out.println("\nDay Changed");
                    this.day = day.nextDay();
                    this.dayCount++;
                    player.resetShowerCount();
                    player.reserBathCount();
                    first = false;
                    rainWater();
                }
                if(waktu.isAfter(LocalTime.of(01, 00))){
                    break;
                }
            }
        }
    }

    public void runThread(Player player){
        Runnable task = () -> {
            updateDay(player);
        };

        Thread thread = new Thread(task);
        thread.start();
    }

    public void changeSeason(){
        this.season = season.nextSeason();
        this.seasonCount++;
        System.out.println("Season has changed!");
    }

    public void changeWeather(){
        this.weather = weather.nextWeather();
        System.out.println("Today is a " + weather.toString() + " day");
    }

    public void showTime(){
        System.out.println("\nTime played:");
        System.out.println(this.dayCount + " day(s) played");
        System.out.println(this.seasonCount + " season(s) has passed");
        System.out.println("");
        System.out.println("Current game time:");
        System.out.println("Time : " + time.getFormattedGameTime());
        System.out.println("Today is " + day.toString());
        System.out.println("Today's weather is " + weather.toString());
        System.out.println("Current season is " + season.toString());
    }

    public void rainWater(){
        char[][] currentMap = this.farmMap.getMap();
        if(this.weather == Weather.RAINY){
            for(int row = 0; row < 32; row++){
                for(int col = 0; col < 32; col++){
                    if(currentMap[row][col] == 'l'){
                        this.farmMap.setTile('w', col, row);
                    }
                }
            }
            System.out.println("All plants are watered.");
        }
    }
}



