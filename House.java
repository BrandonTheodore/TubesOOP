import java.util.Scanner;

public class House{
    static final char PLAYER = 'P';
    static final char TV = '#';
    static final char LAMP = 'l';
    static final char COUCH = 'c';
    static final char CARPET = '.';
    static final char PLANT = 'v';
    static final char CHAIR = 'n';
    static final char TABLE = '=';
    static final char KITCHEN = 'k';
    static final char SINK = 't';
    static final char TOILET = 'h';
    static final char SHOWER = 's';
    static final char BATHTUB = 'u';
    static final char BED = 'b';
    static final char VERTICAL_WALL = '|';
    static final char HORIZONTAL_WALL = '_';
    static final char EXIT = 'd';
;

    private int playerX;
    private int playerY;
    private char currentTile;
    private char[][] map;
    
    // ANSI color codes
    public static final String RESET = "\033[0m";
    public static final String BROWN = "\033[38;5;94m";
    public static final String GREEN = "\033[32m";
    public static final String LIGHT_BLUE = "\033[94m";
    public static final String GREY = "\033[90m";
    public static final String RED = "\033[31m";
    public static final String YELLOW = "\033[33m";
    public static final String BLUE = "\033[34m";
    public static final String WHITE = "\033[37m";
    public static final String PLAYER_COLOR = "\033[35m";
    public static final String BRIGHT_YELLOW = "\033[38;5;226m";
    public static final String LIGHT_YELLOW = "\033[38;5;228m";
    public static final String BRIGHT_RED = "\033[38;5;196m";
    public static final String DARK_BROWN = "\033[38;5;88m";
    public static final String LIGHT_BROWN = "\033[38;5;137m";

