public class Crops extends Item {
    private static final int ENERGY_VALUE = 3;
    private int cropPerPanen;

    public Crops(String name, int buyPrice, int sellPrice, int cropPerPanen) {
        super(name, buyPrice, sellPrice, ItemCategory.CROPS);
        this.cropPerPanen = cropPerPanen;
    }

    public int getCropPerPanen() {
        return cropPerPanen;
    }
    
    @Override
    public void useItem(Player player, Item item) {
        if (player != null) {
            Inventory inventory = player.getInventory();
            if (inventory.checkItem(this)) { // cek item nya ada gak di inventory
                // nambahin energy player karena makan crops
                player.addEnergy(ENERGY_VALUE);
                // delete crops dr inventory abis dipake
                inventory.removeItem(this, 1);
                // print aja penanda player makan crops
                System.out.println( name + " dimakan player dan player gain " + ENERGY_VALUE);
            } 
            else {
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
        System.out.println("Crop(s) per panen: " + this.cropPerPanen);
    }
}