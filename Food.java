public class Food extends Item {
    private int energyValue;

    public Food(String name, int buyPrice, int sellPrice, int energyValue) {
        super(name, buyPrice, sellPrice, ItemCategory.FOOD);
        this.energyValue = energyValue;
    }

    public int getEnergyValue() {
        return energyValue;
    }

    @Override
    public void useItem(Player player, Item item) {
        if (player != null) {
            Inventory inventory = player.getInventory();
            
            if (inventory.checkItem(this)) { //cek food nya ada di inventory ga
                player.addEnergy(energyValue); // kalo ada nambah energi
                
                inventory.removeItem(this, 1); // remove food yg dimakan dr inventory
                
                // print aja penanda player udah makan
                System.out.println("Consumed " + name + " and gained " + energyValue + " energy.");
            } 
            else{
                System.out.println(name + " is not available in your inventory.");
            }
        }
    }

    @Override
    void printItemStats(){
        System.out.println("Name: " + this.name);
        System.out.println("Buy Price: " + this.buyPrice + "g");
        System.out.println("Sell Price: " + this.sellPrice + "g");
        System.out.println("Item Category: " + this.getCategory().toString());
        System.out.println("Energy Value: " + this.energyValue);
    }
}