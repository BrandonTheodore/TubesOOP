import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Player {
    public enum Gender {
        MALE, FEMALE
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private String name;
    private Gender gender;
    private int energy;
    private String farmName;
    private String partner;
    private int gold;
    private Inventory inventory;
    private Location location;

    private static final int MAX_ENERGY = 100;
    private static final int MIN_ENERGY_BEFORE_SLEEP = -20;

    public Player(String name, Gender gender, String farmName) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama pemain tidak boleh kosong.");
        }
        if (gender == null) {
            throw new IllegalArgumentException("Jenis kelamin pemain tidak boleh null.");
        }
        if (farmName == null || farmName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama farm tidak boleh kosong.");
        }

        this.name = name;
        this.gender = gender;
        this.energy = MAX_ENERGY;
        this.farmName = farmName;
        this.partner = null;
        this.gold = 0;
        this.inventory = new Inventory();
        this.location = new Location("Home");
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
    public int getGold() {
        return gold;
    }
    public Inventory getInventory() {
        return inventory;
    }
    public Location getLocation() {
        return location;
    }

    public void setGender(Gender gender) {
        if (gender == null) {
            throw new IllegalArgumentException("Jenis kelamin tidak boleh null.");
        }
        this.gender = gender;
    }

    public void setEnergy(int energy) {
        this.energy = Math.min(energy, MAX_ENERGY);
    }

    public void setFarmName(String farmName) {
        if (farmName == null || farmName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama farm tidak boleh kosong.");
        }
        this.farmName = farmName;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public void setGold(int gold) {
        this.gold = Math.max(0, gold);
    }

    public void setInventory(Inventory inventory) {
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory tidak boleh null.");
        }
        this.inventory = inventory;
    }

    public void setLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Lokasi tidak boleh null.");
        }
        this.location = location;
    }

    public boolean consumeEnergy(int energyCost) {
        if (energyCost < 0) {
            throw new IllegalArgumentException("Biaya energi tidak boleh negatif.");
        }
        this.energy -= energyCost;
        if (this.energy < MIN_ENERGY_BEFORE_SLEEP) {
            System.out.println(this.name + " kelelahan dan harus segera tidur! Energi mencapai " + this.energy);
            sleep();
            return false;
        }
        System.out.println(this.name + " melakukan aksi. Energi berkurang " + energyCost + ". Sisa energi: " + this.energy);
        return true;
    }

    public void addEnergy(int energyGain) {
        if (energyGain < 0) {
            throw new IllegalArgumentException("Penambahan energi tidak boleh negatif.");
        }
        this.energy = Math.min(this.energy + energyGain, MAX_ENERGY);
        System.out.println(this.name + " mendapatkan energi. Energi bertambah " + energyGain + ". Energi saat ini: " + this.energy);
    }

    public void addGold(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Jumlah gold yang ditambahkan tidak boleh negatif.");
        }
        this.gold += amount;
        System.out.println(this.name + " mendapatkan " + amount + " gold. Total gold: " + this.gold);
    }

    public boolean removeGold(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Jumlah gold yang dikurangi tidak boleh negatif.");
        }
        if (this.gold >= amount) {
            this.gold -= amount;
            System.out.println(this.name + " mengeluarkan " + amount + " gold. Sisa gold: " + this.gold);
            return true;
        } else {
            System.out.println("Gold " + this.name + " tidak cukup untuk mengurangi " + amount + ". Hanya punya " + this.gold);
            return false;
        }
    }

    public void sleep() {
        System.out.println(this.name + " tertidur pulas...");
        this.energy = MAX_ENERGY;
        System.out.println(this.name + " bangun dengan energi penuh (" + this.energy + ")!");
    }

    public boolean eat(Item food) {
        int quantityToConsume = 1;

        if (food == null) {
            System.out.println(this.name + " mencoba makan sesuatu yang tidak valid.");
            return false;
        }

        if (!(food instanceof Food)) {
            System.out.println(this.name + " tidak bisa makan " + food.getName() + " (bukan kategori makanan/Food).");
            return false;
        }

        Food foodToEat = (Food) food; 
        
        if (!this.inventory.checkItemAndQuantity(foodToEat, quantityToConsume)) {
            System.out.println(this.name + " tidak memiliki cukup " + foodToEat.getName() + " di inventory.");
            return false;
        }

        int energyGain = foodToEat.getEnergyGain(); 

        if (this.inventory.removeItem(foodToEat, quantityToConsume)) {
            addEnergy(energyGain);
            System.out.println(this.name + " makan " + foodToEat.getName() + " dan mendapatkan " + energyGain + " energi.");
            return true;
        }
        // else blok gaperlu karena removeItem sudah mencetak pesan jika gagal
        return false;
    }

    public void move(Direction direction) {
        int energyCost = 5;
        if (consumeEnergy(energyCost)) {
            System.out.println(this.name + " bergerak ke arah: " + direction + ".");
            String oldLoc = this.location.getName();
            if (direction == Player.Direction.UP) this.location.setName("North " + oldLoc);
            else if (direction == Player.Direction.DOWN) this.location.setName("South " + oldLoc);
            else if (direction == Player.Direction.LEFT) this.location.setName("West " + oldLoc);
            else if (direction == Player.Direction.RIGHT) this.location.setName("East " + oldLoc);
            System.out.println(this.name + " sekarang berada di: " + this.location.getName() + ".");
        } else {
            System.out.println(this.name + " terlalu lelah untuk bergerak ke " + direction + ".");
        }
    }

    public void visit(Location newLocation) {
        int energyCost = 10;
        if (this.location.equals(newLocation)) {
            System.out.println(this.name + " sudah berada di " + newLocation.getName() + ".");
            return;
        }

        if (consumeEnergy(energyCost)) {
            System.out.println(this.name + " mengunjungi " + newLocation.getName() + " dari " + this.location.getName() + ".");
            this.setLocation(newLocation);
        } else {
            System.out.println(this.name + " terlalu lelah untuk mengunjungi " + newLocation.getName() + ".");
        }
    }

    public boolean gift(NPC npc, Item item) {
        int energyCost = 5;
        int quantityToGift = 1; 
        if (consumeEnergy(energyCost)) {
            if (this.inventory.removeItem(item, quantityToGift)) {
                System.out.println(this.name + " memberikan " + item.getName() + " sebagai hadiah kepada " + npc.getName() + ".");
                npc.receiveGift(item);
                return true;
            } else {
                System.out.println(this.name + " tidak memiliki " + item.getName() + " di inventory (atau tidak cukup jumlahnya).");
                return false;
            }
        } else {
            System.out.println(this.name + " terlalu lelah untuk memberikan hadiah.");
            return false;
        }
    }

    public void chat(NPC npc) {
        int energyCost = 3;
        if (consumeEnergy(energyCost)) {
            System.out.println(this.name + " mengobrol dengan " + npc.getName() + ".");
        } else {
            System.out.println(this.name + " terlalu lelah untuk mengobrol.");
        }
    }

    public boolean propose(NPC npc) {
        int energyCost = 20;
        Item proposalRing = new Item("Proposal Ring", 1000);
        int quantityOfRing = 1;

        if (consumeEnergy(energyCost)) {
            if (this.inventory.checkItemAndQuantity(proposalRing, quantityOfRing)) {
                System.out.println(this.name + " melamar " + npc.getName() + "!");
                boolean accepted = npc.receiveProposal(this);

                if (accepted) {
                    this.inventory.removeItem(proposalRing, quantityOfRing);
                    System.out.println(npc.getName() + " menerima lamaran! Selamat " + this.name + ", Anda bertunangan dengan " + this.partner + "!");
                    return true;
                } else {
                    System.out.println(npc.getName() + " menolak lamaran Anda.");
                    return false;
                }
            } else {
                System.out.println(this.name + " tidak memiliki cincin lamaran (Proposal Ring) di inventory atau jumlahnya tidak cukup.");
                return false;
            }
        } else {
            System.out.println(this.name + " terlalu lelah untuk melamar.");
            return false;
        }
    }

    public boolean marry(NPC npc) {
        int energyCost = 30;
        if (consumeEnergy(energyCost)) {
            if (this.partner != null && this.partner.equals(npc.getName()) && npc.getRelationshipStatus() == NPC.RelationshipStatus.FIANCE) {

                System.out.println(this.name + " menikah dengan " + npc.getName() + "!");
                npc.marryPlayer(this);
                this.setPartner(npc.getName());
                System.out.println("Selamat, " + this.name + " dan " + npc.getName() + " resmi menikah!");
                return true;
            } else {
                System.out.println(this.name + " tidak dapat menikah dengan " + npc.getName() + ".");
                System.out.println("Status: " + (this.partner == null ? "Belum bertunangan." : "Bertunangan dengan " + this.partner + ".") + " NPC Relationship: " + npc.getRelationshipStatus());
                return false;
            }
        } else {
            System.out.println(this.name + " terlalu lelah untuk menikah.");
            return false;
        }
    }

    public boolean till() {
        int energyCost = 10;
        if (consumeEnergy(energyCost)) {
            System.out.println(this.name + " mengolah tanah di lokasi saat ini.");
            return true;
        } else {
            System.out.println(this.name + " terlalu lelah untuk mengolah tanah.");
            return false;
        }
    }

    public boolean recoverLand() {
        int energyCost = 15;
        if (consumeEnergy(energyCost)) {
            System.out.println(this.name + " memulihkan tanah.");
            return true;
        } else {
            System.out.println(this.name + " terlalu lelah untuk memulihkan tanah.");
            return false;
        }
    }

    public boolean harvest() {
        int energyCost = 8;
        int quantityHarvested = 1;
        if (consumeEnergy(energyCost)) {
            System.out.println(this.name + " memanen tanaman.");
            Item harvestedCrop = new Item("Hasil Panen", 10);
            this.inventory.addItem(harvestedCrop, quantityHarvested);
            System.out.println("Hasil panen ditambahkan ke inventory.");
            return true;
        } else {
            System.out.println(this.name + " terlalu lelah untuk memanen.");
            return false;
        }
    }

    public boolean fish() {
        int energyCost = 15;
        int quantityCaught = 1;
        System.out.println(this.name + " mulai memancing...");

        if (consumeEnergy(energyCost)) {
            Season currentSeason = Season.SPRING;
            Weather currentWeather = Weather.SUNNY;
            LocationFish currentLocationFish;
            if (this.location.getName().equalsIgnoreCase("River")) {
                currentLocationFish = LocationFish.RIVER;
            } else if (this.location.getName().equalsIgnoreCase("Lake")) {
                currentLocationFish = LocationFish.LAKE;
            } else if (this.location.getName().equalsIgnoreCase("Ocean")) {
                currentLocationFish = LocationFish.OCEAN;
            } else {
                currentLocationFish = LocationFish.POND;
            }
            String currentTime = "10:00"; //misal aja

            List<Fish> allPossibleFish = new java.util.ArrayList<>();
            allPossibleFish.add(new Fish("Ikan Mas", 0, Rarity.COMMON,
                Arrays.asList(Season.SPRING, Season.SUMMER), Arrays.asList(Weather.SUNNY, Weather.CLOUDY), Arrays.asList(LocationFish.POND, LocationFish.RIVER), "06:00", "18:00"));
            allPossibleFish.add(new Fish("Ikan Lele", 0, Rarity.REGULAR,
                Arrays.asList(Season.SPRING, Season.SUMMER, Season.AUTUMN), Arrays.asList(Weather.RAINY, Weather.CLOUDY), Arrays.asList(LocationFish.RIVER, LocationFish.LAKE), "18:00", "02:00"));
            allPossibleFish.add(new Fish("Ikan Tuna", 0, Rarity.RARE,
                Arrays.asList(Season.SUMMER, Season.AUTUMN), Arrays.asList(Weather.SUNNY), Arrays.asList(LocationFish.OCEAN), "09:00", "17:00"));
            allPossibleFish.add(new Fish("Ikan Salmon", 0, Rarity.EPIC,
                Arrays.asList(Season.AUTUMN), Arrays.asList(Weather.SUNNY, Weather.RAINY), Arrays.asList(LocationFish.RIVER), "08:00", "16:00"));
            allPossibleFish.add(new Fish("Legendary Fish", 0, Rarity.LEGENDARY,
                Arrays.asList(Season.WINTER), Arrays.asList(Weather.SNOWY), Arrays.asList(LocationFish.LAKE), "00:00", "23:59"));

            List<Fish> catchableFish = new java.util.ArrayList<>();
            for (Fish f : allPossibleFish) {
                if (f.isCatchable(currentSeason, currentWeather, currentLocationFish, currentTime)) {
                    catchableFish.add(f);
                }
            }

            if (catchableFish.isEmpty()) {
                System.out.println(this.name + " memancing... tapi tidak ada ikan yang bisa ditangkap di kondisi ini. Anda hanya mendapatkan sampah!");
                this.inventory.addItem(new Item("Sampah", 1), quantityCaught);
                return true;
            }

            Random rand = new Random();
            Fish caughtFish = catchableFish.get(rand.nextInt(catchableFish.size()));

            this.inventory.addItem(caughtFish, quantityCaught);
            System.out.println(this.name + " berhasil memancing dan mendapatkan " + caughtFish.getName() + " (" + caughtFish.getFishRarity() + ")!");
            System.out.println("Harga jual: " + caughtFish.getValue() + " gold.");
            return true;
        } else {
            System.out.println(this.name + " terlalu lelah untuk memancing.");
            return false;
        }
    }

    public void showInventory() {
        System.out.println("\n--- Inventory " + this.name + " ---");
        this.inventory.printInventory(); 
        System.out.println("--------------------");
    }
}