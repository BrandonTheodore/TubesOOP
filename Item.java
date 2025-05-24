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

    public abstract void useItem(Player player, Item item);
}
