import java.util.List;

public class Fish extends Item {
    private List<Season> seasons;
    private List<Weather> weathers;
    private List<LocationFish> locations;
    private String timeStart;
    private String timeEnd;
    private Rarity rarity;
    
    public Fish(String name, int sellPrice, Rarity rarity, List<Season> seasons, List<Weather> weathers, List<LocationFish> locations, String timeStart, String timeEnd) {
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
    
    public List<LocationFish> getLocations() {
        return locations;
    }
    
    public String getTimeStart() {
        return timeStart;
    }
    
    public String getTimeEnd() {
        return timeEnd;
    }
    
    public Rarity getFishRarity() {
        return rarity;
    }
    
    public boolean isCatchable(Season currentSeason, Weather currentWeather, LocationFish currentLocation, String currentTime) {
        // season weather location hrs match
        boolean seasonMatch = seasons.contains(currentSeason);
        boolean weatherMatch = weathers.contains(currentWeather);
        boolean locationMatch = locations.contains(currentLocation);
        
        // ngubah waktu saat ini ke format jam
        int currentHour = parseTimeToHour(currentTime);
        int startHour = parseTimeToHour(timeStart);
        int endHour = parseTimeToHour(timeEnd);
        
        // waktu harus match
        boolean timeMatch;
        if (endHour < startHour) { // kalo melewati tengah malam (18:00 - 02:00)
            timeMatch = (currentHour >= startHour) || (currentHour <= endHour);
        } else { // kalo normal (06:00 - 18:00)
            timeMatch = (currentHour >= startHour) && (currentHour <= endHour);
        }
        
        return seasonMatch && weatherMatch && locationMatch && timeMatch;
    }
    
    // mengubah string waktu menjadi jam
    private int parseTimeToHour(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]);
    }

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
    
    // ngitung rentang jam
    private static int calculateHourRange(String timeStart, String timeEnd) {
        int start = Integer.parseInt(timeStart.replace(":", "")) / 100;
        int end = Integer.parseInt(timeEnd.replace(":", "")) / 100;
        
        if (end < start) {
            return (24 - start) + end;
        } else {
            return end - start;
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