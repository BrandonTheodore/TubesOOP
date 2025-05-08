import java.util.HashMap;
import java.util.Map;

public class ShippingBin {
    private final int maxSlot = 16;
    private Map<Item, Integer> bin;

    public ShippingBin(){
        this.bin = new HashMap<>();
    }

    public void addItem(Item item, int quantity, Player player) {
        // mungkin checkItem ngecheck itemnya ada di inventory sekaligus quantitynya
        if(item == null || quantity <= 0 || player.inventory.checkItem(item)){
            System.out.println("Invalid item or quantity");
            return;
        }

        if (this.bin.containsKey(item)) {
            player.inventory.removeItem(item, quantity);
            this.bin.put(item, this.bin.get(item) + quantity);
        } else {
            if (this.bin.size() < maxSlot) {
                player.inventory.removeItem(item, quantity);
                this.bin.put(item, quantity);
            } else {
                System.out.println("Bin is full!");
            }
        }
    }

    public void sellBin(Player player) {
        if(this.bin.isEmpty()){
            System.out.println("");
        }

        int totalSellPrice = 0;
        for (Map.Entry<Item, Integer> entry : this.bin.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            totalSellPrice += item.getSellPrice() * quantity;
        }

        // waktu uangnya dikirim abis tidur
        player.addGold(totalSellPrice);
        clearBin();
    }

    public void clearBin(){
        this.bin.clear();
    }

    public void PrintBin(){
        System.out.printf("%-15s %-10s\n", "Item", "Quantity");
        System.out.println("-----------------------------");

        int totalSellPrice = 0;
        for (Map.Entry<Item, Integer> entry : this.bin.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            totalSellPrice += item.getSellPrice() * quantity;
            System.out.printf("%-15s %-10s\n", item, quantity);
        }

        System.out.println("Total Sell Price: " + totalSellPrice);
    }
}