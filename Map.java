import java.util.Random;
import java.util.Scanner;

public class Map {
    static final int WIDTH = 32;
    static final int HEIGHT = 32;
    static final char TILLABLE = '.';
    static final char TILLED = 't';
    static final char PLANTED = 'l';
    static final char HOUSE = 'h';
    static final char BIN = 's';
    static final char POND = 'o';
    static final char WORLDMAP = 'w';
    static final char PLAYER = 'P';

    private int playerX;
    private int playerY;
    private char currentTile;
    private char[][] map;
    
    private Random rand;

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
        final int WORLDMAP_SIZE = 2;

        rand = new Random();

        // Fill map with '.'
        for(int row = 0; row < HEIGHT; row++) {
            for(int col = 0; col < WIDTH; col++) {
                map[row][col] = TILLABLE;
            }
        }

        // Place workbench at bottom-right
        int workbenchX = WIDTH - WORLDMAP_SIZE;
        int workbenchY = HEIGHT - WORLDMAP_SIZE;
        fillArea(workbenchX, workbenchY, WORLDMAP_SIZE, WORLDMAP_SIZE, WORLDMAP);

        // Place player in the middle
        if(map[playerY][playerX] == TILLABLE) {
            map[playerY][playerX] = PLAYER;
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

    /*
     * print map
     */
    public void printMap() {
        for(int row = 0; row < HEIGHT; row++) {
            for(int col = 0; col < WIDTH; col++) {
                System.out.print(map[row][col] + " ");
            }
            System.out.println();
        }
    }

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
    public boolean isOutOfBounds(String input){
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
    public boolean isWalkable(String input){
        boolean walkable = false;
        boolean bound = isOutOfBounds(input);
        if(input.equals("w") && bound == false){
            if(this.map[this.playerY - 1][this.playerX] == TILLABLE || this.map[this.playerY - 1][this.playerX] == TILLED || this.map[this.playerY - 1][this.playerX] == PLANTED){
                walkable = true;
            }
        } else if(input.equals("a") && bound == false){
            if(this.map[this.playerY][this.playerX - 1] == TILLABLE || this.map[this.playerY][this.playerX - 1] == TILLED || this.map[this.playerY][this.playerX - 1] == PLANTED){
                walkable = true;
            }
        } else if(input.equals("s") && bound == false){
            if(this.map[this.playerY + 1][this.playerX] == TILLABLE || this.map[this.playerY + 1][this.playerX] == TILLED || this.map[this.playerY + 1][this.playerX] == PLANTED){
                walkable = true;
            }
        } else if(input.equals("d") && bound == false){
            if(this.map[this.playerY][this.playerX + 1] == TILLABLE || this.map[this.playerY][this.playerX + 1] == TILLED || this.map[this.playerY][this.playerX + 1] == PLANTED){
                walkable = true;
            }
        } 
        return walkable;
    }

    /*
     * Move the player
     */
    public String move(String input){
        int previousX = this.playerX;
        int previousY = this.playerY;
        char previousTile = 'a';
        boolean canMove = false;
        boolean inputValid = true;
        String message = "";

        switch (input) {
            case "w" -> {
                if(!isOutOfBounds(input) && isWalkable(input)){
                    previousTile = this.currentTile;
                    this.playerY -= 1;
                    this.currentTile = this.map[this.playerY][this.playerX];
                    canMove = true;
                }
            }
            case "a" -> {
                if(!isOutOfBounds(input) && isWalkable(input)){
                    previousTile = this.currentTile;
                    this.playerX -= 1;
                    this.currentTile = this.map[this.playerY][this.playerX];
                    canMove = true;
                }
            }
            case "s" -> {
                if(!isOutOfBounds(input) && isWalkable(input)){
                    previousTile = this.currentTile;
                    this.playerY += 1;
                    this.currentTile = this.map[this.playerY][this.playerX];
                    canMove = true;
                }
            }
            case "d" -> {
                if(!isOutOfBounds(input) && isWalkable(input)){
                    previousTile = this.currentTile;
                    this.playerX += 1;
                    this.currentTile = this.map[this.playerY][this.playerX];
                    canMove = true;
                }
            }
            default -> inputValid = false;
        }

        if(canMove && inputValid){
            this.map[this.playerY][this.playerX] = PLAYER;
            this.map[previousY][previousX] = previousTile;
            return message;
        } else if(!canMove && inputValid) {
            message = "Cannot move through buildings / out of bounds!";
            return message;
        } else {
            message = "use WASD to move!";
            return message;
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

    /**
     * world map
     * masih bingung parameternya apa aja,
     * atau mending taro di player aja biar lebih gampang akses data-datanya
     */
    public void worldMap(/** Player player, NPC npc */){
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while(running){
            System.out.println("=== World Map ===");
            System.out.println("1. NPC's House");
            System.out.println("2. Fishing Location");
            System.out.println("3. Store");
            System.out.println("** Type the number to navigate through the world map **");
            System.out.println("** Write 'back' to go to the previous section **");
            System.out.println("");
            System.out.print("Place to visit: ");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "1" -> {
                    System.out.println("=== NPC's House ===");
                    System.out.println("1. Mayor Tedi");
                    System.out.println("2. Caroline");
                    System.out.println("3. Perry");
                    System.out.println("4. Dasco");
                    System.out.println("5. Emily");
                    System.out.println("6. Abigail");
                    /**
                    * do NPC action
                    */
                }
                case "2" -> {
                    System.out.println("=== Fishing Locations ===");
                    System.out.println("1. Forest River");
                    System.out.println("2. Mountain Lake");
                    System.out.println("3. Ocean");
                    /**
                     * Fish based on the location
                     */
                }
                case "3" -> {
                    System.out.println("=== Store ===");
                    /**
                     * Show item name and price,
                     * decrease player gold,
                     * and add item to player's inventory after buying
                     */
                }
                case "back" -> {
                    break;
                }
                default -> {
                    System.out.println("Input is not valid.");
                }
            }

            if(input.equals("back")){
                break;
            }
        }
    }
}