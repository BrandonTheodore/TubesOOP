package ShippingBin;

import java.util.HashMap;
import java.util.Map;

class ShippingBin {
    private Map<String, Integer> bin; // item and quantity in the bin
    private final int capacity = 16; // maximum amount of unique items in the bin

    // value of each items
    private Map<String, Integer> itemValue;

    public ShippingBin(){
        this.bin = new HashMap<String, Integer>();
    }

    public boolean isBinFull(Map<String, Integer> bin){
        return bin.size() >= capacity;
    }

    public void addItem(String item, Integer quantity) throws ItemNotFoundException {
        // item is null
        if(item == null){
            throw new NullPointerException("Item must have value (not null)");
        }

        // quantity is null
        if(quantity == null){
            throw new NullPointerException("Cannot add null amount of " + item);
        }

        // item is not in the game
        if(!itemValue.containsKey(item)){
            throw new ItemNotFoundException("There's no " + item + " in this game's items list");
        }

        // quantity is 0 or negative
        if(quantity <= 0){
            throw new IllegalStateException("Cannot add 0 or negative amount of items :(");
        }

        // PROGRAM
        // item is already in the bin, just add quantity
        if(bin.containsKey(item)){
            int currQuantity = bin.get(item);
            bin.put(item, currQuantity + quantity);
        } else { // item is not in the bin, add the item
            if(isBinFull(bin)){
                throw new IllegalStateException("Bin is full");
            }
            bin.put(item, quantity);
        }
    }

    public void removeItem(String item, Integer quantity) throws ItemNotFoundException {
        // item is null
        if(item == null){
            throw new NullPointerException("Item must have value (not null)");
        }

        // quantity is null
        if(quantity == null){
            throw new NullPointerException("Cannot remove null amount of " + item);
        }

        // item is not in the game
        if(!itemValue.containsKey(item)){
            throw new ItemNotFoundException("There's no " + item + " in this game's items list");
        }

        // quantity is 0 or negative
        if(quantity <= 0){
            throw new IllegalStateException("Cannot remove 0 or negative stuff :(");
        }

        // bin is empty
        if(bin.isEmpty()){
            throw new IllegalStateException("No vtubers to remove or bin already empty");
        }

        // item not in the bin, nothing to remove
        if(!(bin.containsKey(item))){
            throw new ItemNotFoundException("There's no item named " + item + " inside the bin");
        }

        // PROGRAM
        int currQuantity = bin.get(item);
        // quantity to remove is more than current quantity, resulting in negative quantities, cannot remove
        if(currQuantity < quantity){
            throw new IllegalStateException("There's only " + currQuantity + " " + item + " in the bin.");
        }

        // quantity to remove is the same as current quantity, remove item from the bin
        if(currQuantity == quantity){
            bin.remove(item);
        } else { // subtract the quantity of the item in the bin
            bin.put(item, currQuantity - quantity);
        }
    }

    public int calculatePrice(){
        // bin is empty
        if(bin.isEmpty()){
            throw new IllegalStateException("Bin is empty, nothing to sell");
        }

        int totalPrice = 0;

        for(Map.Entry<String, Integer> entry : bin.entrySet()) {
            String item = entry.getKey();
            int quantity = entry.getValue();
            
            int price = itemValue.get(item);

            totalPrice += price * quantity;
        }

        return totalPrice;
    }

    public void printShippingBin(){
        // bin is empty
        if(bin.isEmpty()){
            throw new IllegalStateException("Bin is Empty");
        }

        System.out.println("=== Shipping Bin ===");
        System.out.printf("%-15s | %s%n", "Item", "Quantity");
        System.out.println("-------------------------");

        for(Map.Entry<String, Integer> entry : bin.entrySet()){
            System.out.printf("%-15s | %d%n", entry.getKey(), entry.getValue());
        }
        System.out.println("Total bin price: " + calculatePrice() + " gold");
    }

    public static class ItemNotFoundException extends Exception {
        public ItemNotFoundException(String message){
            super(message);
        }
    }
}