import java.util.ArrayList;
import java.util.List;

public class SeedsManager {
    private List<Seeds> allSeeds;
    private CropsManager cropsManager; // untuk mendapatkan crops reference

    public SeedsManager() {
        cropsManager = new CropsManager();
        allSeeds = new ArrayList<>();

        // spring
        allSeeds.add(new Seeds("Parsnip Seeds", 20, Season.SPRING, 1, cropsManager.getCropsByName("Parsnip")));
        allSeeds.add(new Seeds("Cauliflower Seeds", 80, Season.SPRING, 5, cropsManager.getCropsByName("Cauliflower")));
        allSeeds.add(new Seeds("Potato Seeds", 50, Season.SPRING, 3, cropsManager.getCropsByName("Potato")));
        allSeeds.add(new Seeds("Wheat Seeds", 60, Season.SPRING, 1, cropsManager.getCropsByName("Wheat")));

        // summer
        allSeeds.add(new Seeds("Blueberry Seeds", 80, Season.SUMMER, 7, cropsManager.getCropsByName("Blueberry")));
        allSeeds.add(new Seeds("Tomato Seeds", 50, Season.SUMMER, 3, cropsManager.getCropsByName("Tomato")));
        allSeeds.add(new Seeds("Hot Pepper Seeds", 40, Season.SUMMER, 1, cropsManager.getCropsByName("Hot Pepper")));
        allSeeds.add(new Seeds("Melon Seeds", 80, Season.SUMMER, 4, cropsManager.getCropsByName("Melon")));

        // fall
        allSeeds.add(new Seeds("Cranberry Seeds", 100, Season.FALL, 2, cropsManager.getCropsByName("Cranberry")));
        allSeeds.add(new Seeds("Pumpkin Seeds", 150, Season.FALL, 7, cropsManager.getCropsByName("Pumpkin")));
        allSeeds.add(new Seeds("Wheat Seeds", 60, Season.FALL, 1, cropsManager.getCropsByName("Wheat"))); // Wheat bisa spring dan fall
        allSeeds.add(new Seeds("Grape Seeds", 60, Season.FALL, 3, cropsManager.getCropsByName("Grape")));
    }

    // return semua nama seeds
    public List<String> getAllSeedsNames() {
        List<String> seedsNames = new ArrayList<>();
        for (Seeds seeds : allSeeds) {
            seedsNames.add(seeds.getName());
        }
        return seedsNames;
    }

    // buat return dalam bentuk string semua nama seeds
    public String getSeedsNamesAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allSeeds.size(); i++) {
            sb.append(allSeeds.get(i).getName());
            if (i < allSeeds.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    // return semua seeds
    public List<Seeds> getAllSeeds() {
        return new ArrayList<>(allSeeds); // return copy dari array allSeeds
    }

    // get seeds berdasarkan nama
    public Seeds getSeedsByName(String name) {
        for (Seeds seeds : allSeeds) {
            if (seeds.getName().equalsIgnoreCase(name)) {
                return seeds;
            }
        }
        return null;
    }
}