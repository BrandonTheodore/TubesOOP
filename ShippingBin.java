import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ShippingBin {
    private final int maxSlot = 16;
    private Map<Item, Integer> bin;
    private Time time;
    private int totalIncome;
    private int incomePerSale;
    private boolean hasBeenUsed;

    public ShippingBin(Time time){
        this.bin = new HashMap<>();
        this.time = time;
        this.totalIncome = 0;
        this.hasBeenUsed = false;
        this.incomePerSale = 0;
    }

    public int getTotalIncome(){
        return this.totalIncome;
    }

    /**
     * add item to bin,
     * if already in bin add quantity
     */
    public void addItem(Item item, int quantity, Player player) {
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
            this.incomePerSale += item.sellPrice*quantity;
            System.out.println("You have successfully added " + quantity + " " + item.getName() + " to your Shipping Bin!");
        } else {
            if (this.bin.size() < maxSlot) {
                player.getInventory().removeItem(item, quantity);
                this.bin.put(item, quantity);
                this.incomePerSale += item.sellPrice*quantity;
                System.out.println("You have successfully added " + quantity + " " + item.getName() + " to your Shipping Bin!");
            } else {
                System.out.println("Bin is full!");
            }
        }
    }

    /**
     * add player gold,
     * clear bin
     */
    public void sell(Player player) {
        if(this.hasBeenUsed){
            System.out.println("Shipping bin has been sold today!");
            return;
        }

        if(this.bin.isEmpty()){
            System.out.println("Shipping bin is empty!");
            return;
        }

        this.hasBeenUsed = true;
        System.out.println("Negotiating with the merchant...");
        System.out.println("Looking for buyers.. it usually takes a day");
        this.bin.clear();

        Runnable task = () -> {
            while(true){
                LocalTime currTime = time.getCurrentGameTime();
                if(currTime.isAfter(LocalTime.of(00, 00)) && currTime.isBefore(LocalTime.of(01, 00))){
                    player.addGold(this.incomePerSale);
                    System.out.println("You successfully sold your items for a total of " + this.incomePerSale + " gold!");
                    System.out.println("You now have " + player.getGold() + " gold!");
                    this.totalIncome += this.incomePerSale;
                    this.incomePerSale = 0;
                    this.hasBeenUsed = false;
                    time.addTime(15);
                    break;
                }
            }
        };

        Thread thread = new Thread(task);
        thread.start();
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