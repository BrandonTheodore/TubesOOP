import java.util.*;
import java.util.Map;

public class Cooking {
    public boolean cook(Player player, String recipeName, Misc fuelMiscItem, RecipeManager recipeManager, Time time) {
        Recipe recipe = RecipeManager.getRecipeByName(recipeName);
        Map<String, Integer> ingredients = recipe.getIngredients();

        if (recipe == null) {
            System.out.println(player.getName() + ": Resep '" + recipeName + "' tidak ditemukan.");
            return false;
        }
        if (!recipe.isUnlocked()) {
            System.out.println(player.getName() + ": Resep '" + recipeName + "' masih terkunci (" + recipe.getUnlockInfo() + ").");
            return false;
        }
        if (fuelMiscItem == null) {
            System.out.println(player.getName() + ": Bahan bakar tidak valid.");
            return false;
        }

        // Lokasi harus Home
        if (!player.location.getName().equalsIgnoreCase("Home")) {
            System.out.println(player.getName() + ": Anda harus berada di Rumah untuk memasak.");
            return false;
        }

        // Cek fuel valid
        if (fuelMiscItem.getCategory() != ItemCategory.MISC ||
            (fuelMiscItem.getType() != MiscType.FIREWOOD && fuelMiscItem.getType() != MiscType.COAL)) {
            System.out.println(player.getName() + ": " + fuelMiscItem.getName() + " bukan bahan bakar yang valid (harus Firewood atau Coal).");
            return false;
        }

        // Cek fuel ada
        if (!player.getInventory().checkItemAndQuantity(fuelMiscItem, 1)) {
            System.out.println(player.getName() + ": Anda tidak memiliki cukup " + fuelMiscItem.getName() + " di inventory.");
            return false;
        }

        // Cek energi
        if (player.getEnergy() < 10) {
            System.out.println(player.getName() + ": Energi tidak cukup (" + player.getEnergy() + ") untuk memulai memasak.");
            return false;
        }

        player.setEnergy(player.getEnergy() - 10);
        System.out.println(player.getName() + ": Memulai memasak '" + recipeName + "'. Energi berkurang 10. Sisa energi: " + player.getEnergy() + ".");
        time.addTime(60);

        // Cek dan buang bahan-bahan
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            String name = entry.getKey();
            int quantity = entry.getValue();

            if (name.equalsIgnoreCase("Any Fish")) {
                int totalFishUsed = 0;
                for (Map.Entry<Item, Integer> invEntry : player.getInventory().getInventory().entrySet()) {
                    Item inventoryItem = invEntry.getKey();
                    int invQty = invEntry.getValue();

                    if (inventoryItem.getCategory() == ItemCategory.FISH && invQty > 0) {
                        int toRemove = Math.min(quantity - totalFishUsed, invQty);
                        player.getInventory().removeItem(inventoryItem, toRemove);
                        totalFishUsed += toRemove;

                        if (totalFishUsed >= quantity) break;
                    }
                }

                if (totalFishUsed < quantity) {
                    System.out.println("Ikanmu tidak cukup untuk memasak resep ini.");
                    return false;
                }

            } else {
                Item item = RecipeManager.findItemByName(name);
                if (item == null || !player.getInventory().checkItemAndQuantity(item, quantity)) {
                    System.out.println("Kamu tidak punya cukup " + name + ".");
                    return false;
                }
                player.getInventory().removeItem(item, quantity);
            }
        }

        // Buang bahan bakar
        player.getInventory().removeItem(fuelMiscItem, 1);

        // 💡 Tambahkan makanan hasil sesuai fuel
        int foodQuantity = (fuelMiscItem.getType() == MiscType.COAL) ? 2 : 1;
        player.getInventory().addItem(recipe.getFood(), foodQuantity);

        System.out.println(player.getName() + ": Memasak '" + recipeName + "' selesai! Kamu dapat " + foodQuantity + "x " + recipe.getResultFood().getName() + ".");
        System.out.println("Kamu bisa melakukan aktivitas lain sekarang.");
        return true;
    }
}
