import java.util.Map;
import java.util.HashMap;

public class Store {
    private Map<Item, Integer> itemsForSale;
    private Map<Recipe, Integer> recipesForSale;

    public Store() {
        itemsForSale = new HashMap<>();
        recipesForSale = new HashMap<>();
    }

    // Tambahkan item untuk dijual
    public void addItemForSale(Item item, int price) {
        itemsForSale.put(item, price);
    }

    // Tambahkan resep untuk dijual
    public void addRecipeForSale(Recipe recipe, int price) {
        recipesForSale.put(recipe, price);
    }

    // Tampilkan daftar item
    public void showItemsForSale() {
        System.out.println("ITEM FOR SALE:");
        for (Map.Entry<Item, Integer> entry : itemsForSale.entrySet()) {
            System.out.println("- " + entry.getKey().getName() + " : " + entry.getValue() + " coin");
        }
    }

    // Tampilkan daftar resep
    public void showRecipesForSale() {
        System.out.println("RECIPES FOR SALE:");
        for (Map.Entry<Recipe, Integer> entry : recipesForSale.entrySet()) {
            System.out.println("- " + entry.getKey().getRecipeName() + " : " + entry.getValue() + " coin");
        }
    }

    // Beli item
    public boolean buyItem(Player player, String itemName) {
        for (Map.Entry<Item, Integer> entry : itemsForSale.entrySet()) {
            if (entry.getKey().getName().equalsIgnoreCase(itemName)) {
                int price = entry.getValue();
                if (player.getGold() < price) {
                    System.out.println("Uangmu tidak cukup.");
                    return false;
                }
                player.removeGold(price);
                player.getInventory().addItem(entry.getKey(), 1);
                System.out.println("Kamu membeli " + itemName);
                return true;
            }
        }
        System.out.println("Item tidak ditemukan.");
        return false;
    }

    // Beli resep
    public boolean buyRecipe(Player player, String recipeName) {
        for (Map.Entry<Recipe, Integer> entry : recipesForSale.entrySet()) {
            if (entry.getKey().getRecipeName().equalsIgnoreCase(recipeName)) {
                int price = entry.getValue();
                if (player.getGold() < price) {
                    System.out.println("Uangmu tidak cukup.");
                    return false;
                }
                player.removeGold(price);
                entry.getKey().setUnlocked(true); // resep terbuka
                player.unlockRecipe(entry.getKey()); // simpan di player
                System.out.println("Kamu membeli resep " + recipeName);
                return true;
            }
        }
        System.out.println("Resep tidak ditemukan.");
        return false;
    }
}
