import java.util.Random;
import java.util.Scanner;

public class Map {
    static final int WIDTH = 32;
    static final int HEIGHT = 32;
    static final char TILLABLE = '.';
    static final char TILLED = 't';
    static final char PLANTED = 'l';
    static final char WATERED = 'w';
    static final char HARVESTABLE = 'c';
    static final char WITHERED = 'x';
    static final char HOUSE = 'h';
    static final char BIN = 's';
    static final char POND = 'o';
    static final char PLAYER = 'P';

    private int playerX;
    private int playerY;
    private char currentTile;
    private char[][] map;
    
    private Random rand;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m"; 
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_CYAN = "\u001B[36m";

    public Map() {
        this.map = new char[HEIGHT][WIDTH];
        this.playerX = WIDTH / 2;
        this.playerY = HEIGHT / 2;
        this.currentTile = '.';
    }

    /*
     * return map
     */
    public char[][] getMap(){
        return map;
    }

    /**
     * return playerX/column
     */
    public int getPlayerX(){
        return playerX;
    }

    /**
     * return playerY/row
     */
    public int getPlayerY(){
        return playerY;
    }

    /*
     * Generate map random each time
     */
    public void generateMap () {
        final int HOUSE_SIZE = 6;
        final int BIN_WIDTH = 3;
        final int BIN_HEIGHT = 2;
        final int POND_WIDTH = 4;
        final int POND_HEIGHT = 3;

        rand = new Random();

        // Fill map with '.'
        for(int row = 0; row < HEIGHT; row++) {
            for(int col = 0; col < WIDTH; col++) {
                map[row][col] = TILLABLE;
            }
        }

        // Place house
        int houseX, houseY;
        while(true) {
            houseX = rand.nextInt(WIDTH - HOUSE_SIZE - BIN_WIDTH - 2);
            houseY = rand.nextInt(HEIGHT - HOUSE_SIZE - BIN_HEIGHT - 2);

            if(isAreaEmpty(houseX, houseY, HOUSE_SIZE, HOUSE_SIZE)) {
                fillArea(houseX, houseY, HOUSE_SIZE, HOUSE_SIZE, HOUSE);
                break;
            }
        }

        // Place bin 1 tile away from house
        int[][] binOffsets = {
            {HOUSE_SIZE + 1, 0}, // right
            {0, -BIN_HEIGHT - 1}, // above
            {-BIN_WIDTH - 1, 0}, // left
            {0, HOUSE_SIZE + 1}, // below
        };

        for(int[] offset : binOffsets) {
            int binX = houseX + offset[0];
            int binY = houseY + offset[1];

            if(isAreaEmpty(binX, binY, BIN_WIDTH, BIN_HEIGHT)) {
                fillArea(binX, binY, BIN_WIDTH, BIN_HEIGHT, BIN);
                break;
            }
        }

        // Place pond
        while(true) {
            int pondX = rand.nextInt(WIDTH - POND_WIDTH);
            int pondY = rand.nextInt(HEIGHT - POND_HEIGHT);

            if(isAreaEmpty(pondX, pondY, POND_WIDTH, POND_HEIGHT)) {
                fillArea(pondX, pondY, POND_WIDTH, POND_HEIGHT, POND);
                break;
            }
        }
    }

    // /*
    //  * print map
    //  */
    // public void printMap() {
    //     for(int row = 0; row < HEIGHT; row++) {
    //         for(int col = 0; col < WIDTH; col++) {
    //             System.out.print(map[row][col] + " ");
    //         }
    //         System.out.println();
    //     }
    // }

    /*
     * check if an object is already places in the map
     */
    private boolean isAreaEmpty(int startX, int startY, int width, int height) {
        for(int y = startY; y < startY + height; y++) {
            for(int x = startX; x < startX + width; x++) {
                if(x < 0 || x >= this.WIDTH || y < 0 || y >= this.HEIGHT){
                    throw new ArrayIndexOutOfBoundsException("Map/Object height or width is incorrect");
                }
                if(map[y][x] != TILLABLE) return false;
            }
        }
        return true;
    }

    /*
     * fill an area with a specific character
     */
    private void fillArea(int startX, int startY, int width, int height, char c) {
        for(int y = startY; y < startY + height; y++) {
            for(int x = startX; x < startX + width; x++) {
                if(x < 0 || x >= this.WIDTH || y < 0 || y >= this.HEIGHT){
                    throw new ArrayIndexOutOfBoundsException("Map/Object height or width is incorrect");
                }
                map[y][x] = c;
            }
        }
    }

