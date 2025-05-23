import java.util.ArrayList;
import java.util.List;

public class CropsManager {
    private List<Crops> allCrops;

    public CropsManager() {
        allCrops = new ArrayList<>();

        allCrops.add(new Crops("Parsnip", 50, 35, 1));
        allCrops.add(new Crops("Cauliflower", 200, 150, 1));
        allCrops.add(new Crops("Potato", 0, 80, 1)); 
        allCrops.add(new Crops("Wheat", 50, 30, 3));
        allCrops.add(new Crops("Blueberry", 150, 40, 3));
        allCrops.add(new Crops("Tomato", 90, 60, 1));
        allCrops.add(new Crops("Hot Pepper", 0, 40, 1));
        allCrops.add(new Crops("Melon", 0, 250, 1));
        allCrops.add(new Crops("Cranberry", 0, 25, 10)); 
        allCrops.add(new Crops("Pumpkin", 300, 250, 1));
        allCrops.add(new Crops("Grape", 100, 10, 20));
    }

    // return semua nama crops
    public List<String> getAllCropsNames() {
        List<String> cropsNames = new ArrayList<>();
        for (Crops crops : allCrops) {
            cropsNames.add(crops.getName());
        }
        return cropsNames;
    }

    // buat dapetin string semua nama crops
    public String getCropsNamesAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allCrops.size(); i++) {
            sb.append(allCrops.get(i).getName());
            if (i < allCrops.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    // return semua crops
    public List<Crops> getAllCrops() {
        return new ArrayList<>(allCrops); // return copy dari array allCrops
    }

    // get crops berdasarkan nama
    public Crops getCropsByName(String name) {
        for (Crops crops : allCrops) {
            if (crops.getName().equalsIgnoreCase(name)) {
                return crops;
            }
        }
        return null;
    }
}