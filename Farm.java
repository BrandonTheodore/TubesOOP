import java.util.Random;
import java.util.Scanner;

public class Farm {
    private String name;
    private Player player;
    private Map farmMap;
    private Time time;
    private String day;
    private String season;
    private String weather;

    public Farm(String farm, Player player, int time){
        this.name = farm;
        this.player = player;
        this.farmMap = new Map();
        this.time = new Time();
        this.day = "P";
        this.season = "P";
        this.weather = "P";
    }


}



