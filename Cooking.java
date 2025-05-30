import java.time.LocalTime;
import java.util.Map;

public class Cooking {
    public boolean cook(Player player, String recipeName, Misc fuelMiscItem, RecipeManager recipeManager, Time time) {
        RecipeManager.printUnlockedRecipes();        
        
        Recipe recipe = RecipeManager.getRecipeByName(recipeName);

        if (recipe == null) {
            System.out.println("Resep '" + recipeName + "' tidak ditemukan.");
            return false;
        }

        if (!recipe.isUnlocked()) {
            System.out.println("Resep '" + recipeName + "' masih terkunci (" + recipe.getUnlockInfo() + ").");
            return false;
        }

        if (fuelMiscItem == null) {
            System.out.println("Bahan bakar tidak valid.");
            return false;
        }
      
        // Cek fuelnya valid atau engga
        if (fuelMiscItem.getCategory() != ItemCategory.MISC ||
            (fuelMiscItem.getType() != MiscType.FIREWOOD && fuelMiscItem.getType() != MiscType.COAL)) {
            System.out.println(player.getName() + ": " + fuelMiscItem.getName() + " bukan bahan bakar yang valid (harus Firewood atau Coal).");
            return false;
        }

        if (!player.getInventory().checkItemAndQuantity(fuelMiscItem, 1)) {
            System.out.println(player.getName() + ": Anda tidak memiliki cukup " + fuelMiscItem.getName() + " di inventory.");
            return false;
        }

        // Cek energy player
        if (player.getEnergy() < 10) {
            System.out.println(player.getName() + ": Energi tidak cukup (" + player.getEnergy() + ") untuk memulai memasak.");
            return false;
        }

        // Tentukan portion berdasarkan fuel
        int portion = (fuelMiscItem.getType() == MiscType.COAL) ? 2 : 1;

        // Cek dan kurangin bahan
        Map<String, Integer> ingredients = recipe.getIngredients();
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            String name = entry.getKey();
            int quantity = entry.getValue() * portion;

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
                    System.out.println("Kamu tidak punya cukup " + name + " (butuh " + quantity + ").");
                    return false;
                }
                player.getInventory().removeItem(item, quantity);
            }
        }

        player.setEnergy(player.getEnergy() - 10);
        Runnable task = () -> {
            cookTimer(time);
        };
        Thread thread = new Thread(task);
        thread.start();
        player.getInventory().removeItem(fuelMiscItem, 1);
        player.getInventory().addItem(recipe.getFood(), portion);

        System.out.println(player.getName() + ": Memasak '" + recipeName + "' telah dimulai.");
        System.out.println("Energi berkurang 10. Sisa energi: " + player.getEnergy() + ".");
        System.out.println("Anda menerima " + portion + "x " + recipe.getFood().getName() + ".");
        System.out.println("Waktu berlalu 1 jam. Anda dapat melakukan aktivitas lain sekarang.");

        return true;
    }

    public void cookTimer(Time time){
        LocalTime waktuAwal = time.getCurrentGameTime();
        while(true){
            LocalTime waktuSekarang = time.getCurrentGameTime();
            long minutes = java.time.Duration.between(waktuAwal, waktuSekarang).toMinutes();
            if(minutes >= 60){
                break;
            }
        }
        
    }
}
