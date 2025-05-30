// import java.util.*;
// public class Main {
//     public static void main(String[] args){
//         MapDriver game = new MapDriver();
//         game.Play();
        
//     }
// }

import java.lang.*;
import java.util.*;

/**
 * Driver class for the updated Map implementation of a farming game.
 * This class demonstrates the functionality of the Map class
 * by allowing player movement and interaction with the game world.
 */
public class Main {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m"; 
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_CYAN = "\u001B[36m";

    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Do you want to play the game? (y/n) ");
        String inp = scanner.nextLine().toLowerCase();
        if(inp.equals("n")){
            System.out.println("bye");
            return;
        }

        System.out.println("Input your name (1 try only): ");
        String inputName = scanner.nextLine();

        Gender gender;

        while (true) {
            System.out.println("Input your gender (m/f): ");
            String inputGender = scanner.nextLine().toLowerCase();
            if(inputGender.equals("m")){
                // init player male
                gender = Gender.MALE;
                break;
            } else if(inputGender.equals("f")){
                // init player female 
                gender = Gender.FEMALE;
                break;
            } else {
                System.out.println("Gender must be Male(m)/Female(f)");
            }
        }

        System.out.println("Input your farm name: ");
        String inputFarmName = scanner.nextLine();
        System.out.println("");
        Time time = new Time();
        Farm farm = new Farm(inputFarmName, time);
        farm.runThread();
        Cooking cooking = new Cooking();
        Store store = new Store();
        Idle idle = new Idle(new Watering());
        Random rand = new Random();
        int randomNumber = rand.nextInt(1000);

        Player player = new Player(inputName, gender, farm, time, Location.FARM);
        player.addGold(9999);

        CropsManager cropsManager = new CropsManager();
        EquipmentManager equipmentManager = new EquipmentManager();
        FishManager fishManager = new FishManager();
        FoodManager foodManager = new FoodManager();
        MiscManager miscManager = new MiscManager();
        RecipeManager recipeManager = new RecipeManager();
        RecipeManager.initRecipes();
        NPCManager npcManager = new NPCManager();
        SeedsManager seedsManager = new SeedsManager();

        NPCManager.initNPC(time);
        // NPCManager npcManager = new NPCManager();

        System.out.println("");
        System.out.println("Generating " + player.getName() + "'s game, please wait...");
        Thread.sleep(2000);

        List<NPC> allNPC = npcManager.getAllNPC();
        List<String> allSeedName = seedsManager.getAllSeedsNames();
        // List<Seeds> allSeeds = seedsManager.getAllSeeds();
        // List<Item> itemToBeSold = itemDijual();

        Map gameMap = player.getFarm().getfarmMap();
        
