import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    private Map<Item, Integer> itemsForSale;
    private Map<Recipe, Integer> recipesForSale;
    // List<Recipe> all = RecipeManager.getAllRecipes();
    private int totalExpenditure;

    public Store() {
        itemsForSale = new HashMap<>();
        recipesForSale = new HashMap<>();

        List<Item> itemList = Item.itemDijual();
        for (Item item : itemList) {
            itemsForSale.put(item, item.getBuyPrice());
        }
    
        for (Recipe r : RecipeManager.getAllRecipes()) {
            if (r.getRecipeName().equals("Fish n' Chips")) {
                recipesForSale.put(r, 70);
            }
            if (r.getRecipeName().equals("Fish Sandwich")) {
                recipesForSale.put(r, 70);
            }
        }

        this.totalExpenditure = 0;
    }

    public int getTotalExpenditure(){
        return this.totalExpenditure;
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
        System.out.println("================== ITEMS FOR SALE ================");
        System.out.println("--------------------------------------------------");
        System.out.printf("| %-5s | %-25s | %-10s |\n", "No", "Item", "Harga");
        System.out.println("--------------------------------------------------");

        int idx = 1;
        for (Map.Entry<Item, Integer> entry : itemsForSale.entrySet()) {
            System.out.printf("| %-5d | %-25s | %-10d |\n", 
                idx, entry.getKey().getName(), entry.getValue());
            idx++;
        }
        System.out.println("--------------------------------------------------");
        System.out.println();
    }

    // Tampilkan daftar resep
    public void showRecipesForSale() {
        System.out.println("=============== RECIPES FOR SALE =================");
        System.out.println("--------------------------------------------------");
        System.out.printf("| %-5s | %-25s | %-10s |\n", "No", "Recipe", "Harga");
        System.out.println("--------------------------------------------------");
        
        System.out.printf("| %-5d | %-25s | %-10d |\n", 1, "Fish n' Chips", 70);
        System.out.printf("| %-5d | %-25s | %-10d |\n", 2, "Fish Sandwich", 70);
        
        System.out.println("--------------------------------------------------");
        System.out.println();
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
                this.totalExpenditure += totalPrice;
                return true;
            }
        }

        System.out.println("Item tidak ditemukan.");
        return false;
    }

    // Beli resep
    public boolean buyRecipe(Player player, String recipeName) {
        if (recipeName.equalsIgnoreCase("Fish n' Chips")) {
            int price = 70;
            if (player.getGold() < price) {
                System.out.println("Uangmu tidak cukup.");
                return false;
            }
            player.removeGold(price);

            // Unlocked: cari resepnya dulu dari semua resep
            for (Recipe r : RecipeManager.getAllRecipes()) {
                if (r.getRecipeName().equals("Fish n' Chips")) {
                    r.setUnlocked(true);
                    break;
                }
            }

            System.out.println("Kamu membeli resep Fish n' Chips");
            totalExpenditure += price;
            return true;
        } 
        else if (recipeName.equalsIgnoreCase("Fish Sandwich")) {
            int price = 70;
            if (player.getGold() < price) {
                System.out.println("Uangmu tidak cukup.");
                return false;
            }
            player.removeGold(price);

            for (Recipe r : RecipeManager.getAllRecipes()) {
                if (r.getRecipeName().equals("Fish Sandwich")) {
                    r.setUnlocked(true);
                    break;
                }
            }

            System.out.println("Kamu membeli resep Fish Sandwich");
            totalExpenditure += price;
            return true;
        }

        System.out.println("Resep tidak ditemukan.");
        return false;
    }



    public String getItemNameByIndex(int index){
        int idx = 1;
        for (Map.Entry<Item, Integer> entry : itemsForSale.entrySet()) {
            if(idx == index){
                return entry.getKey().getName();
            }
            idx++;
        }
        return null;
    }

    public String getRecipeNameByIndex(int index){
        int idx = 1;
        for (Map.Entry<Recipe, Integer> entry : recipesForSale.entrySet()) {
            if (idx == index) {
                return entry.getKey().getRecipeName();
            }
            idx++;
        }
        return null;
    }
}
