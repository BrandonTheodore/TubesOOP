import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Item, Integer> items;

    // konstruktor
    public Inventory() {
        items = new HashMap<>();
        initStartingItems();
    }

    // berarti ini harusnya ada di player
    public Map<Item, Integer> getInventory() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }

    // method menambahkan item
    public void addItem(Item item, int quantity) {
        if (items.containsKey(item)) {
            items.put(item, items.get(item) + quantity);
        } else {
            items.put(item, quantity);
        }
    }

    // method menghapus item
    public boolean removeItem(Item item, int quantity) {
        // kalo itemnya ga ada
        if (!items.containsKey(item)) {
            return false; 
        }

        int currentQuantity = items.get(item);
        // kalo quantity yang mau dihapus lebih banyak dari yang player punya
        if (quantity > currentQuantity) {
            return false;
        }

        // kalo sama dengan
        if (currentQuantity == quantity) {
            items.remove(item);
        } else {
            items.put(item, currentQuantity - quantity);
        }
        return true;
    }

    // method untuk menggunakan item
    public Item getItem(Item item) {
        if (!items.containsKey(item)) {
            return null;
        } else {
            int currentQuantity = items.get(item);
            if (currentQuantity == 1) {
                items.remove(item);
            } else {
                items.put(item, currentQuantity - 1);
            }
            return item;
        }
    }

    // method untuk mendapatkan jumlah item
    public int getItemQuantity(Item item) {
        if (!items.containsKey(item)) {
            return 0;
        } else {
            return items.get(item);
        }
    }

    public Item getItemByName(String name) {
        for (Item item : items.keySet()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    // method untuk mengecek apakah sebuah item ada di inventory
    public boolean checkItem(Item item) {
        return items.containsKey(item);
    }

    // method buat check item dan jumlahnya
    public boolean checkItemAndQuantity(Item item, int quantity) {
        if (!items.containsKey(item)) {
            return false;
        } else {
            if (items.get(item) >= quantity) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean checkItemByName(String itemName) {
        for (Item item : items.keySet()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    private void initStartingItems() {
        SeedsManager seedsManager = new SeedsManager();

        // Tambahin seeds
        Seeds parsnipSeeds = seedsManager.getSeedsByName("Parsnip Seeds");
        if (parsnipSeeds != null) {
            addItem(parsnipSeeds, 15);
        }

        // Tambahin equipment awal
        EquipmentManager equipmentManager = new EquipmentManager();
        for (Equipment equipment : equipmentManager.getAllEquipment()) {
            addItem(equipment, 1);
        }

        // // Tambah fuel buat nyoba di main
        // Misc firewood = new Misc("Firewood", 0, 0, MiscType.FIREWOOD);
        // Misc coal = new Misc("Coal", 0, 0, MiscType.COAL);
        // addItem(firewood, 1);
        // addItem(coal, 1);
    }

    // method untuk menampilkan inventory
    public void printInventory() {
        if (items.isEmpty()) {
            System.out.println("Inventory kosong");
            return;
        }
        System.out.println("----------------------------+------------");
        System.out.printf("| %-25s | %-10s|\n", "Nama Item", "Jumlah");
        System.out.println("----------------------------+------------");
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            String name = entry.getKey().getName();
            int quantity = entry.getValue();
            System.out.printf("| %-25s | %-10d|\n", name, quantity);
        }
        System.out.println("----------------------------+------------");
    }
}