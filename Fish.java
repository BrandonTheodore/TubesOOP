import java.sql.Time;

public class Fish extends Item{
    private Season season;
    private Time time;
    private Weather weather;
    private Location location;
    private Rarity rarity;
    private int energy;

    public Fish(String name, int buyP, int sellP, ItemCategory c, Season s, Time t, Weather w, Location l, Rarity r, int e){
        super(name, buyP, sellP, c);
        this.season = s;
        this.time = t;
        this.weather = w;
        this.location = l;
        this.rarity = r;
        this.energy = e;
    }

    public int getEnergy(){
        return energy;
    }

    // untuk boolean isCatchable gue bingung kalau kasusnya ANY, should we add ANY di enum weather and season and time?
    public boolean isCatchable(Season s, int time, Weather w, Location l){
        return this.season == s && this.time == time && this.weather == w && this.location == l;
    }

    public void useItem(Player player, Fish fish){
        player.setEnergy(player.getEnergy() + fish.getEnergy());
    }
    
}
