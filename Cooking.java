public class Cooking {
    // nanti tiap mau cook ambil dulu nama fuelnya yaa

    public boolean cook(Player player, Recipe recipe, String fuelName) {
        // cek lokasi player
        if (!player.getLocation().equals("House")) {
            System.out.println("Player tidak berada di dapur");
            return false;
        }

        // cek resep udah unlocked belum
        if (!player.getUn)

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

    }


}
