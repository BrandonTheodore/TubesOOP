import java.util.List;
import java.time.LocalTime;


public class Fish extends Item {
    private List<Season> seasons;
    private List<Weather> weathers;
    private List<Location> locations;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private Rarity rarity;
   
    public Fish(String name, int sellPrice, Rarity rarity, List<Season> seasons, List<Weather> weathers, List<Location> locations, LocalTime timeStart, LocalTime timeEnd) {
        super(name, 0, calculateSellPrice(rarity, seasons.size(), calculateHourRange(timeStart, timeEnd), weathers.size(), locations.size()), ItemCategory.FISH);
        this.rarity = rarity;
        this.seasons = seasons;
        this.weathers = weathers;
        this.locations = locations;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }
   
    public List<Season> getSeasons() {
        return seasons;
    }
   
    public List<Weather> getWeathers() {
        return weathers;
    }
   
    public List<Location> getLocations() {
        return locations;
    }
   
    public LocalTime getTimeStart() {
        return timeStart;
    }
   
    public LocalTime getTimeEnd() {
        return timeEnd;
    }
   
    public Rarity getFishRarity() {
        return rarity;
    }

   
    public boolean isCatchable(Season currentSeason, Weather currentWeather, Location currentLocation, LocalTime currentTime) {
        // season weather location hrs match
        boolean seasonMatch = seasons.contains(currentSeason);
        boolean weatherMatch = weathers.contains(currentWeather);
        boolean locationMatch = locations.contains(currentLocation);
       
        boolean timeMatch = isTimeInRange(currentTime, timeStart, timeEnd);
       
       
        return seasonMatch && weatherMatch && locationMatch && timeMatch;
    }


    private boolean isTimeInRange(LocalTime currentTime, LocalTime startTime, LocalTime endTime) {
        // buat kasus kalo endTime lebih kecil drpd startTime kek 02.00 dan 20.00
        if (endTime.isBefore(startTime)) {
            // currentTime bisa di antara startTime sampai 23:59 ATAU 00:00 sampai endTime
            return !currentTime.isBefore(startTime) || !currentTime.isAfter(endTime);
        } else {
            // Waktu normal dalam satu hari
            // currentTime harus di antara startTime dan endTime
            return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
        }
    }  


    private static int calculateHourRange(LocalTime timeStart, LocalTime timeEnd) {
        int startHour = timeStart.getHour();
        int endHour = timeEnd.getHour();
        
        int hourRange;
        
        if (endHour < startHour) {
            hourRange = (24 - startHour) + endHour;
        } 
        else {
            hourRange = endHour - startHour;
        }
        
        // Pastikan hourRange minimal 1 untuk menghindari pembagian dengan 0
        return Math.max(hourRange, 1);
    }
   
    @Override
    public void useItem(Player player, Item item) {
        if (player != null) {
            Inventory inventory = player.getInventory();
            if (inventory.checkItem(this)) {
                player.addEnergy(1); // kalo makan ikan energi nambah 1
                inventory.removeItem(this, 1); // remove item dari inventory
                System.out.println("Player makan ikan " + getName() + " ,energy +1");
            }
            else {
                System.out.println(getName() + " is not available in your inventory.");
            }
        }
    }
   
   
    public static int calculateSellPrice(Rarity rarity, int seasonCount, int hourCount, int weatherCount, int locationCount) {
        double baseMultiplier = (4.0 / seasonCount) * (24.0 / hourCount) * (2.0 / weatherCount) * (4.0 / locationCount);
        int rarityMultiplier;
        switch(rarity){
            case COMMON:
                rarityMultiplier = 10;
                break;
            case REGULAR:
                rarityMultiplier = 5;
                break;
            case LEGENDARY:
                rarityMultiplier = 25;
                break;
            default:
                rarityMultiplier = 5;
        }
        return (int) (baseMultiplier * rarityMultiplier);
    }
}
