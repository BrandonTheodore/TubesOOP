import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FishManager {
    private List<Fish> allFish;
    
    public FishManager() {
        allFish = new ArrayList<>();
        
        // common fish
        List<Season> anySeasons = Arrays.asList(Season.SUMMER, Season.FALL, Season.WINTER, Season.SPRING);
        List<Weather> anyWeathers = Arrays.asList(Weather.SUNNY, Weather.RAINY);
        
        allFish.add(new Fish("Bullhead", 0, Rarity.COMMON, anySeasons, anyWeathers, Arrays.asList(LocationFish.MOUNTAIN_LAKE), "00:00", "23:59"));
        allFish.add(new Fish("Carp", 0, Rarity.COMMON, anySeasons, anyWeathers, Arrays.asList(LocationFish.MOUNTAIN_LAKE, LocationFish.POND), "00:00", "23:59"));
        allFish.add(new Fish("Chub", 0, Rarity.COMMON, anySeasons, anyWeathers, Arrays.asList(LocationFish.FOREST_RIVER, LocationFish.MOUNTAIN_LAKE), "00:00", "23:59"));
        
        // regular fish
        allFish.add(new Fish("Largemouth Bass", 0, Rarity.REGULAR, anySeasons, anyWeathers, Arrays.asList(LocationFish.MOUNTAIN_LAKE), "06:00", "18:00"));
        allFish.add(new Fish("Rainbow Trout", 0, Rarity.REGULAR, Arrays.asList(Season.SUMMER), Arrays.asList(Weather.SUNNY), Arrays.asList(LocationFish.FOREST_RIVER, LocationFish.MOUNTAIN_LAKE), "06:00", "18:00"));
        allFish.add(new Fish("Sturgeon", 0, Rarity.REGULAR, Arrays.asList(Season.SUMMER, Season.WINTER), anyWeathers, Arrays.asList(LocationFish.MOUNTAIN_LAKE), "06:00", "18:00"));
        allFish.add(new Fish("Midnight Carp", 0, Rarity.REGULAR, Arrays.asList(Season.WINTER, Season.FALL), anyWeathers, Arrays.asList(LocationFish.MOUNTAIN_LAKE, LocationFish.POND), "20:00", "02:00"));
        allFish.add(new Fish("Flounder", 0, Rarity.REGULAR, Arrays.asList(Season.SPRING, Season.SUMMER), anyWeathers, Arrays.asList(LocationFish.OCEAN), "06:00", "22:00"));
        allFish.add(new Fish("Halibut", 0, Rarity.REGULAR, anySeasons, anyWeathers, Arrays.asList(LocationFish.OCEAN), "06:00", "11:00"));
        allFish.add(new Fish("Halibut", 0, Rarity.REGULAR, anySeasons, anyWeathers, Arrays.asList(LocationFish.OCEAN), "19:00", "02:00"));
        allFish.add(new Fish("Octopus", 0, Rarity.REGULAR, Arrays.asList(Season.SUMMER), anyWeathers, Arrays.asList(LocationFish.OCEAN), "06:00", "22:00"));
        allFish.add(new Fish("Pufferfish", 0, Rarity.REGULAR, Arrays.asList(Season.SUMMER), Arrays.asList(Weather.SUNNY), Arrays.asList(LocationFish.OCEAN), "00:00", "16:00"));
        allFish.add(new Fish("Sardine", 0, Rarity.REGULAR, anySeasons, anyWeathers, Arrays.asList(LocationFish.OCEAN), "06:00", "18:00"));
        allFish.add(new Fish("Super Cucumber", 0, Rarity.REGULAR, Arrays.asList(Season.SUMMER, Season.FALL, Season.WINTER), anyWeathers, Arrays.asList(LocationFish.OCEAN), "18:00", "02:00"));
        allFish.add(new Fish("Catfish", 0, Rarity.REGULAR, Arrays.asList(Season.SPRING, Season.SUMMER, Season.FALL), Arrays.asList(Weather.RAINY), Arrays.asList(LocationFish.FOREST_RIVER, LocationFish.POND), "06:00", "22:00"));
        allFish.add(new Fish("Salmon", 0, Rarity.REGULAR, Arrays.asList(Season.FALL), anyWeathers, Arrays.asList(LocationFish.FOREST_RIVER), "06:00", "18:00"));
        
        // legendary fish
        allFish.add(new Fish("Angler", 0, Rarity.LEGENDARY, Arrays.asList(Season.FALL), anyWeathers, Arrays.asList(LocationFish.POND), "08:00", "20:00"));
        allFish.add(new Fish("Crimsonfish", 0, Rarity.LEGENDARY, Arrays.asList(Season.SUMMER), anyWeathers, Arrays.asList(LocationFish.OCEAN), "08:00", "20:00"));
        allFish.add(new Fish("Glacierfish", 0, Rarity.LEGENDARY, Arrays.asList(Season.WINTER), anyWeathers, Arrays.asList(LocationFish.FOREST_RIVER), "08:00", "20:00"));
        allFish.add(new Fish("Legend", 0, Rarity.LEGENDARY, Arrays.asList(Season.SPRING), Arrays.asList(Weather.RAINY), Arrays.asList(LocationFish.MOUNTAIN_LAKE), "08:00", "20:00"));
    }
    
    // return semua nama ikan
    public List<String> getAllFishNames() {
        List<String> fishNames = new ArrayList<>();
        for (Fish fish : allFish) {
            fishNames.add(fish.getName());
        }
        return fishNames;
    }
    
    // Method untuk mendapatkan string semua nama ikan
    public String getFishNamesAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allFish.size(); i++) {
            sb.append(allFish.get(i).getName());
            if (i < allFish.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    
    // return semua ikan
    public List<Fish> getAllFish() {
        return new ArrayList<>(allFish); // return copy dari array allFish
    }
    
    // get ikan berdasarkan nama
    public Fish getFishByName(String name) {
        for (Fish fish : allFish) {
            if (fish.getName().equalsIgnoreCase(name)) {
                return fish;
            }
        }
        return null;
    }
    
    // Method untuk mendapatkan ikan yang bisa ditangkap di kondisi tertentu
    public List<Fish> getCatchableFish(Season season, Weather weather, LocationFish location, String time) {
        List<Fish> catchableFish = new ArrayList<>();
        for (Fish fish : allFish) {
            if (fish.isCatchable(season, weather, location, time)) {
                catchableFish.add(fish);
            }
        }
        return catchableFish;
    }
    
    // Method untuk mendapatkan ikan berdasarkan rarity
    public List<Fish> getFishByRarity(Rarity rarity) {
        List<Fish> fishByRarity = new ArrayList<>();
        for (Fish fish : allFish) {
            if (fish.getFishRarity() == rarity) {
                fishByRarity.add(fish);
            }
        }
        return fishByRarity;
    }
}