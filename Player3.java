import java.util.ArrayList;
import java.util.List;

public class Player3 {

    public enum Gender {
        MALE, FEMALE
    }

    private String name;
    private Gender gender;
    private int energy;
    private String farmName;
    private String partner;
    private int gold;
    private List<String> inventory;
    private String location;

    // Constants
    private final int MAX_ENERGY = 100;
    private final int MIN_ENERGY = -20;

    public Player3(String name, Gender gender, String farmName) {
        this.name = name;
        this.gender = gender;
        this.farmName = farmName;
        this.energy = MAX_ENERGY;
        this.partner = null;
        this.gold = 0;
        this.inventory = new ArrayList<>();
        this.location = "Farm";
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public int getEnergy() {
        return energy;
    }

    public String getFarmName() {
        return farmName;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public int getGold() {
        return gold;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addGold(int amount) {
        this.gold += amount;
    }

    public void spendGold(int amount) {
        if (this.gold >= amount) {
            this.gold -= amount;
        } else {
            System.out.println("Not enough gold!");
        }
    }

    public void addItem(String item) {
        inventory.add(item);
    }

    public void removeItem(String item) {
        inventory.remove(item);
    }

    public void doAction(String actionName, int energyCost) {
        System.out.println(name + " is attempting to do " + actionName);
        this.energy -= energyCost;

        if (this.energy < MIN_ENERGY) {
            System.out.println("Energy too low! Gotta go to sleep...");
            sleep();
        } else if (this.energy < 0) {
            System.out.println("Warning: Low energy. Current energy: " + this.energy);
        } else {
            System.out.println("Action completed. Energy left: " + this.energy);
        }
    }

    public void sleep() {
        System.out.println(name + " is sleeping...");
        this.energy = MAX_ENERGY;
        System.out.println("Energy restored to: " + this.energy);
    }

    public void fishing() {
        int energyCost = 10;
        doAction("Fishing", energyCost);
        if (this.energy >= MIN_ENERGY) {
            addItem("Fish");
            System.out.println("You caught a fish!");
        }
    }

    // You can add more actions like move(), interact(), etc.
}