    /*
     * check if the player is out of bound
     */
    public boolean isOutOfBound (String input){
        // Scanner scanner = new Scanner(System.in);
        boolean outOfBound = false;
        if(input.equals("w") && this.playerY == 0){
            outOfBound = true;
        } else if(input.equals("a") && this.playerX == 0){
            outOfBound = true;
        } else if(input.equals("s") && this.playerY == HEIGHT - 1){
            outOfBound = true;
        } else if(input.equals("d") && this.playerX == WIDTH - 1){
            outOfBound = true;
        }

        return outOfBound;
    }

    /*
     * check if the tile is walkable
     */ 
    private boolean isWalkable(String input){
        boolean walkable = false;
        boolean bound = isOutOfBound(input);
        if(input.equals("w") && !bound){
            if(this.map[this.playerY - 1][this.playerX] == TILLABLE || this.map[this.playerY - 1][this.playerX] == TILLED || this.map[this.playerY - 1][this.playerX] == PLANTED || this.map[this.playerY - 1][this.playerX] == WATERED || this.map[this.playerY - 1][this.playerX] == HARVESTABLE || this.map[this.playerY - 1][this.playerX] == WITHERED){
                walkable = true;
            }
        } else if(input.equals("a") && !bound){
            if(this.map[this.playerY][this.playerX - 1] == TILLABLE || this.map[this.playerY][this.playerX - 1] == TILLED || this.map[this.playerY][this.playerX - 1] == PLANTED || this.map[this.playerY][this.playerX - 1] == WATERED || this.map[this.playerY][this.playerX - 1] == HARVESTABLE || this.map[this.playerY][this.playerX - 1] == WITHERED){
                walkable = true;
            }
        } else if(input.equals("s") && !bound){
            if(this.map[this.playerY + 1][this.playerX] == TILLABLE || this.map[this.playerY + 1][this.playerX] == TILLED || this.map[this.playerY + 1][this.playerX] == PLANTED || this.map[this.playerY + 1][this.playerX] == WATERED || this.map[this.playerY + 1][this.playerX] == HARVESTABLE || this.map[this.playerY + 1][this.playerX] == WITHERED){
                walkable = true;
            }
        } else if(input.equals("d") && !bound){
            if(this.map[this.playerY][this.playerX + 1] == TILLABLE || this.map[this.playerY][this.playerX + 1] == TILLED || this.map[this.playerY][this.playerX + 1] == PLANTED || this.map[this.playerY][this.playerX + 1] == WATERED || this.map[this.playerY][this.playerX + 1] == HARVESTABLE || this.map[this.playerY][this.playerX + 1] == WITHERED){
                walkable = true;
            }
        } 
        return walkable;
    }

    /*
     * Move the player
     */
    public boolean move (String input) {
        boolean canMove = false;
        // boolean inputValid = true;

        switch (input) {
            case "w" -> {
                if(!isOutOfBound(input) && isWalkable(input)){
                    this.playerY -= 1;
                    this.currentTile = this.map[this.playerY][this.playerX];
                    canMove = true;
                }
            }
            case "a" -> {
                if(!isOutOfBound(input) && isWalkable(input)){
                    this.playerX -= 1;
                    this.currentTile = this.map[this.playerY][this.playerX];
                    canMove = true;
                }
            }
            case "s" -> {
                if(!isOutOfBound(input) && isWalkable(input)){
                    this.playerY += 1;
                    this.currentTile = this.map[this.playerY][this.playerX];
                    canMove = true;
                }
            }
            case "d" -> {
                if(!isOutOfBound(input) && isWalkable(input)){
                    this.playerX += 1;
                    this.currentTile = this.map[this.playerY][this.playerX];
                    canMove = true;
                }
            }
            // default -> inputValid = false;
        }
        return canMove;

        // if(canMove && inputValid){
        //     return true;
        // } else {
        //     // message = "use WASD to move!";
        //     return false;
        // }
    }

