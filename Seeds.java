public class Seeds extends Item{
    // Seed hanya dapat ditanam pada musim yang sesuai.
    private Season season;
    // Harga jual seed adalah setengah harga belinya
    private int daysToHarvest;

    public Seeds(String name, int buyP, int sellP, ItemCategory c, Season s, int daysToHarvest){
        super(name, buyP, sellP, c);
        this.season = s;
        this.daysToHarvest = daysToHarvest;
    }

    public int getDaysToHarvest() {
        return daysToHarvest;
    }

    public void water(){
        daysToHarvest--;
    }

    public void useItem(Player player, Seeds seed){
        this.water();
    }
    
}
