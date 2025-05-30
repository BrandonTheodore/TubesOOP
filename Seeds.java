public class Seeds extends Item {
    private Season season;
    private int daysToHarvest; 
    private Crops resultCrop;
    private int lastWatered;
    private String status; 
    
    public Seeds(String name, int buyPrice, Season season, int daysToHarvest, Crops resultCrop) {
        // harga jual seed adalah setengah harga belinya
        super(name, buyPrice, buyPrice / 2, ItemCategory.SEED);
        this.season = season;
        this.daysToHarvest = daysToHarvest;
        this.resultCrop = resultCrop;
        this.lastWatered = 0;
        this.status = "Fresh";
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
    
    public Season getSeason() {
        return season;
    }

    public int getLastWatered(){
        return lastWatered;
    }
    
    public void setLastWatered(int water){
        this.lastWatered = water;
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

    @Override
    void printItemStats(){
        System.out.println("Name: " + this.name);
        System.out.println("Buy Price: " + this.buyPrice + "g");
        System.out.println("Sell Price: " + this.sellPrice + "g");
        System.out.println("Item Category: " + this.getCategory().toString());
        System.out.println("Season: " + this.season.toString());
        System.out.println("Days to Harvest: " + this.daysToHarvest);
        System.out.println("Result Crop: " + this.resultCrop.toString());
    }
}