import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fish extends Item {
    private List<Season> seasons;
    private List<Weather> weathers;
    private List<Location> locations; // mountain lake, pond, dll
    private String timeStart;
    private String timeEnd;
    private Rarity rarity;

    // seasons untuk masing-masing ikan
    List<Season> anySeasons = Arrays.asList(Season.SUMMER, Season.AUTUMN, Season.WINTER, Season.SPRING); // rarity common, largemouth bass, halibut, sardine
    List<Season> rainbowSeasons = Arrays.asList(Season.SUMMER);
    List<Season> sturgeonSeasons = Arrays.asList(Season.SUMMER, Season.WINTER);
    List<Season> midnightSeasons= Arrays.asList(Season.WINTER, Season.FALL);
    List<Season> flounderSeasons = Arrays.asList(Season.SPRING, Season.SUMMER);
    List<Season> octopusSeasons = Arrays.asList(Season.SUMMER);
    List<Season> pufferfishSeasons = Arrays.asList(Season.SUMMER);
    List<Season> superCucumberSeasons = Arrays.asList(Season.SUMMER, Season.FALL, Season.WINTER);
    List<Season> catFishSeasons = Arrays.asList(Season.SPRING, Season.SUMMER, Season.FALL);
    List<Season> salmonSeasons = Arrays.asList(Season.FALL);
    List<Season> anglerSeasons = Arrays.asList(Season.FALL);
    List<Season> crimsonfishSeasons = Arrays.asList(Season.SUMMER);
    List<Season> glacierfishSeason = Arrays.asList(Season.WINTER);
    List<Season> LegendSeasons = Arrays.asList(Season.SPRING);

    // weathers untuk masing-masing ikan
    List<Weather> anyWeathers = Arrays.asList(Weather.SUNNY, Weather.RAINY); // common fish, largemouth bass, sturgeon, midnight carp, flounder, halibut, octopus, sardine, super cucumber, salmon, angler, crimsonfish, glacierfish
    List<Weather> summerWeathers = Arrays.asList(Weather.SUNNY); // rainbow trout, pufferfish 
    List<Weather> rainyWeathers = Arrays.asList(Weather.RAINY); // catfish, legend

    // location untuk masing-masing ikan
    List<LocationFish> bullheadLocations = Arrays.asList(LocationFish.MOUNTAIN_LAKE);
    List<LocationFish> carpLocations = Arrays.asList(LocationFish.MOUNTAIN_LAKE, LocationFish.POND);
    List<LocationFish> chubLocations = Arrays.asList(LocationFish.FOREST_RIVER, LocationFish.MOUNTAIN_LAKE);
    List<LocationFish> largemouthLocations = Arrays.asList(LocationFish.MOUNTAIN_LAKE);
    List<LocationFish> rainbowLocations = Arrays.asList(LocationFish.FOREST_RIVER, LocationFish.MOUNTAIN_LAKE);
    List<LocationFish> sturgeonLocations = Arrays.asList(LocationFish.MOUNTAIN_LAKE);
    List<LocationFish> midnightLocations = Arrays.asList(LocationFish.MOUNTAIN_LAKE, LocationFish.POND);
    List<LocationFish> oceanLocations = Arrays.asList(LocationFish.OCEAN); // flounder, halibut, octopus, pufferfish, sardine, super cucumber, crimsonfish
    List<LocationFish> catfishLocations = Arrays.asList(LocationFish.FOREST_RIVER, LocationFish.POND);
    List<LocationFish> salmonLocations = Arrays.asList(LocationFish.FOREST_RIVER);
    List<LocationFish> anglerLocations = Arrays.asList(LocationFish.POND);
    List<LocationFish> glacierLocations = Arrays.asList(LocationFish.FOREST_RIVER);
    List<LocationFish> legendLocations = Arrays.asList(LocationFish.MOUNTAIN_LAKE);

    
    public Fish(String name, int sellPrice, Rarity rarity, List<Season> seasons, List<Weather> weathers, List<Location> locations, String timeStart, String timeEnd) {
        super(name, 0, calculateSellPrice(rarity, seasons.size(), calculateHourRange(timeStart, timeEnd), weathers.size(), locations.size()), ItemCategory.FISH); // fish gabisa dibeli jadi buyPrice = 0
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
    
    public String getTimeStart() {
        return timeStart;
    }
    
    public String getTimeEnd() {
        return timeEnd;
    }
    
    public Rarity getFishRarity() {
        return rarity;
    }
    
    public boolean isCatchable(Season currentSeason, Weather currentWeather, Location currentLocation, String currentTime) {
        boolean seasonMatch = seasons.contains(currentSeason); // cek season di parameter ada di list seasons gak, kalo ada true
        boolean weatherMatch = weathers.contains(currentWeather);// cek weather di parameter ada di list weathers gak, kalo ada true
        boolean locationMatch = locations.contains(currentLocation); // cek season di parameter ada di list seasons gak, kalo ada true
        
        // masih bingung untuk time mau gimana
        boolean timeMatch = timeStart != null && timeEnd != null; 

        return seasonMatch && weatherMatch && locationMatch && timeMatch;
    }

    public void useItem(Player player) {
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
                rarityMultiplier = 5; // default raritynya bakal regular
        }
        return (int) (baseMultiplier * rarityMultiplier);
    }
}