import java.util.ArrayList;
import java.util.List;

public class EquipmentManager {
    private List<Equipment> allEquipment;

    public EquipmentManager() {
        allEquipment = new ArrayList<>();
        allEquipment.add(new Equipment("Hoe", 0, 0, EquipmentType.HOE));
        allEquipment.add(new Equipment("Watering Can", 0, 0, EquipmentType.WATERING_CAN));
        allEquipment.add(new Equipment("Pickaxe", 0, 0, EquipmentType.PICKAXE));
        allEquipment.add(new Equipment("Fishing Rod", 0, 0, EquipmentType.FISHING_ROD));
    }

    // return semua nama equipment
    public List<String> getAllEquipmentNames() {
        List<String> equipmentNames = new ArrayList<>();
        for (Equipment equipment : allEquipment) {
            equipmentNames.add(equipment.getName());
        }
        return equipmentNames;
    }

    // return nama equipment as string
    public String getEquipmentNamesAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allEquipment.size(); i++) {
            sb.append(allEquipment.get(i).getName());
            if (i < allEquipment.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    // return semua equipment
    public List<Equipment> getAllEquipment() {
        return new ArrayList<>(allEquipment); // return copy dari array allEquipment
    }

    // get equipment berdasarkan nama
    public Equipment getEquipmentByName(String name) {
        for (Equipment equipment : allEquipment) {
            if (equipment.getName().equalsIgnoreCase(name)) {
                return equipment;
            }
        }
        return null;
    }

    // return equipment yang sedang di equip
    public List<Equipment> getEquippedEquipment() {
        List<Equipment> equippedEquipment = new ArrayList<>();
        for (Equipment equipment : allEquipment) {
            if (equipment.isEquipped()) {
                equippedEquipment.add(equipment);
            }
        }
        return equippedEquipment;
    }
}