public class Crops extends Item { // bingung crops mau extend item apa seeds
    // Semua crop yang telah dipanen tidak akan dapat dipanen lagi (berubah kembali menjadi soil)
    private int daysToHarvest; //day udh siap di harvest   
    private boolean isHarvestable;    
    // private Item resultItem;     // gapaham apani        
    private int growthStage;    
    private int jumlahCropPerPanen;         
    private static final int energyCrops = 3;  

    public Crops(String name, int buyPrice, int sellPrice, ItemCategory category, int daysToHarvest, int jumlahCropPerPanen, Item resultItem) {
        super(name, buyPrice, sellPrice, category);
        this.daysToHarvest = daysToHarvest;
        // this.resultItem = resultItem; // gapaham apani
        this.jumlahCropPerPanen = jumlahCropPerPanen;
        this.isHarvestable = false; 
        this.growthStage = 0; // biji
    }

    public boolean isReadyForHarvest(int daysPassed) {
        if (daysPassed >= daysToHarvest) {
            isHarvestable = true;
            return true;
        }
        return false;
    }

    public Item harvest() {
        if (isHarvestable) {
            isHarvestable = false; // Reset status setelah dipanen
            growthStage = 0; // Reset tahapan pertumbuhan
            return resultItem;
        } else {
            System.out.println("Tanaman belum siap dipanen.");
            return null;
        }
    }

    // kalo player makan crops energy nya nambah 3 
    public void useItem(Player player, Crops crop){
        player.getEnergy += energyCrops; // nambahin energi player
        return player.getEnergy(); // return current energy abis penambahan
    }

    
    public int sell(Store store) {
        // nambahin item crops ke store
        store.addItem(this); 
        return getSellPrice(); // harga jual item
    }

    public int getDaysToHarvest() {
        return daysToHarvest;
    }

    public boolean isHarvestable() {
        return isHarvestable;
    }

    public Item getResultItem() {
        return resultItem;
    }

    public int getSellPrice() {
        return sellPrice;
    }
}
