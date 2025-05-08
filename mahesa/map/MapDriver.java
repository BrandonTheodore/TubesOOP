import java.util.Scanner;

/**
 * Driver class for the updated Map implementation of a farming game.
 * This class demonstrates the functionality of the Map class
 * by allowing player movement and interaction with the game world.
 */
public class MapDriver {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m"; 
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_CYAN = "\u001B[36m";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map gameMap = new Map();
        
        // Generate the initial map
        gameMap.generateMap();
        
        boolean running = true;
        String input;
        String message = "nothing";
        
        // Game loop
        while (running) {
            // Print the map with color
            printColorMap(gameMap.getMap());
            
            // Get surrounding tiles information
            char[] surroundingTiles = gameMap.getSurroundingTiles();
            char currentTile = gameMap.getCurrentTile();
            
            // Display player status and surroundings
            if(message.equals("nothing")){
                System.out.println("");
            } else {
                System.out.println(ANSI_RED + message + ANSI_RESET);
            }
            System.out.println("");
            
            System.out.println("Player position: (" + gameMap.getPlayerX() + ", " + gameMap.getPlayerY() + ")");
            System.out.println("Current tile: " + getTileDescription(currentTile));
            System.out.println("Surroundings: Up[" + getTileDescription(surroundingTiles[0]) + 
                               "], Down[" + getTileDescription(surroundingTiles[1]) + 
                               "], Left[" + getTileDescription(surroundingTiles[2]) + 
                               "], Right[" + getTileDescription(surroundingTiles[3]) + "]");
            
            System.out.println("Controls:");
            System.out.println("- WASD: Move player");
            System.out.println("- T: Till soil");
            System.out.println("- P: Plant crops");
            System.out.println("- M: Access world map (not finished)");
            System.out.println("- Q: Quit game");
            System.out.println();
            
            // Get player input
            System.out.print("Enter command: ");
            input = scanner.nextLine().toLowerCase();
            
            // Process input
            switch (input) {
                case "w", "a", "s", "d" -> {
                    message = gameMap.move(input);
                }
                case "t" -> {
                    // Till soil if possible
                    if (gameMap.isTillable()) {
                        gameMap.setCurrentTile('t');
                        message = "You tilled the soil!";
                    } else {
                        message = "Cannot till here!";
                    }
                }
                case "p" -> {
                    // Plant on tilled soil
                    if (gameMap.isTilled()) {
                        gameMap.setCurrentTile('l');
                        message = "You planted seeds!";
                    } else {
                        message = "Cannot plant here! Till the soil first.";
                    }
                }
                case "m" -> {
                    // Access world map
                    TerminalClear.clearScreen();
                    System.out.println("Opening world map...");
                    gameMap.worldMap();
                }
                case "q" -> {
                    System.out.println("Quitting game. Goodbye!");
                    running = false;
                }
                default -> message = "Unknown command. Use WASD to move, T to till, P to plant, M for world map, Q to quit.";
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
        System.out.println("Map:");
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                switch(map[row][col]) {
                    case Map.TILLABLE -> System.out.print(ANSI_GREEN + ". " + ANSI_RESET);
                    case Map.TILLED -> System.out.print(ANSI_YELLOW + "t " + ANSI_RESET);
                    case Map.PLANTED -> System.out.print(ANSI_GREEN + "l " + ANSI_RESET);
                    case Map.HOUSE -> System.out.print(ANSI_RED + "h " + ANSI_RESET);
                    case Map.BIN -> System.out.print(ANSI_YELLOW + "s " + ANSI_RESET);
                    case Map.POND -> System.out.print(ANSI_BLUE + "o " + ANSI_RESET);
                    case Map.WORLDMAP -> System.out.print(ANSI_CYAN + "w " + ANSI_RESET);
                    case Map.PLAYER -> System.out.print(ANSI_RED + "P " + ANSI_RESET);
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
            case Map.WORLDMAP -> "World Map";
            case Map.PLAYER -> "Player";
            case '\0' -> "Edge of Map";
            default -> "Unknown";
        };
    }
}