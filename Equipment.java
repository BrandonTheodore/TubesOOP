public class Equipment extends Item {
    private EquipmentType type;
    private boolean isEquipped;

    public Equipment(String name, int buyPrice, int sellPrice, EquipmentType type) {
        super(name, buyPrice, sellPrice, ItemCategory.EQUIPMENT);
        this.type = type;
        this.isEquipped = false;
    }

    public EquipmentType getType() {
        return type;
    }

    public boolean isEquipped() {
        return isEquipped; // return true kalo lg equip
    }

    //setter buat nge equip certain equipment
    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
    }

    @Override
    public void useItem(Player player, Item item) {
        if (player != null) {
            switch(type) {
                case HOE:
                    break;
                case WATERING_CAN:
                    break;
                case PICKAXE:
                    break;
                case FISHING_ROD:
                    break;
                default:
                    System.out.println("Unknown equipment!");
            }
        }
    }
}