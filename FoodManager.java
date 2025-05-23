import java.util.ArrayList;
import java.util.List;

public class FoodManager {
    private List<Food> allFood; 

    public FoodManager(){
        allFood = new ArrayList<>();

        allFood.add(new Food("Fish n' Chips", 150, 135, 50));
        allFood.add(new Food("Baguette", 100, 80, 25));
        allFood.add(new Food("Sashimi", 300, 275, 70));
        allFood.add(new Food("Fugu", 0, 135, 50)); 
        allFood.add(new Food("Wine", 100, 90, 20));
        allFood.add(new Food("Pumpkin Pie", 120, 100, 35));
        allFood.add(new Food("Veggie Soup", 140, 120, 40));
        allFood.add(new Food("Fish Stew", 280, 260, 70));
        allFood.add(new Food("Spakbor Salad", 0, 250, 70)); 
        allFood.add(new Food("Fish Sandwich", 200, 180, 50));
        allFood.add(new Food("The Legends of Spakbor", 0, 2000, 100)); 
        allFood.add(new Food("Cooked Pig's Head", 1000, 0, 100)); 
    }
    
    // return semua nama food
    public List<String> getAllFoodNames() {
        List<String> foodNames = new ArrayList<>();
        for (Food food : allFood) {
            foodNames.add(food.getName());
        }
        return foodNames;
    }
    
    // method untuk get food name as string
    public String getFoodNamesAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allFood.size(); i++) {
            sb.append(allFood.get(i).getName());
            if (i < allFood.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    
    // return semua food
    public List<Food> getAllFood() {
        return new ArrayList<>(allFood); // return copy dari array allFood
    }
    
    // get food berdasarkan name
    public Food getFoodByName(String name) {
        for (Food food : allFood) {
            if (food.getName().equalsIgnoreCase(name)) {
                return food;
            }
        }
        return null;
    }
}