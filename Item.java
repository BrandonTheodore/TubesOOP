import java.util.ArrayList;
import java.util.List;
public abstract class Item {
    protected String name;
    protected int sellPrice;
    protected int buyPrice;
    protected ItemCategory category;

    public Item(String name, int buyP, int sellP, ItemCategory c){
        this.name = name;
        this.buyPrice = buyP;
        this.sellPrice = sellP;
        this.category = c;
    }

    public ItemCategory getCategory(){
        return category;
    }

    public String getName(){
        return name;
    }

    public int getBuyPrice(){
        return buyPrice;
    }

    public int getSellPrice(){
        return sellPrice;
    }

    public static List<Item> itemDijual(){
        List<Item> itemsDijual = new ArrayList<>();
        // FishManager fishlist = new FishManager();
        // for (Fish fish : fishlist.getAllFish()) {
        //     itemsDijual.add(fish);
        // }
        SeedsManager seedlist = new SeedsManager();
        for(Seeds seeds : seedlist.getAllSeeds()){
            itemsDijual.add(seeds);
        }
        CropsManager cropslist = new CropsManager();
        for(Crops crops : cropslist.getAllCrops()){
            if(crops.buyPrice != 0){
                itemsDijual.add(crops);
            }
        }
        FoodManager foodlist = new FoodManager();
        for(Food food : foodlist.getAllFood()){
            if(food.buyPrice != 0){
                itemsDijual.add(food);
            }
        }
        MiscManager misclist = new MiscManager();
        for(Misc misc : misclist.getAllMiscs()){
            itemsDijual.add(misc);
        }
        return itemsDijual;
    }

    public abstract void useItem(Player player, Item item);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Item other = (Item) obj;
        return this.name.equalsIgnoreCase(other.name); // atau pakai ID, dsb
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode(); // supaya konsisten dengan equals()
    }

    public final void play(){
        printItemStats();
    }

    abstract void printItemStats();
}
