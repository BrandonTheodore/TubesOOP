import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
    private House house;

    private int totalCropHarvested;
    private int totalFishCaught;
    private int totalHarvest;
    private boolean isMarried;
    private boolean haveCaughtLegendaryFish;
    private boolean slept;

    private int showerCount;
    private int bathCount;

    private ShippingBin shippingBin;
    private Seeds[][] seedMap;

    private static final int MAX_ENERGY = 100;
    private static final int MIN_ENERGY_BEFORE_SLEEP = -20;
    private static final double ENERGY_PENALTY_THRESHOLD_PERCENTAGE = 0.10;
    private static final int ENERGY_BONUS_IF_ZERO = 10;
    private static final int TILL_ENERGY_COST_PER_TILE = 5;
    private static final int RECOVER_ENERGY_COST_PER_TILE = 5;
    private static final int FISHING_ENERGY_COST = 5;
    private static final int MARRY_ENERGY_COST = 80;
    private static final int WATCHING_ENERGY_COST = 5;
    private static final int VISIT_ENERGY_COST = 10;


    public Player(String name, Gender gender, Farm farm, Time time, Location location) {

        this.name = name;
        this.gender = gender;
        this.energy = MAX_ENERGY;
        this.farm = farm;
        this.partner = null;
        this.gold = 0;
        this.inventory = new Inventory();
        this.time = time;
        this.house = new House();

        this.totalCropHarvested = 0;
        this.totalFishCaught = 0;
        this.totalHarvest = 0;
        this.isMarried = false;
        this.haveCaughtLegendaryFish = false;
        this.slept = true;

        this.showerCount = 0;
        this.bathCount = 0;
        
        this.location = location;
        this.shippingBin = new ShippingBin(time);

        this.seedMap = new Seeds[32][32];
    }

    public ShippingBin getShippingBin(){
        return shippingBin;
    }

    public Seeds getSeedFromSeedMap(int playerX, int playerY){
        return seedMap[playerY][playerX];
    }

    public boolean getHaveCaughtLegendaryFish(){
        return this.haveCaughtLegendaryFish;
    }

    public House getHouse(){
        return this.house;
    }

    public boolean getSlept(){
        return this.slept;
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

    public int getTotalCropHarvested(){
        return this.totalCropHarvested;
    }

    public int getTotalHarvest(){
        return this.totalHarvest;
    }

    public int getTotalFishCaught(){
        return this.totalFishCaught;
    }

    public boolean getIsMarried(){
        return this.isMarried;
    }

    public void resetShowerCount(){
        this.showerCount = 0;
    }

    public void reserBathCount(){
        this.bathCount = 0;
    }

    public void setSlept(boolean slept){
        this.slept = slept;
    }

    public void setGender(Gender gender) {
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
        if(this.energy <= 0){
            return false;
        }
        if(this.energy - energyCost < MIN_ENERGY_BEFORE_SLEEP){
            System.out.println("Jika melakukan aksi energi mencapai " + (this.energy-energyCost) + ", kurang dari cadangan energi (-20)");
            System.out.println(this.name + " tidak bisa melakukan aksi.");
            return false;
        }
        this.energy -= energyCost;
        System.out.println(this.name + " melakukan aksi. Energi berkurang " + energyCost + ". Sisa energi: " + this.energy);
        if(this.energy == -20){
            System.out.println("Energi mencapai -20, " + this.name + " sangat lelah, harus segera tidur;");
            sleep();
        }
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
    public void till(Map map) {
        if (consumeEnergy(5)) { 
            time.addTime(5);
            map.setCurrentTile('t');
            System.out.println("Till successful");
        } 
        else {
            System.out.println(this.name + " terlalu lelah untuk mengolah lebih banyak tanah.");
        }
    }

    public void recoverLand(Map map) {
        if (consumeEnergy(5)) {
            time.addTime(5);
            map.setCurrentTile('.');
            System.out.println("Recovered tilled land");
        } else {
            System.out.println(this.name + " terlalu lelah untuk memulihkan lebih banyak tanah.");
        }
    }

    public void cropProgress(int playerX, int playerY, Seeds seed, Map map) {
        int timer = seed.getDaysToHarvest();
        int i = 0;
        int lastWatered = 0;
        char[][] currentMap = map.getMap();
        while(i < timer){
            boolean first = true;
            while(true){
                LocalTime waktu = time.getCurrentGameTime();
                if(waktu.isAfter(LocalTime.of(00, 00)) && waktu.isBefore(LocalTime.of(6, 05)) && first){
                    // if(this.farm.getWeather() == Weather.RAINY){
                    //     rainWater(map, seed);
                    //     System.out.println("Its raining, all plants are watered");
                    // }

                    // if(prevTile == 'w'){
                    //     seed.setLastWatered(0);
                    // } else if (this.farm.getSeason() == Season.SUMMER && prevTile == 'l' && currentMap[playerY][playerX] == 'l'){
                    //     seed.incLastWatered();
                    // }

                    if(currentMap[playerY][playerX] == 'w'){
                        lastWatered = 0;
                    } else if (this.farm.getSeason() == Season.SUMMER){
                        lastWatered++;
                    }

                    map.setTile('l', playerX, playerY);

                    if(lastWatered > 2){
                        System.out.println("Plant has withered!");
                        seed.setStatus("Withered");
                        map.setTile('x', playerX, playerY);
                        return;
                    }
                    first = false;
                    i++;
                }
                if(waktu.isAfter(LocalTime.of(6, 05))){
                    break;
                }
            }
        }
        map.setTile('c', playerX, playerY);
    }

    public void plant(int playerX, int playerY, Seeds seed, Map map) {
        if(!this.inventory.checkItemAndQuantity(seed, 1)){
            System.out.println("You have no " + seed.getName() + " in your inventory!");
            return;
        }

        if(this.farm.getSeason() == Season.WINTER){
            System.out.println("Cannot plant in the winter");
            return;
        }

        if(this.farm.getSeason() != seed.getSeason()){
            System.out.println(seed.getName() + " can only be planted in " + seed.getSeason() + " season, now is " + this.farm.getSeason() + " season.");
            return;
        }

        Runnable task = () -> {
            cropProgress(playerX, playerY, seed, map);
        };

        if(consumeEnergy(5)){
            this.seedMap[playerY][playerX] = seed;
            this.time.addTime(5);
            this.inventory.removeItem(seed, 1);
            map.setCurrentTile('l');
            System.out.println("You planted a " + seed.getName() + ", " + seed.getDaysToHarvest() + " days to harvest.");
            System.out.println("Make sure to water it properly!");

            Thread thread = new Thread(task);
            thread.start(); 
        } else {
            System.out.println("Too tired to plant");
        }      
    }

    public void watering(int playerX, int playerY, Seeds seed, Map map) {
        if(consumeEnergy(5)){
            seed.setLastWatered(0);
            this.time.addTime(5);
            map.setCurrentTile('w');
        } else {
            System.out.println("Terlalu lelah untuk melakukan watering");
        }
    }

    public void harvest(int playerX, int playerY, Map map) {
        Seeds seedToHarvest = seedMap[playerY][playerX];
        if(consumeEnergy(5)){
            seedMap[playerY][playerX] = new Seeds("temp", 0, Season.FALL, 0, new Crops("temp", 0, 0, 0));
            this.time.addTime(5);
            this.inventory.addItem(seedToHarvest.getResultCrop(), seedToHarvest.getResultCrop().getCropPerPanen());
            this.totalCropHarvested += seedToHarvest.getResultCrop().getCropPerPanen();
            this.totalHarvest++;
            System.out.println(seedToHarvest.getResultCrop().getCropPerPanen() + " " + seedToHarvest.getResultCrop().getName() + " harvested");
            map.setCurrentTile('.');
        } else {
            System.out.println("Too tired to harvest " + seedToHarvest.getResultCrop().getName());
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
            time.addTime(5);
            System.out.println(this.name + " makan " + foodToEat.getName() + " dan mendapatkan " + energyGain + " energi.");
            return true;
        }
        return false;
    }

    public void sleep() {
        System.out.println("\n" + this.name + " tertidur pulas...");

        int energyBeforeSleep = this.energy;
        if (energyBeforeSleep == 0) {
            this.energy += MAX_ENERGY / 2 + ENERGY_BONUS_IF_ZERO;
            // Bonus jika energi persis 0 saat tidur
            // if (energyBeforeSleep == 0) {
            //     this.energy += MAX_ENERGY / 2 + ENERGY_BONUS_IF_ZERO;
            //     System.out.println(this.name + " tidur saat energi 0! Mendapatkan bonus energi +10. Energi: " + this.energy + ".");
            // }
            // else if (energyBeforeSleep < (MAX_ENERGY * ENERGY_PENALTY_THRESHOLD_PERCENTAGE)) {
            //     this.energy += MAX_ENERGY / 2;
            //     System.out.println(this.name + " tidur saat energi menipis (" + energyBeforeSleep + "). Energi terisi setengah: " + this.energy + ".");
            // } else {
            //     this.energy = MAX_ENERGY; 
            //     System.out.println(this.name + " tidur. Energi terisi penuh (" + this.energy + ")!");
            // }
        } else if (energyBeforeSleep < (MAX_ENERGY * ENERGY_PENALTY_THRESHOLD_PERCENTAGE)) {
            this.energy += MAX_ENERGY / 2;
            System.out.println(this.name + " tidur saat energi menipis (" + energyBeforeSleep + "). Energi terisi setengah: " + this.energy + ".");
        }
        else {
            // Kondisi normal: energi terisi penuh
            this.energy = MAX_ENERGY;
            System.out.println(this.name + " bangun dengan energi penuh (" + this.energy + ")!");
        }
        LocalTime skipTimeToMorning = LocalTime.of(6, 0);
        if((time.getCurrentGameTime().isAfter(LocalTime.of(6, 0)) && time.getCurrentGameTime().isBefore(LocalTime.of(23, 59))) || time.getCurrentGameTime().equals(LocalTime.of(23, 59))){
            // farm.setDay(farm.getDay().nextDay());
            // farm.setDayCount(farm.getDayCount() + 1);
            System.out.println("\nDay Changed");
            this.farm.setDay(farm.getDay().nextDay());
            this.farm.setDayCount(farm.getDayCount() + 1);
            resetShowerCount();
            reserBathCount();
        }
        time.setTime(skipTimeToMorning);
        farm.changeDay();
        this.farm.rainWater();
        System.out.println("Waktu game maju sampai pagi."); //harus ditambahin
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean fishing(Location location) {
        System.out.println(this.name + " mulai memancing...");

        // Konsumsi Energi dan Hentikan Waktu ---
        if (!consumeEnergy(FISHING_ENERGY_COST)) {
            System.out.println(this.name + " terlalu lelah untuk memancing.");
            return false; 
        }

        time.stopTime();

        Season currentSeason = farm.getSeason(); //ini apa dibawah2nya juga
        Weather currentWeather = farm.getWeather();
        farm.showTime();

        FishManager fishies = new FishManager();
        List<Fish> allFishies = fishies.getAllFish();
        List<Fish> catchableFishies = new ArrayList<>();
        for (Fish f : allFishies) {
            if (f.isCatchable(currentSeason, currentWeather, location, time.getCurrentGameTime())) {
                catchableFishies.add(f);
            }
        }

        if (catchableFishies.isEmpty()) {
            System.out.println(this.name + " memancing... tapi tidak ada ikan yang bisa ditangkap di kondisi ini. Anda hanya mendapatkan sampah!");
            this.inventory.addItem(new Misc("Sampah", 0, 0, MiscType.OTHER),1);
            time.addTime(15); 
            time.resumeTime();
            return true;
        }

        Random rand = new Random();
        Fish caughtFish = catchableFishies.get(rand.nextInt(catchableFishies.size())); // Pilih ikan secara acak

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
                scanner.nextLine();
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
        time.addTime(15);
        time.resumeTime();

        if (guessedCorrectly) {
            this.inventory.addItem(caughtFish, 1);
            System.out.println(this.name + " berhasil menangkap " + caughtFish.getName() + " (" + caughtFish.getFishRarity() + ")!");
            System.out.println("Harga jual: " + caughtFish.getSellPrice() + " gold.");

            if(caughtFish.getFishRarity() == Rarity.LEGENDARY){
                this.haveCaughtLegendaryFish = true;
            }

            this.totalFishCaught++;
            return true;
        } else {
            System.out.println("Anda gagal menebak angka. Ikan " + caughtFish.getName() + " berhasil lolos!");
            return false;
        }
    }

    public void propose(NPC npc) {
        System.out.println(this.name + " melamar " + npc.getName() + "!");
        
        // Ambil proposal ring dari MiscManager
        MiscManager miscManager = new MiscManager();
        Misc proposalRing = miscManager.getMiscByName("proposal ring");
        
        if (proposalRing == null) {
            System.out.println("Error: Proposal Ring tidak ditemukan di system.");
            return;
        }
        
        if (!this.inventory.checkItemByName("proposal ring")) {
            System.out.println(this.name + " tidak memiliki Proposal Ring di inventory.");
            return;
        }
        
        npc.receiveProposal(this);
    }

    public boolean marry(NPC npc) {
        if (!consumeEnergy(MARRY_ENERGY_COST)) {
            System.out.println(this.name + " terlalu lelah untuk menikah.");
            return false;
        }
        // System.out.println(this.name + " menikah dengan " + npc.getName() + "!");
        // npc.marryPlayer(this); 
        // this.setPartner(npc.getName()); 

        // System.out.println("Selamat, " + this.name + " dan " + npc.getName() + " resmi menikah!");
        // this.isMarried = true;
        npc.marryPlayer(this);
        return true;
    }

    public boolean watching() {
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

    public void visiting(Location location) {
        if (consumeEnergy(VISIT_ENERGY_COST)) {
            System.out.println(this.name + " mengunjungi " + location);
            time.addTime(15);
            System.out.println("Jalan 15 menit nih, cape juga.");
            this.location = location;
        } else {
            System.out.println(this.name + " kecapean buat ke " + location + " skip dulu dah.");
        }
    }

    public void chatting(NPC npc) {
        // convo di handle npc
        if (npc == null) {
            System.out.println("NPC tidak valid.");
        }
        npc.chatWith(this);
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


    public void openInventory() {
        System.out.println("\n--- Inventory " + this.name + " ---");
        this.inventory.printInventory(); 
        System.out.println("--------------------");
    }

    public void showTime() {
        farm.showTime();
    }

    public String showLocation() {
        return this.name + " berada di " + location.toString() + ".";
    }

    public void sleepAtTwo(){
        Runnable task = () -> {
            while (true) { 
                if(this.time.getCurrentGameTime().isBefore(LocalTime.of(3, 00)) && this.time.getCurrentGameTime().isAfter(LocalTime.of(2, 00))){
                    sleep();
                }
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }

    public void sleepAtTwoHouse(Cooking cooking, RecipeManager recipeManager, MiscManager miscManager){
        Runnable task = () -> {
            while (true) { 
                if(this.time.getCurrentGameTime().isBefore(LocalTime.of(3, 00)) && this.time.getCurrentGameTime().isAfter(LocalTime.of(2, 00))){
                    this.slept = false;
                    System.out.println("\nHarus segera pulang dan tidur, waktu sudah jam 02.00!");
                    this.time.stopTime();
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }

    public void chill(){
        addEnergy(15);
        this.time.addTime(30);
    }

    public void admirePlants(){
        addEnergy(1);
        this.time.addTime(1);
    }

    public void toilet1(){
        addEnergy(1);
        this.time.addTime(2);
    }   

    public boolean toilet2(){
        if(consumeEnergy(10)){
            this.time.addTime(20);
            return true;
        }
        return false;
    }

    public boolean shower(){
        if(this.showerCount < 2){
            addEnergy(10);
            this.time.addTime(20);
            this.showerCount++;
            return true;
        }
        return false;
    }

    public boolean bath(){
        if(this.bathCount < 1){
            addEnergy(30);
            this.time.addTime(120);
            this.bathCount++;
            return true;
        }
        return false;
    }

    public void smolEat(){
        addEnergy(1);
        this.time.addTime(1);
    }

    public boolean study(){
        if(consumeEnergy(20)){
            this.time.addTime(90);
            return true;
        }
        return false;
    }
}