        // Generate the initial map
        try {
            gameMap.generateMap();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        
        boolean running = true;
        String input;
        String message = "nothing";
        int goldThreshold = 17209;
        boolean endgameStatsShown = false;
        
        // Game loop
        while (running) {

            if((player.getIsMarried() || player.getGold() >= goldThreshold) && !endgameStatsShown){
                printEndgameStats(player, store, allNPC);
                endgameStatsShown = true;
                System.out.println("** Press enter to continue playing **");
                scanner.nextLine();
            }

            // Print the map with color
            printColorMap(gameMap.getMap());
            
            // Get surrounding tiles information
            char[] surroundingTiles = gameMap.getSurroundingTiles();
            char currentTile = gameMap.getCurrentTile();
            boolean houseNearby = false;
            boolean pondNearby = false;
            boolean shippingBinNearby = false;
            
            // Display player status and surroundings
            if(message.equals("nothing")){
                System.out.println("System Message: ");
            } else {
                System.out.print("System Message: ");
                System.out.println(message);
            }
            System.out.println("");

            System.out.println("Player Coordinates: (" + gameMap.getPlayerX() + ", " + gameMap.getPlayerY() + ")");
            System.out.println("Current tile: " + getTileDescription(currentTile));
            System.out.println("Surroundings: Up[" + getTileDescription(surroundingTiles[0]) + 
                               "], Down[" + getTileDescription(surroundingTiles[1]) + 
                               "], Left[" + getTileDescription(surroundingTiles[2]) + 
                               "], Right[" + getTileDescription(surroundingTiles[3]) + "]");
            
            System.out.println("Controls:");
            System.out.println("- WASD: Move player");
            System.out.println("- T: Till soil");
            System.out.println("- R: Recover land");
            System.out.println("- P: Plant crop");
            System.out.println("- Wa: Water crop");
            System.out.println("- Hr: Harvest crop");
            System.out.println("- Eat: eat food");
            System.out.println("- Q: Quit game");
            System.out.println("- En: Show energy");
            System.out.println("- Time: Show time");
            System.out.println("- Inv: Show inventory");
            System.out.println("- Gold: Show gold");

            // if the player is near a structure, show additional controls
            for(int i = 0; i < 4; i++){
                if(surroundingTiles[i] == 'h'){
                    System.out.println("- H: Open House action menu");
                    houseNearby = true;
                }
                if(surroundingTiles[i] == 'o'){
                    System.out.println("- F: Fish in the pond");
                    pondNearby = true;
                }
                if(surroundingTiles[i] == 's'){
                    System.out.println("- SB: Open Shipping Bin menu");
                    shippingBinNearby = true;
                }
            }
            System.out.println();

            message = "nothing";

            // Get player input
            System.out.print("Enter command: ");
            input = scanner.nextLine().toLowerCase();
            
            // Process input
            switch (input) {
                case "w", "a", "s", "d" -> {
                    boolean moving = gameMap.move(input);
                    boolean outOfBounds = gameMap.isOutOfBound(input);
                    if(!moving){
                        if(outOfBounds){
                            System.out.print("Do you want to exit the farm? (y/n) ");
                            input = scanner.nextLine().toLowerCase();
                            if(input.equals("y")){
                                gameMap.worldMap(player, npcManager, store);
                                message = "Home sweet Farm!";
                                break;
                            } else {
                                message = "Out of bounds!";
                            }
                        } else {
                            message = "Cannot move through objects";
                        }
                    }
                }
                case "t" -> {
                    // Till soil if possible
                    if (gameMap.isTillable()) {
                        player.till(gameMap);
                        Thread.sleep(1000);
                        // message = "You tilled the soil!";
                    } else {
                        message = "Cannot till here!";
                    }
                }
                case "r" -> {
                    // recover land
                    if(gameMap.isTilled()){
                        player.recoverLand(gameMap);
                        Thread.sleep(1000);
                        // message = "You recovered the land!";
                    } else if (gameMap.isPlanted() || gameMap.isHarvestReady()){
                        message = "The soil is already used for planting!";
                    } else {
                        message = "No land to recover!";
                    }
                }
                case "p" -> {
                    // Plant on tilled soil
                    if (gameMap.isTilled()) {
                        while(true){
                            System.out.println("** Press 'b' to get back **");
                            System.out.print("Seed to plant (Match Case): ");
                            boolean seedFound = false;
                            String inputSeed = scanner.nextLine();

                            if(inputSeed.equals("b")){
                                message = "Plant cancelled";
                                break;
                            }

                            for(String seed : allSeedName){
                                if(inputSeed.equals(seed)){
                                    seedFound = true;
                                    break;
                                }
                            }

                            if(seedFound){
                                player.plant(gameMap.getPlayerX(), gameMap.getPlayerY(), seedsManager.getSeedsByName(inputSeed), gameMap);
                                Thread.sleep(1500);
                                break;
                            } else {
                                message = "Seed not found!";
                            }
                        }
                    } else {
                        message = "Cannot plant here! Till the soil first.";
                    }
                }
                case "wa" -> {
                    // water crops, kayaknya perlu thread?
                    if(gameMap.isWatered()){
                        message = "The plant is already watered";
                    } else if(gameMap.isPlanted()){
                        player.watering(gameMap.getPlayerX(), gameMap.getPlayerY(), player.getSeedFromSeedMap(gameMap.getPlayerX(), gameMap.getPlayerY()), gameMap);
                        Thread.sleep(1000);
                        // message = "You watered the crop!";
                    } else {
                        message = "There's no plant to water";
                    }
                }
                case "hr" -> {
                    if(gameMap.isHarvestReady()){
                        player.harvest(gameMap.getPlayerX(), gameMap.getPlayerY(), gameMap);
                        // message = "You harvested the crops!";
                    } else if (gameMap.isPlanted()) {
                        message = "Crop is not ready for harvest";
                    } else if (gameMap.isWithered()) {
                        message = "Crop has withered";
                    }else {
                        message = "No plant to harvest";
                    }
                }
                case "q" -> {
                    System.out.println("Quitting game...");
                    Thread.sleep(3000);
                    System.out.println("Goodbye!!");
                    running = false;
                }
                case "h" -> {
                    if(houseNearby){
                        houseAction(player, cooking, recipeManager, miscManager);
                        message = "You exited the house";
                    } else {
                        message = "You are not near a house!";
                    }
                }
                case "f" -> {
                    if(pondNearby){
                        boolean fished = player.fishing(Location.POND);
                        if(fished){
                            message = "You're done Fishing in the pond.";
                        } else {
                            message = "Fishing failed";
                        }
                        Thread.sleep(2000);
                    } else {
                        message = "You are not near a pond!";
                    }
                }
                case "sb" -> {
                    if(shippingBinNearby){
                        shippingBinAction(player);
                        message = "You exited the Shipping Bin";
                    } else {
                        message = "You are not near a shipping bin!";
                    }
                }
                case "eat" -> {
                    System.out.println("Food to eat (Match Case): ");
                    input = scanner.nextLine();
                    boolean ate = player.eat(foodManager.getFoodByName(input));
                    if(ate){
                        message = "You have eatan a " + input + "!";
                    } else {
                        message = input + " is not a food";
                    }
                }
                case "en" -> {
                    message = Integer.toString(player.getEnergy()) + " energy points left";
                }
                case "time" -> {
                    player.getFarm().showTime();
                    System.out.println("** Press enter to go back **");
                    input = scanner.nextLine();
                }
                case "inv" -> {
                    player.getInventory().printInventory();
                    System.out.println("** Press enter to go back **");
                    input = scanner.nextLine();
                }
                case "gold" -> {
                    message = "You now have " + Integer.toString(player.getGold()) + " gold";
                }
                case "honi" -> {
                    player.addGold(17209 - 9999 - 10);
                }
                case "mani" -> {
                    player.addGold(100);
                }
                case "x" -> {
                    time.addTime(60);
                }
                case "idle" -> {
                    if(randomNumber % 2 == 0){
                        idle.setIdle(new Humming());
                    } else {
                        idle.setIdle(new Humming());
                    }
                    idle.idling();
                }
                default -> message = "Unknown command.";
            }
            
            // Clear some space between turns
            // System.out.println("\n----------\n");

            TerminalClear.clearScreen();
        }

        scanner.close();
    }
    
