import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class Store {
    private Map<Item, Integer> itemsForSale;
    private Map<Recipe, Integer> recipesForSale;

    public Store() {
        itemsForSale = new HashMap<>();
        recipesForSale = new HashMap<>();

        List<Item> itemList = Item.itemDijual();
        for (Item item : itemList) {
            // Misalnya kita kasih harga default 50, atau bisa item.getPrice()
            itemsForSale.put(item, item.getBuyPrice()); // asumsi ada getPrice()
        }

        recipesForSale = RecipeManager.recipeYangDijual();
    }

    // // Tambahkan item untuk dijual
    // public void addItemForSale(Item item, int price) {
    //     itemsForSale.put(item, price);
    // }

    // // Tambahkan resep untuk dijual
    // public void addRecipeForSale(Recipe recipe, int price) {
    //     recipesForSale.put(recipe, price);
    // }

    // Tampilkan daftar item
    public void showItemsForSale() {
        System.out.println("ITEM FOR SALE:");
        int idx = 1;
        for (Map.Entry<Item, Integer> entry : itemsForSale.entrySet()) {
            System.out.println(idx + entry.getKey().getName() + " : " + entry.getValue() + " coin");
            idx++;
        }
    }

    // Tampilkan daftar resep
    public void showRecipesForSale() {
        System.out.println("RECIPES FOR SALE:");
        int idx = 1;
        for (Map.Entry<Recipe, Integer> entry : recipesForSale.entrySet()) {
            System.out.println(idx + entry.getKey().getRecipeName() + " : " + entry.getValue() + " coin");
            idx++;
        }
    }

    // Beli item
    public boolean buyOneItem(Player player, String itemName) {
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

    // Beli item dengan jumlah tertentu
    public boolean buyItem(Player player, String itemName, int quantity) {
        if (quantity <= 0) {
            System.out.println("Jumlah yang dibeli harus lebih dari 0.");
            return false;
        }

        for (Map.Entry<Item, Integer> entry : itemsForSale.entrySet()) {
            if (entry.getKey().getName().equalsIgnoreCase(itemName)) {
                int pricePerItem = entry.getValue();
                int totalPrice = pricePerItem * quantity;

                if (player.getGold() < totalPrice) {
                    System.out.println("Uangmu tidak cukup untuk membeli " + quantity + " " + itemName + ".");
                    return false;
                }

                player.removeGold(totalPrice);
                player.getInventory().addItem(entry.getKey(), quantity);
                System.out.println("Kamu membeli " + quantity + " " + itemName + ".");
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
                // player.unlockRecipe(entry.getKey()); // set recipenya biar true
                System.out.println("Kamu membeli resep " + recipeName);
                return true;
            }
        }
        System.out.println("Resep tidak ditemukan.");
        return false;
    }
}
