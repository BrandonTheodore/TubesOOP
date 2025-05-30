
import java.util.List;
import java.util.Objects;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return name.equals(item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public final void play(){
        printItemStats();
    }

    abstract void printItemStats();
}
