public class InventoryDriver {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        Item fish1 = new Fish("Salmon", 100, 100, ItemCategory.FISH, Rarity.REGULAR, 10);
        Item food1 = new Food("Pastel", 50, 50, ItemCategory.FOOD, 20);
        Item fish2 = new Fish("Cupang", 500, 200, ItemCategory.FISH, Rarity.COMMON, 50);

        inventory.addItem(fish1, 3);
        inventory.addItem(food1, 2);
        inventory.addItem(fish2, 1);

        System.out.println("Inventory Awal");
        inventory.printInventory();

        System.out.println("\nHapus 1 Pastel");
        inventory.removeItem(food1, 1);
        inventory.printInventory();

        // System.out.println("\nMakan 1 Salmon");
        // inventory.getItem(fish1);
        // inventory.printInventory();

        System.out.println("\nCek Item");
        if (inventory.checkItem(fish2)) {
            System.out.println("Kamu punya " + inventory.getItemQuantity(fish2) + " " + fish2.getName());
        } else {
            System.out.println("Tidak ditemukan.");
        }
    }
}