    /**
     * Get player's surrounding tiles
     * tiles[0] up
     * tiles[1] down
     * tiles[2] left
     * tiles[3] right
     */
    public char[] getSurroundingTiles(){
        char[] tiles = new char[4];

        // tiles[0] is the tile above the player
        if(this.playerY > 0) {
            tiles[0] = map[this.playerY - 1][this.playerX];
        }

        // tiles[1] is the tile below the player
        if(this.playerY < HEIGHT - 1){
            tiles[1] = map[this.playerY + 1][this.playerX];
        }

        // tiles[2] is the tile left to the player
        if(this.playerX > 0) {
            tiles[2] = this.map[this.playerY][this.playerX - 1];
        }

        // tiles[3] is the tile right to thr player
        if(this.playerX < WIDTH - 1) {
            tiles[3] = this.map[this.playerY][this.playerX + 1];
        }

        return tiles;
    }

    /**
     * return current player's tile
     */
    public char getCurrentTile(){
        return this.currentTile;
    }

    /**
     * set current player's tile
     */
    public void setCurrentTile(char tile){
        this.map[playerY][playerX] = tile;
        this.currentTile = tile;
    }

    /**
     * set any tile
     */
    public void setTile(char tile, int x, int y){
        this.map[y][x] = tile;
    }

    /**
     * check if the tile the player currently on is tillable
     */
    public boolean isTillable(){
        return this.currentTile == '.';
    }

    /**
     * check if the tile the player currently on is tilled
     */
    public boolean isTilled(){
        return this.currentTile == 't';
    }

    public boolean isPlanted(){
        return this.currentTile == 'l';
    }

    public boolean isHarvestReady(){
        return this.currentTile == 'c';
    }

    public boolean isWithered(){
        return this.currentTile == 'x';
    }

    public boolean isWatered(){
        return this.currentTile == 'w';
    }