    /**
     * Print the map with color-coded tiles for better visualization
     */
    private static void printColorMap(char[][] map) {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                switch(map[row][col]) {
                    case Map.TILLABLE -> System.out.print(ANSI_GREEN + ". " + ANSI_RESET);
                    case Map.TILLED -> System.out.print(ANSI_YELLOW + "t " + ANSI_RESET);
                    case Map.PLANTED -> System.out.print(ANSI_GREEN + "l " + ANSI_RESET);
                    case Map.HOUSE -> System.out.print(ANSI_RED + "h " + ANSI_RESET);
                    case Map.BIN -> System.out.print(ANSI_YELLOW + "s " + ANSI_RESET);
                    case Map.POND -> System.out.print(ANSI_BLUE + "o " + ANSI_RESET);
                    case Map.WATERED -> System.out.print(ANSI_BLUE + "w " + ANSI_RESET);
                    case Map.HARVESTABLE -> System.out.print(ANSI_CYAN + "c " + ANSI_RESET);
                    case Map.WITHERED -> System.out.print("x ");
                    case Map.PLAYER -> System.out.print("P ");
                    default -> System.out.print(map[row][col] + " ");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Get a human-readable description of a tile type
     */
    private static String getTileDescription(char tile) {
        return switch(tile) {
            case Map.TILLABLE -> "Tillable Soil";
            case Map.TILLED -> "Tilled Soil";
            case Map.PLANTED -> "Planted Crop";
            case Map.HOUSE -> "House";
            case Map.BIN -> "Storage Bin";
            case Map.POND -> "Pond";
            case Map.WATERED -> "Watered Plant";
            case Map.HARVESTABLE -> "Harvestable";
            case Map.WITHERED -> "Withered Plant";
            case Map.PLAYER -> "Player";
            case '\0' -> "Edge of Map";
            default -> "Unknown";
        };
    }

    public static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false; 
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void houseAction(Player player, Cooking cooking, RecipeManager recipeManager, MiscManager miscManager){
        String message = "nothing";
        String input = "";

        while(true){
            System.out.println("\n=== House Menu ===");
            System.out.println("1. Cooking");
            System.out.println("2. Sleeping");
            System.out.println("3. Watching");
            System.out.println("** Type 'b' to go to the previous section **");
            System.out.println("** Type the number based on the action! **");

            System.out.println("");
            if(message.equals("nothing")){
                System.out.println("System Message: ");
            } else {
                System.out.print("System Message: ");
                System.out.println(message);
            }
            System.out.println("");

            System.out.print("Action to do: ");
            input = scanner.nextLine().toLowerCase();

            message = "nothing";
            
            switch(input){
                case "1" -> {
                    // RecipeManager.initRecipes();
                    RecipeManager.printUnlockedRecipes();
                    System.out.print("\nMasukkan nama resep yang ingin dimasak: ");
                    String recipeName = scanner.nextLine();

                    System.out.print("Gunakan bahan bakar apa? (firewood / coal): ");
                    String fuelInput = scanner.nextLine().toLowerCase();

                    Misc fuel = null;
                    for (Item item : player.getInventory().getInventory().keySet()) {
                        if (item instanceof Misc miscItem) {
                            if ((fuelInput.equals("firewood") && miscItem.getType() == MiscType.FIREWOOD)
                            || (fuelInput.equals("coal") && miscItem.getType() == MiscType.COAL)) {
                                fuel = miscItem;
                                break;
                            }
                        }
                    }

                    boolean success = cooking.cook(player, recipeName, fuel, recipeManager, player.getTime());
                    message = success ? "Masakan berhasil dimulai!" : "Masakan gagal.";
                }

                case "2" -> {
                    player.sleep();
                    message = "Kamu tidur dan energi kamu di-reset sesuai kondisi.";
                }

                case "3" -> {
                    player.watching();
                    message = "You watched a movie!";
                }
                case "b" -> {
                    return;
                }
                default -> message = "Action is not valid";
            }
        }
    }

    public static void shippingBinAction(Player player) throws InterruptedException {
        String message = "nothing";

        while (true) { 
            System.out.println("=== Shipping Bin Menu ==="); 
            System.out.println("1. Add item to Shipping Bin");
            System.out.println("2. Sell Shipping Bin (once per day)");
            System.out.println("3. Show current items, quantity, and price in Shipping Bin");
            System.out.println("** Type 'b' to go to the previous section **");
            System.out.println("** Type the number based on the action! **");

            if(message.equals("nothing")){
                System.out.println("System Message: ");
            } else {
                System.out.print("System Message: ");
                System.out.println(message);
            }
            System.out.println("");
            System.out.print("Action to do: ");

            message = "nothing";
            
            String input = scanner.nextLine().toLowerCase();

            switch(input) {
                case "1" -> {
                    System.out.println("** Type 'b' to go to the previous section **");
                    System.out.println("What item do you want to add?");
                    System.out.print("Item name (Match Case): ");
                    String inputItemName = scanner.nextLine();

                    while(true){
                        System.out.println("** Type 'b' to go to the previous section **");
                        System.out.println("How much of this item do you want to add?");
                        System.out.print("Input Item Quantity: "); 
                        input = scanner.nextLine().toLowerCase();
                        boolean inputIsInteger = isInteger(input);

                        if(input.equals("b")){
                            break;
                        }

                        if(inputIsInteger){
                            int itemQuantity = Integer.parseInt(input);
                            player.getShippingBin().addItem(player.getInventory().getItemByName(inputItemName), itemQuantity, player);
                            Thread.sleep(1500);
                            break;
                        } else {
                            System.out.println("Input must be an integer!");
                        }
                    }
                }
                case "2" -> {
                    player.getShippingBin().sell(player);
                    Thread.sleep(1500);
                    message = "Looking for someone to buy your Shipping Bin";
                }
                case "3" -> {
                    System.out.println("=== Current Shipping Bin ===");
                    player.getShippingBin().printBin();
                    System.out.println("** Type anything to go back **");
                    scanner.nextLine();
                }
                case "b" -> {
                    return;
                }
                default -> message = "Invalid input";
            }
        }
    }

    public static void printEndgameStats(Player player, Store store, List<NPC> allNPC){
        double seasonAverageIncome = player.getShippingBin().getTotalIncome() / player.getFarm().getSeasonCount();
        double seasonAverageExpenditure = store.getTotalExpenditure() / player.getFarm().getSeasonCount();

        System.out.println(player.getName() + "'s Endgame Stats");
        System.out.println("Total Income                : " + player.getShippingBin().getTotalIncome() + " gold");
        System.out.println("Total Expenditure           : " + store.getTotalExpenditure() + " gold");
        System.out.println("Season Average Income       : " + String.format("%.2f", seasonAverageIncome) + " gold/season");
        System.out.println("Season Average Expenditure  : " + String.format("%.2f", seasonAverageExpenditure) + "gold/season");
        System.out.println("Total Days Played           : " + player.getFarm().getDayCount() + " days played");
        System.out.println("Total Crops Harvested       : " + player.getTotalCropHarvested() + " crops");
        System.out.println("Total Fish Caught           : " + player.getTotalFishCaught() + " fish'");
        System.out.println();

        System.out.println("NPC Status");
        for(NPC npc : allNPC){
            System.out.println(npc.getName() + "'s stats");
            System.out.println("Relationship Status : " + npc.getRelationshipStatus().toString());
            System.out.println("Chatting Frequency  : " + npc.getChattingFrequency());
            System.out.println("Gifting Frequency   : " + npc.getGiftingFrequency());
            System.out.println("Visiting Frequency  : " + npc.getVisitingFrequency());
        }
    }
}