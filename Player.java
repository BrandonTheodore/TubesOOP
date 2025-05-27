import java.time.LocalTime;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Player {
    private String name;
    private Gender gender;
    private int energy;
    private Farm farm;
    private String partner;
    private int gold;
    private Inventory inventory;
    private Location location;
    private FoodManager foodManager;
    private Equipment equippedTool;
    private boolean hasUsedShippingBinToday;
    private Time time;

    private static final int MAX_ENERGY = 100;
    private static final int MIN_ENERGY_BEFORE_SLEEP = -20;
    private static final double ENERGY_PENALTY_THRESHOLD_PERCENTAGE = 0.10;
    private static final int ENERGY_BONUS_IF_ZERO = 10;
    private static final int TILL_ENERGY_COST_PER_TILE = 5;
    private static final int RECOVER_ENERGY_COST_PER_TILE = 5;
    private static final int PLANT_ENERGY_COST_PER_SEED = 5;
    private static final int WATER_ENERGY_COST_PER_TILE = 5;
    private static final int HARVESTING_ENERGY_COST_PER_CROP = 5;
    private static final int FISHING_ENERGY_COST = 5;
    private static final int MARRY_ENERGY_COST = 80;
    private static final int VISIT_ENERGY_COST = 10;
    private static final int CHAT_ENERGY_COST = 3;


    public Player(String name, Gender gender, Farm farm, Time time, Location location) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama pemain tidak boleh kosong.");
        }
        if (gender == null) {
            throw new IllegalArgumentException("Jenis kelamin pemain tidak boleh null.");
        }
        if (farm.getName() == null || farm.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nama farm tidak boleh kosong.");
        }

        this.name = name;
        this.gender = gender;
        this.energy = MAX_ENERGY;
        this.farm = farm;
        this.partner = null;
        this.gold = 0;
        this.inventory = new Inventory();
        this.time = time; 
        this.hasUsedShippingBinToday = false;
        this.location = location;
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
    public Farm getFarm() {
        return farm;
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

    public Time getTime() {
        return time;
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

    public void setFarm(Farm farm) {
        if (farm.getName() == null || farm.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nama farm tidak boleh kosong.");
        }
        this.farm = farm;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public void setGold(int gold) {
        this.gold = Math.max(0, gold);
    }

    public void setInventory(Inventory inventory) {
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory tidak boleh null."); //msh bingung
        }
        this.inventory = inventory;
    }

    public void setLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Lokasi tidak boleh null."); //
        }
        this.location = location;
    }

    public void setTime(Time time) {
        if (time == null) {
            throw new IllegalArgumentException("Waktu tidak boleh null.");
        }
        this.time = time;
    }

    public boolean consumeEnergy(int energyCost) {
        if (energyCost < 0) {
            throw new IllegalArgumentException("Biaya energi tidak harus lebih dari 0.");
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


    //ACTIONS!!!!!
    public boolean till(int numTiles) {
        if (this.equippedTool == null || this.equippedTool.getType() != EquipmentType.HOE) {
            System.out.println("Gunakan HOE untuk mengolah tanah.");
            return false;
        }
        if (!(this.location instanceof FarmLocation)) {
            System.out.println("Pergi ke farm terlebih dahulu untuk mengolah tanah!");
            return false;
        }

        FarmLocation currentFarm = (FarmLocation) this.location;
        int actualTilesTilled = 0;

        for (int i = 0; i < numTiles; i++) {
            Tile tileToTill = currentFarm.findUntilledLandTile(); 
            if (tileToTill == null) {
                System.out.println(this.name + ": Tidak ada lagi tanah 'land' yang bisa diolah di sini.");
                break; 
            }

            int energyCost = TILL_ENERGY_COST_PER_TILE;

            if (consumeEnergy(energyCost)) { 
                System.out.println(this.name + " mengolah tile " + tileToTill.getCoordinates());
                tileToTill.setStatus(TileStatus.SOIL);
                actualTilesTilled++;
                time.addTime(5);

            } else {
                System.out.println(this.name + " terlalu lelah untuk mengolah lebih banyak tanah.");
                break;
            }
        }

        if (actualTilesTilled > 0) {
            System.out.println(this.name + " berhasil mengolah " + actualTilesTilled + " tile tanah.");
            return true;
        } else {
            System.out.println(this.name + " tidak berhasil mengolah tanah satupun.");
            return false;
        }
    }

    public boolean recoverLand(int numTiles) {
        if (this.equippedTool == null || this.equippedTool.getType() != EquipmentType.PICKAXE) {
            System.out.println("Gunakan Pickaxe untuk memulihkan tanah!");
            return false;
        }
        if (!(this.location instanceof FarmLocation)) {
            System.out.println("Pergi ke Farm terlebih dahulu untuk memulihkan tanah!");
            return false;
        }

        FarmLocation currentFarm = (FarmLocation) this.location;
        int actualTilesRecovered = 0;

        for (int i = 0; i < numTiles; i++) {
            Tile tileToRecover = currentFarm.findTileByStatus(TileStatus.SOIL);
            if (tileToRecover == null) {
                System.out.println(this.name + ": Tidak ada lagi soil yang bisa dipulihkan di sini.");
                break;
            }

            int energyCost = RECOVER_ENERGY_COST_PER_TILE;
            if (consumeEnergy(energyCost)) {
                System.out.println(this.name + " memulihkan tile " + tileToRecover.getCoordinates() + " dengan Pickaxe.");
                tileToRecover.setStatus(TileStatus.LAND); 
                tileToRecover.setPlantedCrop(null);
                actualTilesRecovered++;
                time.addTime(5);
            } else {
                System.out.println(this.name + " terlalu lelah untuk memulihkan lebih banyak tanah.");
                break;
            }
        }

        if (actualTilesRecovered > 0) {
            System.out.println(this.name + " berhasil memulihkan " + actualTilesRecovered + " tile tanah.");
            return true;
        } else {
            System.out.println(this.name + " tidak berhasil memulihkan tanah satupun.");
            return false;
        }
    }

    public boolean plant(Seeds seedToPlant, int numSeeds) {
        if (seedToPlant == null) {
            System.out.println(this.name + ": Tidak ada seed untuk ditanam.");
            return false;
        }
        if (seedToPlant.getCategory() != ItemCategory.SEED) {
            System.out.println(this.name + ": " + seedToPlant.getName() + " bukan kategori seed.");
            return false;
        }
        if (!(this.location instanceof FarmLocation)) {
            System.out.println(this.name + ": Anda hanya bisa menanam di Farm!");
            return false;
        }
        if (!this.inventory.checkItemAndQuantity(seedToPlant, numSeeds)) {
            System.out.println(this.name + ": Anda tidak memiliki cukup " + seedToPlant.getName() + " di inventory.");
            return false;
        }

        FarmLocation currentFarm = (FarmLocation) this.location;
        int actualSeedsPlanted = 0;

        for (int i = 0; i < numSeeds; i++) {
            Tile tileToPlantOn = currentFarm.findTileByStatus(TileStatus.SOIL); // Cari tile berstatus SOIL
            if (tileToPlantOn == null) {
                System.out.println(this.name + ": Tidak ada lagi tanah 'soil' yang siap tanam di sini.");
                break;
            }

            int energyCost = PLANT_ENERGY_COST_PER_SEED;
            if (consumeEnergy(energyCost)) {
                System.out.println(this.name + " menanam " + seedToPlant.getName() + " di tile " + tileToPlantOn.getCoordinates() + ".");
                tileToPlantOn.setStatus(TileStatus.PLANTED); 
                tileToPlantOn.setPlantedCrop(seedToPlant);
                this.inventory.removeItem(seedToPlant, 1);
                actualSeedsPlanted++;
                time.addTime(5);
            } else {
                System.out.println(this.name + " terlalu lelah untuk menanam lebih banyak seed.");
                break;
            }
        }

        if (actualSeedsPlanted > 0) {
            System.out.println(this.name + " berhasil menanam " + actualSeedsPlanted + " " + seedToPlant.getName() + ".");
            return true;
        } else {
            System.out.println(this.name + " tidak berhasil menanam seed satupun.");
            return false;
        }
    }

    public boolean watering(int numTiles) {
        if (this.equippedTool == null || this.equippedTool.getType() != EquipmentType.WATERING_CAN) {
            System.out.println("Gunakan Watering Can untuk menyiram tanaman!");
            return false;
        }
        if (!(this.location instanceof FarmLocation)) {
            System.out.println("Pergi ke Farm terlebih dahulu untuk menyiram tanaman!");
            return false;
        }

        FarmLocation currentFarm = (FarmLocation) this.location;
        int actualTilesWatered = 0;

        for (int i = 0; i < numTiles; i++) {
            Tile tileToWater = currentFarm.findTileByStatus(TileStatus.PLANTED);
            if (tileToWater == null) {
                System.out.println(this.name + ": Tidak ada lagi tanah 'planted' yang bisa disiram di sini.");
                break;
            }

            int energyCost = WATER_ENERGY_COST_PER_TILE;
            if (consumeEnergy(energyCost)) {
                System.out.println(this.name + " menyiram tile " + tileToWater.getCoordinates() + ".");
                tileToWater.setStatus(TileStatus.WATERED);
                actualTilesWatered++;
                time.addTime(5);
            } else {
                System.out.println(this.name + " terlalu lelah untuk menyiram lebih banyak tanah.");
                break;
            }
        }

        if (actualTilesWatered > 0) {
            System.out.println(this.name + " berhasil menyiram " + actualTilesWatered + " tile.");
            return true;
        } else {
            System.out.println(this.name + " tidak berhasil menyiram tanah satupun.");
            return false;
        }
    }

    public boolean harvest(int numCrops) {
        if (!(this.location instanceof FarmLocation)) {
            System.out.println("Pergi ke farm untuk memanen!");
            return false;
        }
        FarmLocation currentFarm = (FarmLocation) this.location;
        int actualCropsHarvested = 0;

        for (int i = 0; i < numCrops; i++) {
            Tile tileToHarvest = currentFarm.findHarvestableTile();
            if (tileToHarvest == null) {
                System.out.println("Tidak ada lagi tanaman yang siap panen di sini.");
                break;
            }

            int energyCost = HARVESTING_ENERGY_COST_PER_CROP;
            if (consumeEnergy(energyCost)) {
                System.out.println(this.name + " memanen tanaman dari tile " + tileToHarvest.getCoordinates() + ".");
                
                // Dapatkan jenis hasil panen dari seed yang ditanam
                Seeds plantedSeed = tileToHarvest.getPlantedSeed();
                if (plantedSeed == null) {
                    System.out.println("Error: Tile seharusnya punya seed tapi tidak ada.");
                    tileToHarvest.setStatus(TileStatus.SOIL);
                    return false;
                }
                
                Item harvestedCrop = plantedSeed.getCropType();
                this.inventory.addItem(harvestedCrop, 1);
                System.out.println("Hasil panen " + harvestedCrop.getName() + " ditambahkan ke inventory.");
                time.addTime(5);

                tileToHarvest.setStatus(TileStatus.SOIL);
                tileToHarvest.setPlantedSeed(null);
                actualCropsHarvested++;
            } else {
                System.out.println(this.name + " terlalu lelah untuk memanen lebih banyak.");
                break;
            }
        }

        if (actualCropsHarvested > 0) {
            System.out.println(this.name + " berhasil memanen " + actualCropsHarvested + " tanaman.");
            return true;
        } else {
            System.out.println(this.name + " tidak berhasil memanen tanaman satupun.");
            return false;
        }
    }

    public boolean eat(Item food) {
        int quantityToConsume = 1;

        if (food == null) {
            System.out.println("Makanannya gaada nihh...");
            return false;
        }

        if (!(food instanceof Food) || food.getCategory() != ItemCategory.FOOD) {
            System.out.println(this.name + " tidak bisa makan " + food.getName() + " (bukan makanan).");
            return false;
        }

        Food foodToEat = (Food) food;

        if (!this.inventory.checkItemAndQuantity(foodToEat, quantityToConsume)) {
            System.out.println(this.name + " tidak memiliki cukup " + foodToEat.getName() + " di inventory.");
            return false;
        }

        int energyGain = foodToEat.getEnergyValue();

        if (this.inventory.removeItem(foodToEat, quantityToConsume)) {
            addEnergy(energyGain);
            System.out.println(this.name + " makan " + foodToEat.getName() + " dan mendapatkan " + energyGain + " energi.");
            return true;
            time.addTime(5);
        }
        return false;
    }

    public void sleep() {
        System.out.println(this.name + " tertidur pulas...");

        int energyBeforeSleep = this.energy;

        if (energyBeforeSleep <= 0) { 
            // Bonus jika energi persis 0 saat tidur
            if (energyBeforeSleep == 0) {
                this.energy = MAX_ENERGY + ENERGY_BONUS_IF_ZERO;
                System.out.println(this.name + " tidur saat energi 0! Mendapatkan bonus energi +10. Energi: " + this.energy + ".");
            }
            else if (energyBeforeSleep < (MAX_ENERGY * ENERGY_PENALTY_THRESHOLD_PERCENTAGE)) {
                this.energy = MAX_ENERGY / 2;
                System.out.println(this.name + " tidur saat energi menipis (" + energyBeforeSleep + "). Energi terisi setengah: " + this.energy + ".");
            } else {
                this.energy = MAX_ENERGY; 
                System.out.println(this.name + " tidur. Energi terisi penuh (" + this.energy + ")!");
            }
        } else if (energyBeforeSleep < (MAX_ENERGY * ENERGY_PENALTY_THRESHOLD_PERCENTAGE)) {
            this.energy = MAX_ENERGY / 2;
            System.out.println(this.name + " tidur saat energi menipis (" + energyBeforeSleep + "). Energi terisi setengah: " + this.energy + ".");
        }
        else {
            // Kondisi normal: energi terisi penuh
            this.energy = MAX_ENERGY;
            System.out.println(this.name + " bangun dengan energi penuh (" + this.energy + ")!");
        }
        LocalTime skipTimeToMorning = LocalTime.of(6, 0);
        time.setTime(skipTimeToMorning);
        System.out.println("Waktu game maju sampai pagi."); //harus ditambahin
    }



    public boolean fishing() {
        System.out.println(this.name + " mulai memancing...");

        // --- 1. Validasi Lokasi Memancing ---
        LocationFish currentLocationFish = null;
        boolean isValidFishingLocation = false;

        // Cek jika di FarmLocation dan dekat Pond (jarak 1 tile dari Pond)
        if (this.location instanceof FarmLocation) {
            FarmLocation currentFarm = (FarmLocation) this.location;
            currentLocationFish = LocationFish.POND;
            isValidFishingLocation = true;
            System.out.println("Memancing di Pond (di dalam Farm).");
        } else if (this.location.getName().equalsIgnoreCase("Mountain Lake") ||
                   this.location.getName().equalsIgnoreCase("Forest River") ||
                   this.location.getName().equalsIgnoreCase("Ocean")) {
            if (this.location.getName().equalsIgnoreCase("Mountain Lake")) {
                currentLocationFish = LocationFish.LAKE;
            } else if (this.location.getName().equalsIgnoreCase("Forest River")) {
                currentLocationFish = LocationFish.RIVER;
            } else if (this.location.getName().equalsIgnoreCase("Ocean")) {
                currentLocationFish = LocationFish.OCEAN;
            }
            isValidFishingLocation = true;
            System.out.println("Memancing di " + this.location.getName() + ".");
        }

        if (!isValidFishingLocation || currentLocationFish == null) {
            System.out.println(this.name + ": Anda tidak berada di lokasi memancing yang valid.");
            return false;
        }

        // --- 2. Konsumsi Energi dan Hentikan Waktu ---
        if (!consumeEnergy(FISHING_ENERGY_COST)) {
            System.out.println(this.name + " terlalu lelah untuk memancing.");
            return false; 
        }

        time.stopTime();

        Season currentSeason = time.getCurrentSeason(); //ini apa dibawah2nya juga
        Weather currentWeather = time.getCurrentWeather();
        String currentTime = time.getCurrentTime();

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
            this.inventory.addItem(new Item("Sampah", 1, 1, ItemCategory.OTHER), 1);
            time.addTime(15); 
            time.resumeTime();
            return true;
        }

        Random rand = new Random();
        Fish caughtFish = catchableFish.get(rand.nextInt(catchableFish.size())); // Pilih ikan secara acak

        System.out.println("Anda merasakan tarikan kuat! Ini mungkin " + caughtFish.getName() + " (" + caughtFish.getFishRarity() + ")!");

        //tebak angka
        int maxAttempts;
        int maxRandomNumber;

        switch (caughtFish.getFishRarity()) {
            case COMMON:
                maxAttempts = 10;
                maxRandomNumber = 10;
                break;
            case REGULAR:
                maxAttempts = 10;
                maxRandomNumber = 100;
                break;
            case LEGENDARY:
                maxAttempts = 7;
                maxRandomNumber = 500;
                break;
            case UNCOMMON: maxAttempts = 10; maxRandomNumber = 50; break;
            case RARE: maxAttempts = 8; maxRandomNumber = 200; break;
            case EPIC: maxAttempts = 7; maxRandomNumber = 400; break;
            default:
                maxAttempts = 10;
                maxRandomNumber = 10;
        }

        int targetNumber = rand.nextInt(maxRandomNumber) + 1;
        Scanner scanner = new Scanner(System.in); 

        System.out.println("Mini-game: Tebak angka antara 1 dan " + maxRandomNumber + ". Anda punya " + maxAttempts + " percobaan.");
        boolean guessedCorrectly = false;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            System.out.print("Percobaan " + attempt + "/" + maxAttempts + ". Masukkan tebakan Anda: ");
            int guess;
            try {
                guess = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Masukkan angka.");
                scanner.next(); 
                attempt--;
                continue;
            }

            if (guess == targetNumber) {
                System.out.println("Selamat! Anda berhasil menebak angka dengan benar!");
                guessedCorrectly = true;
                break;
            } else if (guess < targetNumber) {
                System.out.println("Tebakan Anda terlalu rendah.");
            } else {
                System.out.println("Tebakan Anda terlalu tinggi.");
            }
        }
        time.addTime(FISHING_TIME_COST_MINUTES); 
        time.resumeTime(); 

        if (guessedCorrectly) {
            this.inventory.addItem(caughtFish, 1);
            System.out.println(this.name + " berhasil menangkap " + caughtFish.getName() + " (" + caughtFish.getFishRarity() + ")!");
            System.out.println("Harga jual: " + caughtFish.getValue() + " gold.");
            return true;
        } else {
            System.out.println("Anda gagal menebak angka. Ikan " + caughtFish.getName() + " berhasil lolos!");
            return false;
        }
    }

    public boolean propose(NPC npc) {
        Misc proposalRing = new Misc("Proposal Ring", 1000, 1000, MiscType.PROPOSAL_RING, "A ring for proposal.");
        int quantityOfRing = 1;

        System.out.println(this.name + " melamar " + npc.getName() + "!");

        npc.receiveProposal(this);
    }

    public boolean marry(NPC npc) {
        if (!consumeEnergy(MARRY_ENERGY_COST)) {
            System.out.println(this.name + " terlalu lelah untuk menikah.");
            return false;
        }

        // // --- Validasi Prasyarat Pernikahan ---
        // if (this.partner == null || !this.partner.equals(npc.getName())) {
        //     System.out.println(this.name + " tidak bertunangan dengan " + npc.getName() + ".");
        //     return false;
        // }
        // if (npc.getRelationshipStatus() != NPC.RelationshipStatus.FIANCE) {
        //     System.out.println(this.name + ": " + npc.getName() + " belum berstatus fiance.");
        //     return false;
        // }
        // if (!npc.readyForMarriage()) {
        //     System.out.println(this.name + ": " + npc.getName() + " belum siap menikah (perlu minimal " + NPC.MIN_DAYS_AS_FIANCE_FOR_MARRIAGE + " hari setelah tunangan). Hari ini adalah hari ke-" + npc.getDaysSinceFiance() + ".");
        //     return false;
        // }

        System.out.println(this.name + " menikah dengan " + npc.getName() + "!");
        npc.marryPlayer(this); 
        this.setPartner(npc.getName()); 

        // LocalTime skipTimeToNight = LocalTime.of(22, 0);
        // time.setTime(skipTimeToNight);
        this.setLocation(new Location("Home")); 
        // System.out.println(this.name + " dan " + npc.getName() + " menghabiskan waktu bersama. Anda kembali ke " + this.location.getName() + " pada pukul 22.00.");

        System.out.println("Selamat, " + this.name + " dan " + npc.getName() + " resmi menikah!");
        return true;
    }

    public boolean watching() {
        if (!this.location.getName().equalsIgnoreCase("Home")) {
            System.out.println(this.name + ": Anda hanya dapat menonton televisi di Rumah.");
            return false;
        }
    
        if (consumeEnergy(WATCHING_ENERGY_COST)) {
            System.out.println(this.name + " menonton TV.");

            time.addTime(15);
            System.out.println("Anda telah menonton selama 15 menit.");

            return true;
        } else {
            System.out.println(this.name + " terlalu lelah untuk menonton TV.");
            return false;
        }
    }

    public boolean visiting(Location newLocation) {
        if (newLocation == null) {
            System.out.println(this.name + ": Lokasi yang ingin dikunjungi tidak valid.");
            return false;
        }

        if (newLocation instanceof FarmLocation) {
            System.out.println(this.name + ": Anda tidak bisa menggunakan 'visit' untuk pergi ke Farm.");
            return false;
        }
        
        if (this.location.equals(newLocation)) {
            System.out.println(this.name + " sudah berada di " + newLocation.getName() + ".");
        }

        if (consumeEnergy(VISIT_ENERGY_COST)) {
            System.out.println(this.name + " mengunjungi " + newLocation.getName() + " dari " + this.location.getName() + ".");
            this.setLocation(newLocation);

            time.addTime(15);
            System.out.println("Jalan 15 menit nih, cape juga.");

            return true;
        } else {
            System.out.println(this.name + " kecapean buat ke " + newLocation.getName() + "skip dulu dah.");
            return false;
        }
    }

    public boolean chatting(NPC npc) {
        if (npc == null) {
            System.out.println("NPC tidak valid.");
            return false;
        }

        if (npc.getHomeLocation() == null || !this.location.equals(npc.getHomeLocation())) {
            System.out.println("Anda hanya dapat mengobrol dengan " + npc.getName() + " di rumahnya (" + (npc.getHomeLocation() != null ? npc.getHomeLocation().getName() : "lokasi tidak diketahui") + "). Anda saat ini di " + this.location.getName() + ".");
            return false;
        }
        chatWith(this);
    }

    public boolean gifting(NPC npc, Item item) {
        if (npc == null) {
        System.out.println(this.name + ": NPC tidak valid.");
        return false;
        }
        if (item == null) {
            System.out.println(this.name + ": Item tidak valid.");
            return false;
        }
        if (!isAtNPCHome(npc)) {
            System.out.println("Kamu lagi gak dirumah NPC :(");
            return false;
        }

        if (!this.inventory.checkItem(item)) {
            System.out.println(this.name + ": Anda tidak memiliki " + item.getName() + " di inventory.");
            return false;
        }

        int energyCost = 5;
        if (!consumeEnergy(energyCost)) { // cek energy player cukup gak
            System.out.println(this.name + " terlalu lelah untuk memberikan hadiah.");
            return false;
        }
        // heartpoints di handle npc
        npc.receiveGift(this, item);
        
        System.out.println(this.name + " memberikan " + item.getName() + " kepada " + npc.getName() + ".");
        
        return true;
    }

    public void moving(Direction direction) {
        System.out.println(this.name + " mencoba bergerak ke arah: " + direction + ".");

        String oldLocName = this.location.getName(); 
        String newLocName = oldLocName;

        if (direction == Direction.UP) {
            newLocName = "North of " + oldLocName;
        } else if (direction == Direction.DOWN) {
            newLocName = "South of " + oldLocName;
        } else if (direction == Direction.LEFT) {
            newLocName = "West of " + oldLocName;
        } else if (direction == Direction.RIGHT) {
            newLocName = "East of " + oldLocName;
        } else {
            System.out.println("Arah tidak valid.");
            return;
        }
        this.location.setName(newLocName); 
        System.out.println(this.name + " pindah ke " + this.location.getName() + ".");
    }

    public void openInventory() {
        System.out.println("\n--- Inventory " + this.name + " ---");
        this.inventory.printInventory(); 
        System.out.println("--------------------");
    }

    public String showTime() {
        farm.showTime();
    }

    public String showLocation() {
        return this.name + " berada di " + location.toString() + ".";
    }

    public boolean selling(Item item, int quantity, ShippingBin shippingBin, Farm farm) {
        if (item == null) {
            System.out.println(this.name + ": Item tidak valid untuk dijual.");
            return false;
        }
    
        if (quantity <= 0) {
            System.out.println(this.name + ": Jumlah item yang dijual harus lebih dari 0.");
            return false;
        }

        if (!isNearShippingBin()) {
            System.out.println(this.name + ": Anda harus berada di dekat shipping bin untuk menjual item.");
            return false;
        }

        if (hasUsedShippingBinToday) {
            System.out.println(this.name + ": Anda sudah menjual item hari ini. Tunggu sampai esok hari.");
            return false;
        }


        if (!this.inventory.checkItemAndQuantity(item, quantity)) {
            System.out.println(this.name + ": Anda tidak memiliki cukup " + item.getName() + " di inventory.");
            return false;
        }

        // harus punya sell price > 0
        if (item.getSellPrice() <= 0) {
            System.out.println(this.name + ": " + item.getName() + " tidak bisa dijual.");
            return false;
        }

        System.out.println(this.name + " memasukkan " + quantity + " " + item.getName() + " ke shipping bin.");

        // stop time buat selling
        time.stopTime();

        // pake shipping bin buat nambahin item yg udh valid dijual
        shippingBin.addItem(item, quantity, this);

        System.out.println("Item berhasil dimasukkan ke shipping bin. Item akan dijual pada malam hari.");
        System.out.println("Perkiraan pendapatan: " + (item.getSellPrice() * quantity) + " gold.");

        // ubah boolean penggunaan shippingbin jadi true
        hasUsedShippingBinToday = true;

        // waktu +15 menit abis jualan
        time.addTime(15);
        
        // Lanjutkan waktu
        time.resumeTime();

        System.out.println("Waktu game bertambah 15 menit karena proses penjualan.");
        return true;
    }

    private boolean isNearShippingBin() {
    // shipping bin ada di farm dan berjarak 1 petak dari rumah
        if (!(this.location instanceof FarmLocation)) {
            return false;
        }
        return true;
    }
}