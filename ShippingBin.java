import java.util.HashMap;
import java.util.Map;

public class ShippingBin {
    private final int maxSlot = 16;
    private Map<Item, Integer> bin;
    private Time time;

    public ShippingBin(Time time){
        this.bin = new HashMap<>();
        this.time = time;
    }

    /**
     * add item to bin,
     * if already in bin add quantity
     */
    public void sellItem(Item item, int quantity, Player player) {
        // mungkin checkItem ngecheck itemnya ada di inventory sekaligus quantitynya
        if(item == null || !player.getInventory().checkItemByName(item.getName())){
            System.out.println("Invalid item name!");
            return;
        } else if (quantity <= 0){
            System.out.println("Quantity must be more than 0!");
            return;
        } else if (!player.getInventory().checkItemAndQuantity(item, quantity)) {
            System.out.println("You don't have enough " + item.getName() + " in your inventory!");
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
        time.addTime(15);
    }

    /**
     * add player gold,
     * clear bin
     */
    public void getGoldFromSale(Player player) {
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
        this.bin.clear();
    }

    /**
     * print shipping bin
     */
    public void printBin(){
        if(bin.isEmpty()){
            System.out.println("Shipping Bin is empty");
            return;
        }

        System.out.printf("%-15s %-10s\n", "Item", "Quantity");
        System.out.println("-----------------------------");

        int totalSellPrice = 0;
        for (Map.Entry<Item, Integer> entry : this.bin.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            totalSellPrice += item.getSellPrice() * quantity;
            System.out.printf("%-15s %-10s\n", item.getName(), quantity);
        }

        System.out.println("Total Sell Price: " + totalSellPrice);
    }
}