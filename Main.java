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
            System.out.println("- HR: Harvest crop");
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
                    boolean outOfBounds = gameMap.isOutOfBound(input);
                    if(!moving){
                        if(outOfBounds){
                            System.out.print("Do you want to exit the farm? (y/n) ");
                            input = scanner.nextLine().toLowerCase();
                            if(input.equals("y")){
                                gameMap.worldMap();
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
                case "wa" -> {
                    // water crops, kayaknya perlu thread?
                    if(gameMap.isWatered()){
                        message = "The plant is already watered";
                    } else if(gameMap.isPlanted()){
                        gameMap.setCurrentTile('w');
                        message = "You watered the crop!";
                    } else {
                        message = "There's no plant to water";
                    }
                }
                case "hr" -> {
                    if(gameMap.isHarvestReady()){
                        gameMap.setCurrentTile('.');
                        message = "You harvested the crops!";
                    } else if (gameMap.isPlanted()) {
                        message = "Crop is not ready for harvest";
                    } else if (gameMap.isWithered()) {
                        message = "Crop has withered";
                    }else {
                        message = "No plant to harvest";
                    }
                }
                case "q" -> {
                    System.out.println("Quitting game. Goodbye!");
                    running = false;
                }
                case "h" -> {
                    if(houseNearby){
                        while(true){
                            System.out.println("=== House Menu ===");
                            // print action list
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

                            input = scanner.nextLine().toLowerCase();
                            boolean inputIsInteger = isInteger(input);

                            if(input.equals("b")){
                                break;
                            }

                            if(inputIsInteger){
                                System.out.println("Player do action x");
                            } else {
                                message = "Input must be a number!";
                            }
                        }
                        message = "You exited the house";
                    } else {
                        message = "You are not near a house!";
                    }
                }
                case "f" -> {
                    if(pondNearby){
                        System.out.println("Fishing in a Pond..");
                        message = "You got a fish/trash from the pond";
                    } else {
                        message = "You are not near a pond!";
                    }
                }
                case "sb" -> {
                    if(shippingBinNearby){
                        while (true) { 
                            System.out.println("=== Shipping Bin Menu ==="); 
                            System.out.println("1. Add item to Shipping Bin");
                            System.out.println("2. Show current items, quantity, and price in Shipping Bin");
                            System.out.println("3. Sell Shipping Bin");
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
                            
                            input = scanner.nextLine().toLowerCase();
                            boolean inputIsInteger = isInteger(input);

                            if(input.equals("b")){
                                break;
                            }

                            if(inputIsInteger){
                                switch(input) {
                                    case "1" -> {
                                        System.out.println("What item do you want to add?");
                                        System.out.print("Input item name: ");
                                        input = scanner.nextLine();
                                        while(true){
                                            System.out.println("** Type 'b' to go to the previous section **");
                                            System.out.println("How much of this item do you want to add?");
                                            System.out.print("Input Item Quantity: "); 
                                            input = scanner.nextLine().toLowerCase();
                                            inputIsInteger = isInteger(input);

                                            if(input.equals("b")){
                                                break;
                                            }

                                            if(inputIsInteger){
                                                int itemQuantity = Integer.parseInt(input);
                                                message = "Item x added to the shipping bin";
                                                break;
                                            } else {
                                                System.out.println("Input must be an integer!");
                                            }
                                        }
                                    }
                                    case "2" -> {
                                        while (true) { 
                                            System.out.println("=== Current Shipping Bin ===");
                                            System.out.println("** Type 'b' to go to the previous section **");
                                            input = scanner.nextLine().toLowerCase();

                                            if(input.equals("b")){
                                                break;
                                            }
                                        }
                                    }
                                    case "3" -> {
                                        System.out.println("Shipping Bin Sold!");
                                    }
                                    case "b" -> {
                                        break;
                                    }
                                }
                            } else {
                                message = "Input must be a number";
                            }
                        }
                        message = "You exited the shipping bin";
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
                    case Map.WATERED -> System.out.print(ANSI_BLUE + "x " + ANSI_RESET);
                    case Map.HARVESTABLE -> System.out.println(ANSI_CYAN + "c " + ANSI_RESET);
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
            case Map.WATERED -> "Watered";
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
}