    public boolean isInteger(String str) {
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

    /**
     * world map
     * masih bingung parameternya apa aja,
     * atau mending taro di player aja biar lebih gampang akses data-datanya
     */
    public void worldMap(Player player, NPCManager npcManager, Store store){
        Scanner scanner = new Scanner(System.in);
        String message = "nothing";
        player.visiting(Location.WORLDMAP);
        // player.visiting("WORLD MAP");

        while(true){
            if(!player.getSlept()){
                return;
            }
            System.out.println("=== World Map ===");
            System.out.println("1. NPC's House");
            System.out.println("2. Fishing Location");
            System.out.println("3. Store");
            System.out.println("** Type 'back' to go to the previous section **");
            System.out.println("** Type the correspending number to navigate through the world map **");
            System.out.println("");
            if(message.equals("nothing")){
                System.out.println("System Message: None");
            } else {
                System.out.print("System Message: ");
                System.out.println(message);
            }
            System.out.println("");
            System.out.print("Place to visit: ");
            String input = scanner.nextLine().toLowerCase();
            message = "nothing";

            switch (input) {
                case "1" -> {
                    while(true){
                        if(!player.getSlept()){
                            return;
                        }
                        System.out.println("=== NPC's House ===");
                        System.out.println("1. Mayor Tadi");
                        System.out.println("2. Caroline");
                        System.out.println("3. Perry");
                        System.out.println("4. Dasco");
                        System.out.println("5. Abigail");
                        System.out.println("6. Vincent");
                        System.out.println("7. Abil");
                        System.out.println("8. Aul");
                        System.out.println("9. Nazhif");
                        System.out.println("10. Syafiq");
                        System.out.println("11. Farhan");
                        System.out.println("12. BT");
                        System.out.println("13. Arra");
                        System.out.println("14. Fino");
                        System.out.println("15. Mahesa");
                        System.out.println("16. Kenlyn");
                        System.out.println("** Type 'b' to go to the previous section **");
                        System.out.println("** Type the correspending number to navigate through the world map **");

                        System.out.println("");
                        if(message.equals("nothing")){
                            System.out.println("System Message: ");
                        } else {
                            System.out.print("System Message: ");
                            System.out.println(message);
                        }
                        System.out.println("");

                        System.out.print("NPC to visit: ");
                        String input2 = scanner.nextLine().toLowerCase();

                        switch(input2) {
                            case "1" -> {
                                player.visiting(Location.MAYOR_TADI_HOUSE);
                                npcManager.getNPCByName("Mayor Tadi").incrementVisitingFrequency();
                                NPCAction("Mayor Tadi", npcManager, player);
                                message = "Done ... with Mayor Tadi";
                            }
                            case "2" -> {
                                player.visiting(Location.CAROLINE_HOUSE);
                                npcManager.getNPCByName("Caroline").incrementVisitingFrequency();
                                NPCAction("Caroline", npcManager, player);
                                message = "Done ... with Caroline";
                            }
                            case "3" -> {
                                player.visiting(Location.PERRY_HOUSE);
                                npcManager.getNPCByName("Perry").incrementVisitingFrequency();
                                NPCAction("Perry", npcManager, player);
                                message = "Done ... with Perry";
                            }
                            case "4" -> {
                                player.visiting(Location.DASCO_HOUSE);
                                npcManager.getNPCByName("Dasco").incrementVisitingFrequency();
                                NPCAction("Dasco", npcManager, player);
                                message = "Done ... with Dasco";
                            }
                            case "5" -> {
                                player.visiting(Location.ABIGAIL_HOUSE);
                                npcManager.getNPCByName("Abigail").incrementVisitingFrequency();
                                NPCAction("Abigail", npcManager, player);
                                message = "Done ... with Abigail";
                            }
                            case "6" -> {
                                player.visiting(Location.VINCENT_HOUSE);
                                npcManager.getNPCByName("Vincent").incrementVisitingFrequency();
                                NPCAction("Vincent", npcManager, player);
                                message = "Done ... with Vincent";
                            }
                            case "7" -> {
                                player.visiting(Location.ABIL_HOUSE);
                                npcManager.getNPCByName("Abil").incrementVisitingFrequency();
                                NPCAction("Abil", npcManager, player);
                                message = "Done ... with Abil";
                            }
                            case "8" -> {
                                player.visiting(Location.AUL_HOUSE);
                                npcManager.getNPCByName("Aul").incrementVisitingFrequency();
                                NPCAction("Aul", npcManager, player);
                                message = "Done ... with Aul";
                            }
                            case "9" -> {
                                player.visiting(Location.NAZHIF_HOUSE);
                                npcManager.getNPCByName("Nazhif").incrementVisitingFrequency();
                                NPCAction("Nazhif", npcManager, player);
                                message = "Done ... with Nazhif";
                            }
                            case "10" -> {
                                player.visiting(Location.SYAFIQ_HOUSE);
                                npcManager.getNPCByName("Syafiq").incrementVisitingFrequency();
                                NPCAction("Syafiq", npcManager, player);
                                message = "Done ... with Syafiq";
                            }
                            case "11" -> {
                                player.visiting(Location.FARHAN_HOUSE);
                                npcManager.getNPCByName("Farhan").incrementVisitingFrequency();
                                NPCAction("Farhan", npcManager, player);
                                message = "Done ... with Farhan";
                            }
                            case "12" -> {
                                player.visiting(Location.BT_HOUSE);
                                npcManager.getNPCByName("BT").incrementVisitingFrequency();
                                NPCAction("BT", npcManager, player);
                                message = "Done ... with BT";
                            }
                            case "13" -> {
                                player.visiting(Location.ARRA_HOUSE);
                                npcManager.getNPCByName("Arra").incrementVisitingFrequency();
                                NPCAction("Arra", npcManager, player);
                                message = "Done ... with Arra";
                            }
                            case "14" -> {
                                player.visiting(Location.FINO_HOUSE);
                                npcManager.getNPCByName("Fino").incrementVisitingFrequency();
                                NPCAction("Fino", npcManager, player);
                                message = "Done ... with Fino";
                            }
                            case "15" -> {
                                player.visiting(Location.MAHESA_HOUSE);
                                npcManager.getNPCByName("Mahesa").incrementVisitingFrequency();
                                NPCAction("Mahesa", npcManager, player);
                                message = "Done ... with Mahesa";
                            }
                            case "16" -> {
                                player.visiting(Location.KENLYN_HOUSE);
                                npcManager.getNPCByName("Kenlyn").incrementVisitingFrequency();
                                NPCAction("Kenlyn", npcManager, player);
                                message = "Done ... with Kenlyn";
                            }
                            case "b" -> {
                                message = "Back from NPC's house menu";
                                break;
                            }
                            default -> message = "Action is not valid!";
                        }

                        if(input2.equals("b")){
                            break;
                        }
                    }
                }
                case "2" -> {
                    while(true){
                        if(!player.getSlept()){
                            return;
                        }
                        boolean fished = false;
                        System.out.println("=== Fishing Locations ===");
                        System.out.println("1. Forest River");
                        System.out.println("2. Mountain Lake");
                        System.out.println("3. Ocean");
                        System.out.println("** Type 'b' to go to the previous section **");
                        System.out.println("** Type the correspending number to navigate through the world map **");

                        System.out.println("");
                        if(message.equals("nothing")){
                                System.out.println("System Message: ");
                        } else {
                            System.out.print("System Message: ");
                            System.out.println(message);
                        }
                        System.out.println("");
                        
                        System.out.print("Place to fish: ");
                        String input2 = scanner.nextLine().toLowerCase();

                        switch(input2){
                            case "1" -> {
                                player.visiting(Location.FOREST_RIVER);
                                fished = player.fishing(Location.FOREST_RIVER);
                                if(fished){
                                    message = "Fished at a Forest River";
                                } else {
                                    message = "Failed to fish at a Forest River";
                                }
                            } 
                            case "2" -> {
                                player.visiting(Location.MOUNTAIN_LAKE);
                                fished = player.fishing(Location.MOUNTAIN_LAKE);
                                if(fished){
                                    message = "Fished at a Mountain Lake";
                                } else {
                                    message = "Failed to fish at a Mountain Lake";
                                }
                            }
                            case "3" -> {
                                player.visiting(Location.OCEAN);
                                fished = player.fishing(Location.OCEAN);
                                if(fished){
                                    message = "Fished on the Ocean";
                                } else {
                                    message = "Failed to Fish on the Ocean";
                                }
                            }
                            case "b" -> {
                                message = "Back from Fishing Locations menu";
                                break;
                            }
                            default -> message = "Action is not valid!";
                        }

                        if(input2.equals("b")){
                            break;
                        }
                    }
                }
                case "3" -> {
                    player.visiting(Location.STORE);
                    while(true){
                        if(!player.getSlept()){
                            return;
                        }
                        System.out.println("=== Store ===");
                        System.out.println("1. Visit Emily");
                        System.out.println("2. Buy Item");
                        System.out.println("3. Buy Recipe");
                        System.out.println("** Type 'b' to go to the previous section **");
                        System.out.println("** Type the correspending number to navigate through the world map **");
                        
                        System.out.println("");
                        if(message.equals("nothing")){
                                System.out.println("System Message: ");
                        } else {
                            System.out.print("System Message: ");
                            System.out.println(message);
                        }
                        System.out.println("");

                        System.out.print("What to do in the Store: ");
                        String input2 = scanner.nextLine().toLowerCase();

                        message = "nothing";

                        switch(input2){
                            case "1" -> {
                                npcManager.getNPCByName("Emily").incrementVisitingFrequency();
                                NPCAction("Emily", npcManager, player);
                                message = "Done meeting with Emily";
                            }
                            case "2" -> {
                                while(true){
                                    if(!player.getSlept()){
                                        return;
                                    }
                                    store.showItemsForSale();
                                    if(message.equals("nothing")){
                                        System.out.println("System Message: ");
                                    } else {
                                        System.out.print("System Message: ");
                                        System.out.println(message);
                                    }
                                    System.out.println("");

                                    message = "nothing";
                                    boolean success = false;
                                    int inputQ = 0;
                                    String inputQuantity = "";
                                    int intItemName = 0;

                                    System.out.println("** Type 'b' to exit this menu **");
                                    System.out.println("** Type the correspending number to navigate **");

                                    System.out.print("What item do you want to buy: ");
                                    String input3 = scanner.nextLine().toLowerCase();

                                    if(input3.equals("b")){
                                        break;
                                    }
                                    
                                    if(isInteger(input3)){
                                        intItemName = Integer.parseInt(input3);
                                        System.out.print("Item Quantity: ");
                                        inputQuantity = scanner.nextLine().toLowerCase();
                                        if(isInteger(inputQuantity)){
                                            inputQ = Integer.parseInt(inputQuantity);
                                            success = store.buyItem(player, store.getItemNameByIndex(intItemName), inputQ);
                                        } else {
                                            System.out.println("Quantity must be a number!");
                                        }
                                    } else {
                                        message = "Must input a number!";
                                        break;
                                    }

                                    if(success){
                                        message = "Berhasil membeli " + store.getItemNameByIndex(intItemName) + " sebanyak " + inputQuantity;
                                    } else {
                                        message = "Gagal membeli item dari shop";
                                    }
                                }
                            }
                            case "3" -> {
                                while (true) {
                                    if(!player.getSlept()){
                                        return;
                                    }
                                    store.showRecipesForSale();
                                    System.out.println("** Ketik 'b' untuk kembali **");
                                    System.out.print("Nama resep yang ingin dibeli: ");
                                    String recipeName = scanner.nextLine().toLowerCase();

                                    if (recipeName.equals("b")) break;

                                    boolean success = store.buyRecipe(player, recipeName);

                                    if (success) {
                                        message = "Berhasil membeli resep '" + recipeName + "'.";
                                    } else {
                                        message = "Gagal membeli resep. Pastikan nama benar, resep belum dibeli, dan uang cukup.";
                                    }
                                }
                            }

                            case "b" -> {
                                message = "Back from Store menu";
                                break;
                            }
                            default -> message = "Action is not valid!";
                        }

                        if(input2.equals("b")){
                            break;
                        }
                    }
                }
                case "back" -> {
                    break;
                }
                default -> {
                    message = "Input is not valid.";
                }
            }

            if(input.equals("back")){
                break;
            }
        }
    }

    public void NPCAction(String npcName, NPCManager npcManager, Player player){
        Scanner scanner = new Scanner(System.in);
        String message = "nothing";
        
        while(true){
            if(!player.getSlept()){
                return;
            }
            if(npcName.equals("Emily")){
                System.out.println("\n=== " + npcName + "'s Store ===");
            } else {
                System.out.println("\n=== " + npcName + "'s House ===");
            }
            System.out.println("1. Chatting");
            System.out.println("2. Gifting");
            System.out.println("3. Propose");
            System.out.println("4. Marry");
            System.out.println("5. Show " + npcName + "'s heart points");

            System.out.println("");
            if(message.equals("nothing")){
                System.out.println("System Message: ");
            } else {
                System.out.print("System Message: ");
                System.out.println(message);
            }
            System.out.println("");

            System.out.println("** Type 'b' to exit this menu **");
            System.out.println("** Type the correspending number to navigate **");

            message = "nothing";

            System.out.print("Action to do: ");
            String input = scanner.nextLine().toLowerCase();
        
            switch(input){
                case "1" -> {
                    player.chatting(npcManager.getNPCByName(npcName));
                    // message = "You have chat with " + npcName + "!";
                }
                case "2" -> {
                    System.out.println("Item to gift (Match Case): ");
                    String itemToGift = scanner.nextLine();
                    player.gifting(npcManager.getNPCByName(npcName), player.getInventory().getItemByName(itemToGift));
                    // message = "You have gifted " + npcName + " an item!";
                }
                case "3" -> {
                    player.propose(npcManager.getNPCByName(npcName));
                    // message = "You have proposed " + npcName +"!";
                }
                case "4" -> {
                    player.marry(npcManager.getNPCByName(npcName));
                    // message = "You are now married with " + npcName + "!";
                }
                case "5" -> {
                    System.out.println(npcName + " has " + npcManager.getNPCByName(npcName).getHeartPoints() + " heart points");
                    System.out.println("** Press enter to go back **");
                    input = scanner.nextLine();
                }
                case "b" -> {
                    return;
                }
                default -> message = "Action is not valid!";
            }
        }
    }

    public void printColorMap() {
        for (int row = 0; row < this.map.length; row++) {
            for (int col = 0; col < this.map[row].length; col++) {
                if(this.playerX == col && this.playerY == row){
                    System.out.print("P ");
                    continue;
                }
                switch(this.map[row][col]) {
                    case Map.TILLABLE -> System.out.print(ANSI_GREEN + ". " + ANSI_RESET);
                    case Map.TILLED -> System.out.print(ANSI_YELLOW + "t " + ANSI_RESET);
                    case Map.PLANTED -> System.out.print(ANSI_GREEN + "l " + ANSI_RESET);
                    case Map.HOUSE -> System.out.print(ANSI_RED + "h " + ANSI_RESET);
                    case Map.BIN -> System.out.print(ANSI_YELLOW + "s " + ANSI_RESET);
                    case Map.POND -> System.out.print(ANSI_BLUE + "o " + ANSI_RESET);
                    case Map.WATERED -> System.out.print(ANSI_BLUE + "w " + ANSI_RESET);
                    case Map.HARVESTABLE -> System.out.print(ANSI_CYAN + "c " + ANSI_RESET);
                    case Map.WITHERED -> System.out.print("x ");
                }
            }
            System.out.println();
        }
    }
}