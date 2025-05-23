public class Misc extends Item {
    private MiscType type;
    // harga jual harus lebih murah dari harga beli 

    public Misc(String name, int buyPrice, int sellPrice, MiscType type, String description) {
        super(name, buyPrice, sellPrice, ItemCategory.MISC);
        this.type = type;
    }

    public MiscType getType() {
        return type;
    }

    public void useItem(Player player, Item item) {
        if (player != null) {
            Inventory inventory = player.getInventory();
            switch(type) {
                case COAL:
                    System.out.println("Player is currently using COAL.");
                    inventory.removeItem(this, 1);
                    break;
                case FIREWOOD:
                    System.out.println("Player is currently using FIREWOOD.");
                    inventory.removeItem(this, 1);
                    break;
                case PROPOSAL_RING:
                    System.out.println("Cie player mau melamar.");
                    return; // Don't remove the item from inventory
                default:
                    System.out.println("Player is using " + name );
                    inventory.removeItem(this, 1);
            }
        }
    }
}