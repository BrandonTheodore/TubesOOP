import java.util.Map;
import java.util.HashMap;


public class Recipe {
    private String recipeName;
    private Map<String, Integer> ingredients;
    private Food food;
    private boolean isUnlocked;
    private String unlockInfo;

    public Recipe(String recipeName) {
        this.recipeName = recipeName;
        ingredients = new HashMap<>();
        food = null;
        isUnlocked = false;
        unlockInfo = "";
    }

    public void addIngredient(String name, int quantity) {
        ingredients.put(name, quantity); // pakai lowercase buat konsistensi
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean isUnlocked) {
        this.isUnlocked = isUnlocked;
    }

    public String getUnlockInfo() {
        return unlockInfo;
    }

    public void setUnlockInfo(String unlockInfo) {
        this.unlockInfo = unlockInfo;
    }

    public void printIngredients(Food food) {
        System.out.println("Bahan-bahan untuk membuat " + food.getName() + ":");
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            System.out.println("- " + entry.getKey() + " x" + entry.getValue());
        }
    }

    public void printIngredientsByName(String name) {
        System.out.println("Bahan-bahan untuk membuat " + name + ":");
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            System.out.println("- " + entry.getKey() + " x" + entry.getValue());
        }
    }

    public boolean hasRequiredItems(Player player) {
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            Item item = player.getInventory().getItemByName(entry.getKey());
            if (item == null || player.getInventory().getItemQuantity(item) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public void consumeIngredients(Player player) {
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            Item item = player.getInventory().getItemByName(entry.getKey());
            if (item != null) {
                player.getInventory().removeItem(item, entry.getValue());
            }
        }
    }
}
