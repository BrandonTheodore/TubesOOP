public class Food extends Item{
    private int energy;

    public Food(String name, int buyP, int sellP, ItemCategory c, int e){
        super(name, buyP, sellP, c);
        this.energy = e;
    }

    public void useItem(Player player, Food food){
        player.setEnergy(player.getEnergy() + food.energy);
    }
}

