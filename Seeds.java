public class Seeds extends Item {
    private Season season;
    private int daysToHarvest; 
    private Crops resultCrop; 
    
    public Seeds(String name, int buyPrice, Season season, int daysToHarvest, Crops resultCrop) {
        // harga jual seed adalah setengah harga belinya
        super(name, buyPrice, buyPrice / 2, ItemCategory.SEED);
        this.season = season;
        this.daysToHarvest = daysToHarvest;
        this.resultCrop = resultCrop;
    }
    
    public Season getSeason() {
        return season;
    }
    
    public int getDaysToHarvest() {
        return daysToHarvest;
    }
    
    public Crops getResultCrop() {
        return resultCrop;
    }
    
    @Override
    public void useItem(Player player, Item item) {
        if (player != null) {
            Inventory inventory = player.getInventory();
            // cek ada di inventory gak
            if (inventory.checkItem(this)) {
                inventory.removeItem(this, 1);
                System.out.println("Player menanam " + name);
            } else {
                System.out.println("Kamu tidak memiliki " + name + " di inventory.");
            }
        }
    }
}