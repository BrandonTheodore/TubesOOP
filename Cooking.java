import java.util.*;

public class Cooking {
    // nanti tiap mau cook ambil dulu nama fuelnya yaa

    public boolean cook(Player player, Recipe recipe, String fuelName) {
        // cek lokasi player harus dirumah 

        // cek resep udah unlocked belum
        if (!recipe.isUnlocked()) {
            System.out.println("Resep masih terkunci");
            return false;
        }

        // cek fuel
        Item fuelItem = player.getInventory().getItemByName(fuelName);
        int qty = player.getInventory().getItemQuantity(fuelItem);

        if (fuelItem == null) {
            System.out.println("Fuel tidak ditemukan di inventory");
            return false;
        }

        if (fuelName.equalsIgnoreCase("Firewood")) {
            if (qty < 1) {
                System.out.println("Butuh 1 Firewood untuk memasak");
                return false;
            }

            player.getInventory().removeItem(fuelItem, 1);
        }
        
        else if (fuelName.equalsIgnoreCase("Coal")) {
            if (qty < 1) {
                System.out.println("Butuh 1 Coal untuk memasak");
                return false;
            }

            player.getInventory().removeItem(fuelItem, 1);
        }

        else {
            System.out.println("Fuel tidak valid");
            return false;
        }
        
        // cek bahan bahannya
        if (!recipe.hasRequiredItems(player)) {
            System.out.println("Ingredients tidak tercukupi");
            return false;
        }

        if (player.getEnergy() < (-10)) {
            System.out.println("Energi tidak cukup untuk memasak");
            return false;
        }
        return true;
    }
}