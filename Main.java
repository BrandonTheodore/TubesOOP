// import java.util.*;
// public class Main {
//     public static void main(String[] args){
//         MapDriver game = new MapDriver();
//         game.Play();
        
//     }
// }

import java.lang.*;
import java.util.Scanner;

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
    
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Map gameMap = new Map();
        
        // Generate the initial map
        try {
            gameMap.generateMap();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        
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
            boolean houseNearby = false;
            boolean pondNearby = false;
            boolean shippingBinNearby = false;
            
            // Display player status and surroundings
            if(message.equals("nothing")){
                System.out.println("");
            } else {
                System.out.println(ANSI_RED + message + ANSI_RESET);
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
            System.out.println("- P: Plant crops");
            System.out.println("- HR: Harvest crops");
            System.out.println("- Q: Quit game");

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
                    if(!moving){
                        message = "Cannot move through buildings / out of bounds!";
                    }
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
                case "r" -> {
                    // recover land
                    if(gameMap.isTilled()){
                        gameMap.setCurrentTile('.');
                        message = "You recovered the land!";
                    } else if (gameMap.isPlanted() || gameMap.isHarvestReady()){
                        message = "The soil is already used for planting!";
                    } else {
                        message = "No land to recover!";
                    }
                }
                case "p" -> {
                    // Plant on tilled soil
                    if (gameMap.isTilled()) {
                        gameMap.setCurrentTile('v');
                        message = "You planted seeds!";
                    } else {
                        message = "Cannot plant here! Till the soil first.";
                    }
                }
                case "hr" -> {
                    if(gameMap.isHarvestReady()){
                        gameMap.setCurrentTile('.');
                        message = "You harvested the crops!";
                    } else if (gameMap.isPlanted()) {
                        message = "Crop is not ready for harvest";
                    } else {
                        message = "No plant to harvest";
                    }
                }
                case "q" -> {
                    System.out.println("Quitting game. Goodbye!");
                    running = false;
                }
                case "h" -> {
                    if(houseNearby){
                        System.out.println("=== House Menu ===");
                        Thread.sleep(2000);
                    } else {
                        message = "You are not near a house!";
                    }
                }
                case "f" -> {
                    if(pondNearby){
                        System.out.println("Fishing in a Pond..");
                        Thread.sleep(2000);
                    } else {
                        message = "You are not near a pond!";
                    }
                }
                case "sb" -> {
                    if(shippingBinNearby){
                        System.out.println("=== Shipping Bin Menu ===");
                        Thread.sleep(2000);
                    } else {
                        message = "You are not near a shipping bin!";
                    }
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