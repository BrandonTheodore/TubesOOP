import java.util.HashMap;
import java.util.Map;

public class ShippingBin {
    private final int maxSlot = 16;
    private Map<Item, Integer> bin;

    public ShippingBin(){
        this.bin = new HashMap<>();
    }

    /**
     * add item to bin,
     * if already in bin add quantity
     */
    public void addItem(Item item, int quantity, Player player) {
        // mungkin checkItem ngecheck itemnya ada di inventory sekaligus quantitynya
        if(item == null || quantity <= 0 || player.getInventory().checkItem(item)){
            System.out.println("Invalid item or quantity");
            return;
        }

        if (this.bin.containsKey(item)) {
            player.getInventory().removeItem(item, quantity);
            this.bin.put(item, this.bin.get(item) + quantity);
        } else {
            if (this.bin.size() < maxSlot) {
                player.getInventory().removeItem(item, quantity);
                this.bin.put(item, quantity);
            } else {
                System.out.println("Bin is full!");
            }
        }
    }

    /**
     * add player gold,
     * clear bin
     */
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

    /**
     * clear bin
     */
    public void clearBin(){
        this.bin.clear();
    }

    /**
     * print shipping bin
     */
    public void printBin(){
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