    public House(){
        this.playerX = 11;
        this.playerY = 22;  // Changed from 23 to 22 (valid index)
        this.currentTile = ' ';
        this.map = new char[][]{
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'},
            {'|',' ','l',' ','#','#','#',' ','l',' ','|','s',' ',' ','t','|','l',' ',' ','.','.','.',' ','|'},
            {'|',' ',' ',' ',' ',' ',' ',' ',' ',' ','|','h',' ',' ',' ','|','b','b','b','.','.','.','#','|'},
            {'|',' ',' ',' ','.','.','.',' ',' ',' ','|','_','_',' ','_','|','b','b','b','.','.','.','#','|'},
            {'|',' ',' ','c','c','c','c','c',' ',' ',' ',' ',' ',' ',' ','|','b','b','b','.','.','.','#','|'},
            {'|',' ',' ','c','c','c','c','c',' ',' ',' ',' ',' ',' ',' ','|','l',' ',' ','.','.','.',' ','|'},
            {'|',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','|',' ',' ',' ',' ',' ',' ',' ','|'},
            {'|',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','|',' ',' ',' ',' ','n','n',' ','|'},
            {'|','v',' ',' ',' ',' ',' ',' ',' ','v','|','=',' ',' ',' ','|',' ',' ',' ','=','=','=','=','|'},
            {'|','_','_',' ',' ',' ','_','_','_','_','|','=',' ',' ',' ','|',' ',' ',' ','=','=','=','=','|'},
            {'|','v',' ',' ',' ',' ',' ',' ',' ','v','|','=',' ',' ',' ','|','_',' ',' ','_','_','_','_','|'},
            {'|',' ',' ',' ',' ',' ',' ',' ',' ',' ','|','=',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','=','|'},
            {'|',' ','.','.','n','.','.',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','=','|'},
            {'|',' ','.','=','=','=','.',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','v','|'},
            {'|',' ','.','=','=','=','.',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','v','|'},
            {'|',' ','.','.','n','.','.',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','=','|'},
            {'|',' ',' ',' ',' ',' ',' ',' ',' ','v','|','v',' ',' ',' ',' ',' ','v',' ',' ',' ',' ','=','|'},
            {'|',' ',' ',' ',' ',' ',' ',' ','_','_','_','_','_',' ',' ',' ','_','_','_',' ',' ','_','_','|'},
            {'|',' ',' ',' ',' ',' ',' ',' ','|',' ',' ',' ',' ',' ',' ',' ',' ','|','t',' ',' ','s','s','|'},
            {'|','=','k',' ',' ',' ','k','=','|',' ','.','.','.','.',' ',' ',' ','|',' ',' ',' ','s','s','|'},
            {'|','=','k',' ',' ',' ','k','=','|',' ','.','.','.','.',' ',' ',' ','|',' ',' ',' ',' ',' ','|'},
            {'|','=','k','k','k','k','k','=','|',' ','.','.','.','.',' ',' ',' ','|',' ',' ','u','u','u','|'},
            {'|','=','=','=','=','=','=','=','|',' ',' ',' ',' ',' ',' ',' ',' ','|','h',' ','u','u','u','|'},
            {'|','_','_','_','_','_','_','_','|','_','d','d','d','_','_','_','_','|','_','_','_','_','_','|'}
        };
    }

    public int getPlayerX(){
        return this.playerX;
    }

    public int getPlayerY(){
        return this.playerY;
    }

    public char[][] getMap(){
        return this.map;
    }

    public char getCurrentTile(){
        return this.currentTile;
    }

    public boolean isOutOfBound (String input){
        boolean outOfBound = false;
        if(input.equals("w") && this.playerY == 0){
            outOfBound = true;
        } else if(input.equals("a") && this.playerX == 0){
            outOfBound = true;
        } else if(input.equals("s") && this.playerY == this.map.length - 1){  // Fixed: use map.length - 1
            outOfBound = true;
        } else if(input.equals("d") && this.playerX == this.map.length - 1){  // Fixed: use map[0].length - 1
            outOfBound = true;
        }
        return outOfBound;
    }

    private boolean isWalkable(String input){
        boolean walkable = true;  // Changed default to true
        boolean bound = isOutOfBound(input);
        
        if(bound) return false;  // Can't walk if out of bounds
        
        char targetTile = ' ';
        if(input.equals("w")){
            targetTile = this.map[this.playerY - 1][this.playerX];
        } else if(input.equals("a")){
            targetTile = this.map[this.playerY][this.playerX - 1];
        } else if(input.equals("s")){
            targetTile = this.map[this.playerY + 1][this.playerX];
        } else if(input.equals("d")){
            targetTile = this.map[this.playerY][this.playerX + 1];
        }
        
        // Can't walk through walls ('_', '|') or furniture
        if(targetTile == HORIZONTAL_WALL || targetTile == VERTICAL_WALL || targetTile == KITCHEN ||  targetTile == TV || targetTile == TABLE || targetTile == BED || targetTile == COUCH || targetTile == BATHTUB || targetTile == SHOWER || targetTile == PLANT || targetTile == TOILET || targetTile == SINK || targetTile == EXIT || targetTile == LAMP) {
            walkable = false;
        }
        
        return walkable;
    }
    
    public boolean move (String input) {
        // int previousX = this.playerX;
        // int previousY = this.playerY;
        // char previousTile = 'a';
        boolean canMove = false;
        boolean inputValid = true;

        switch (input) {
            case "w" -> {
                if(!isOutOfBound(input) && isWalkable(input)){
                    // previousTile = this.currentTile;
                    this.playerY -= 1;
                    this.currentTile = map[this.playerY][this.playerX];
                    canMove = true;
                }
            }
            case "a" -> {
                if(!isOutOfBound(input) && isWalkable(input)){
                    // previousTile = this.currentTile;
                    this.playerX -= 1;
                    this.currentTile = map[this.playerY][this.playerX];
                    canMove = true;
                }
            }
            case "s" -> {
                if(!isOutOfBound(input) && isWalkable(input)){
                    // previousTile = this.currentTile;
                    this.playerY += 1;
                    this.currentTile = map[this.playerY][this.playerX];
                    canMove = true;
                }
            }
            case "d" -> {
                if(!isOutOfBound(input) && isWalkable(input)){
                    // previousTile = this.currentTile;
                    this.playerX += 1;
                    this.currentTile = map[this.playerY][this.playerX];
                    canMove = true;
                }
            }
            default -> inputValid = false;
        }

        if(canMove && inputValid){
            // this.map[this.playerY][this.playerX] = PLAYER;
            // this.map[previousY][previousX] = previousTile;
            return true;
        } else {
            // message = "use WASD to move!";
            return false;
        }
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
            tiles[0] = this.map[this.playerY - 1][this.playerX];
        }

        // tiles[1] is the tile below the player
        if(this.playerY < 24 - 1){
            tiles[1] = this.map[this.playerY + 1][this.playerX];
        }

        // tiles[2] is the tile left to the player
        if(this.playerX > 0) {
            tiles[2] = this.map[this.playerY][this.playerX - 1];
        }

        // tiles[3] is the tile right to thr player
        if(this.playerX < 24 - 1) {
            tiles[3] = this.map[this.playerY][this.playerX + 1];
        }

        return tiles;
    }

    private static String getTileDescription(char tile) {
        return switch(tile) {
            case House.TV -> "TV";
            case House.LAMP -> "Lamp";
            case House.COUCH -> "Couch";
            case House.CARPET -> "Carpet";
            case House.PLANT -> "Plant";
            case House.CHAIR -> "Chair";
            case House.TABLE -> "Table";
            case House.KITCHEN -> "Kitchen";
            case House.SINK -> "Sink";
            case House.TOILET -> "Toilet";
            case House.SHOWER -> "Shower";
            case House.BATHTUB -> "Bathtub";
            case House.BED -> "Bed";
            case House.VERTICAL_WALL -> "Wall";
            case House.HORIZONTAL_WALL -> "Wall";
            case House.EXIT -> "Exit Door";
            default -> "Floor";
        };
    }

    private void printColorMap() {
        for (int row = 0; row < this.map.length; row++) {
            for (int col = 0; col < this.map[row].length; col++) {
                if(this.playerX == col && this.playerY == row){
                    System.out.print("P ");
                    continue;
                }
                switch(this.map[row][col]) {
                    case House.TV -> System.out.print(BRIGHT_RED + "# " + RESET);
                    case House.LAMP -> System.out.print(BRIGHT_YELLOW + "l " + RESET);
                    case House.COUCH -> System.out.print(BLUE + "c " + RESET);
                    case House.CARPET -> System.out.print(RED + ". " + RESET);
                    case House.PLANT -> System.out.print(GREEN + "v " + RESET);
                    case House.CHAIR -> System.out.print(LIGHT_BROWN + "n " + RESET);
                    case House.TABLE -> System.out.print(BROWN + "= " + RESET);
                    case House.KITCHEN -> System.out.print(LIGHT_BROWN + "k " + RESET);
                    case House.SINK -> System.out.print(WHITE + "t " + RESET);
                    case House.TOILET -> System.out.print(WHITE + "h " + RESET);
                    case House.SHOWER -> System.out.print(LIGHT_BLUE + "s " + RESET);
                    case House.BATHTUB -> System.out.print(LIGHT_BLUE + "u " + RESET);
                    case House.BED -> System.out.print(BLUE + "b " + RESET);
                    case House.EXIT -> System.out.print(DARK_BROWN + "d " + RESET);
                    case House.VERTICAL_WALL -> System.out.print(DARK_BROWN + "| " + RESET);
                    case House.HORIZONTAL_WALL -> System.out.print(DARK_BROWN + "_ " + RESET);
                    default -> System.out.print(map[row][col] + " ");
                }
            }
            System.out.println();
        }
    }

    public void house(Player player, Cooking cooking, RecipeManager recipeManager, MiscManager miscManager, boolean slept) {
        TerminalClear.clearScreen();
        Scanner scanner = new Scanner(System.in);
        String input;
        String message = "nothing";
        boolean running = true;
        boolean playerSlept = slept;
        
        while(running){
            if(playerSlept){
                this.playerX = 17;
                this.playerY = 5;
                playerSlept = false;
                message = "You got too tired, went back home, got some good sleep :D";
            }

            printColorMap();
            char[] surroundingTiles = getSurroundingTiles();
            boolean nearBed = false;
            boolean nearCouch = false;
            boolean nearKitchen = false;
            boolean nearExit = false;
            
            // Display player status and surroundings
            if(message.equals("nothing")){
                System.out.println("System Message: ");
            } else {
                System.out.print("System Message: ");
                System.out.println(message);
            }
            System.out.println("");

            System.out.println("Player Coordinates  : (" + this.playerX + ", " + this.playerY + ")");
            System.out.println("Current tile        : " + getTileDescription(this.currentTile));
            System.out.println("Surroundings        : Up[" + getTileDescription(surroundingTiles[0]) + 
                                "], Down[" + getTileDescription(surroundingTiles[1]) + 
                                "], Left[" + getTileDescription(surroundingTiles[2]) + 
                                "], Right[" + getTileDescription(surroundingTiles[3]) + "]");

            System.out.println("");

            System.out.println("Energy points   : " + player.getEnergy());
            System.out.println("Gold            : " + player.getGold());

            System.out.println("");
            
            System.out.println("Controls:");
            System.out.println("- WASD  : Move player");
            System.out.println("- TIME  : Show time");
            System.out.println("- EXIT  : Faster house exit");
            System.out.println("- HELP  : Display useful info");
            System.out.println("- INV   : Show inventory");

            for(int i = 0; i < 4; i++){
                if(surroundingTiles[i] == KITCHEN){
                    System.out.println("- C     : Cook food");
                    nearKitchen = true;
                    break;
                }
                if(surroundingTiles[i] == BED){
                    System.out.println("- Sl    : Sleep");
                    nearBed = true;
                }
                if(surroundingTiles[i] == COUCH){
                    System.out.println("- Wa   : Watch TV");
                    nearCouch = true;
                }
                if(surroundingTiles[i] == EXIT){
                    System.out.println("- X     : Exit the house");
                    nearExit = true;
                }
            }
            System.out.println("");

            message = "nothing";

            System.out.print("Enter Command: ");
            input = scanner.nextLine().toLowerCase();

            switch(input){
                case "w", "a", "s", "d" -> {
                    boolean moving = move(input);
                    if(!moving){
                        message = "Cannot move through objects";
                    }
                }
                case "c" -> {
                    if(nearKitchen){
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
                    } else {
                        message = "You must be near the Kitchen to cook!";
                    }
                }
                case "sl" -> {
                    if(nearBed){
                        player.sleep();
                        playerSlept = true;
                        message = "You had a good night sleep";
                    } else {
                        message = "You must be near a Bed to sleep!";
                    }
                }
                case "wa" -> {
                    if(nearCouch){
                        player.watching();
                        message = "You watched Senchou!";
                    } else {
                        message = "You must be in a Couch to watch TV!";
                    }
                }
                case "x" ->{
                    if(nearExit){
                        System.out.println("Do you want to exit the house? (y to accept)");
                        input = scanner.nextLine().toLowerCase();
                        if(input.equals("y")){
                            return;
                        }
                        break;
                    } else {
                        message = "You cannot exit, no door to go through";
                    }
                }
                case "exit" -> {
                    System.out.println("Do you want to exit the house? (y to accept)");
                    input = scanner.nextLine().toLowerCase();
                    if(input.equals("y")){
                        return;
                    }
                }
                case "time" -> {
                    player.getFarm().showTime();
                    System.out.println("** Press enter to go back **");
                    scanner.nextLine();
                }
                case "inv" -> {
                    player.getInventory().printInventory();
                    System.out.println("** Press enter to go back **");
                    scanner.nextLine();
                }
                case "help" -> {
                    System.out.println("");
                    System.out.println("Go near these character in the map to do an action,");
                    System.out.println("Couch     : " + COUCH);
                    System.out.println("Bed       : " + BED);
                    System.out.println("Kithcen   : " + KITCHEN);
                    System.out.println("Exit Door : " + EXIT);
                    System.out.println("");

                    System.out.println("These are other objects in the map,");
                    System.out.println("Table   : " + TABLE);
                    System.out.println("Chair   : " + CHAIR);
                    System.out.println("TV      : " + TV);
                    System.out.println("Lamp    : " + LAMP);
                    System.out.println("Carpet  : " + CARPET);
                    System.out.println("Plant   : " + PLANT);
                    System.out.println("Bathtub : " + BATHTUB);
                    System.out.println("Shower  : " + SHOWER);
                    System.out.println("Sink    : " + SINK);
                    System.out.println("Toilet  : " + TOILET);
                    
                    System.out.println("** Press enter to go back **");
                    scanner.next();
                }
                default -> message = "Invalid Input";
            }
            if(this.currentTile == CHAIR){
                message = "You are now sitting in a chair..";
            }
            TerminalClear.clearScreen();
        }
    }
}
