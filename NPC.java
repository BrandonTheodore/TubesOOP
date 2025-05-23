import java.util.*;

abstract class NPC {
    protected String name;
    protected int heartPoints;
    protected List<String> lovedItems;
    protected List<String> likedItems;
    protected List<String> hatedItems;
    protected String relationshipStatus;

    public NPC(String name) {
        this.name = name;
        this.heartPoints = 0;
        this.relationshipStatus = "single";
        this.lovedItems = new ArrayList<>();
        this.likedItems = new ArrayList<>();
        this.hatedItems = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getHeartPoints() {
        return heartPoints;
    }

    public void giveItem(String item) {
        if (lovedItems.contains(item)) {
            heartPoints += 10;
            System.out.println(name + " loved the " + item + "! (+10 heartPoints)");
        } else if (likedItems.contains(item)) {
            heartPoints += 5;
            System.out.println(name + " liked the " + item + ". (+5 heartPoints)");
        } else if (hatedItems.contains(item) || isItemHatedByDefault(item)) {
            heartPoints -= 5;
            System.out.println(name + " hated the " + item + "! (-5 heartPoints)");
        } else {
            System.out.println(name + " felt neutral about the " + item + ".");
        }

        if (heartPoints > 150) heartPoints = 150;
        if (heartPoints < 0) heartPoints = 0;
    }

    // Untuk karakter seperti Mayor Tadi atau Perry yang memiliki aturan khusus
    protected boolean isItemHatedByDefault(String item) {
        return false; // Default tidak ada pengecualian
    }

    public void setRelationshipStatus(String status) {
        this.relationshipStatus = status;
    }

    public String getRelationshipStatus() {
        return relationshipStatus;
    }
}

// Implementasi NPC Spesifik
class MayorTadi extends NPC {
    public MayorTadi() {
        super("Mayor Tadi");
        lovedItems.add("Legend");
        likedItems.addAll(Arrays.asList("Angler", "Crimsonfish", "Glacierfish"));
    }

    @Override
    protected boolean isItemHatedByDefault(String item) {
        return !lovedItems.contains(item) && !likedItems.contains(item);
    }
}

class Caroline extends NPC {
    public Caroline() {
        super("Caroline");
        lovedItems.addAll(Arrays.asList("Firewood", "Coal"));
        likedItems.addAll(Arrays.asList("Potato", "Wheat"));
        hatedItems.add("Hot Pepper");
    }
}

class Perry extends NPC {
    public Perry() {
        super("Perry");
        lovedItems.addAll(Arrays.asList("Cranberry", "Blueberry"));
        likedItems.add("Wine");
    }

    @Override
    protected boolean isItemHatedByDefault(String item) {
        return item.toLowerCase().contains("fish");
    }
}

class Dasco extends NPC {
    public Dasco() {
        super("Dasco");
        lovedItems.addAll(Arrays.asList("The Legends of Spakbor", "Cooked Pig's Head", "Wine", "Fugu", "Spakbor Salad"));
        likedItems.addAll(Arrays.asList("Fish Sandwich", "Fish Stew", "Baguette", "Fish n’ Chips"));
        hatedItems.addAll(Arrays.asList("Legend", "Grape", "Cauliflower", "Wheat", "Pufferfish", "Salmon"));
    }
}

class Emily extends NPC {
    public Emily() {
        super("Emily");
        likedItems.addAll(Arrays.asList("Catfish", "Salmon", "Sardine"));
        hatedItems.addAll(Arrays.asList("Coal", "Wood"));
    }

    @Override
    public void giveItem(String item) {
        if (item.toLowerCase().contains("seed")) {
            heartPoints += 10;
            System.out.println(name + " loved the " + item + "! (+10 heartPoints)");
        } else {
            super.giveItem(item);
        }
    }
}

class Abigail extends NPC {
    public Abigail() {
        super("Abigail");
        lovedItems.addAll(Arrays.asList("Blueberry", "Melon", "Pumpkin", "Grape", "Cranberry"));
        likedItems.addAll(Arrays.asList("Baguette", "Pumpkin Pie", "Wine"));
        hatedItems.addAll(Arrays.asList("Hot Pepper", "Cauliflower", "Parsnip", "Wheat"));
    }
}