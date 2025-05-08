/**
 * TerminalClear - A utility class for clearing the terminal screen in Java
 * 
 * This class provides methods to clear the terminal screen across different operating systems.
 * It detects the operating system and uses the appropriate command.
 */
public class TerminalClear {
    
    /**
     * Clears the terminal screen
     * Works across Windows, macOS/Linux, and other Unix-like systems
     */
    public static void clearScreen() {
        try {
            String operatingSystem = System.getProperty("os.name");
            
            // For Windows
            if (operatingSystem.contains("Windows")) {
                // For Windows, we can use the "cls" command
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            // For macOS, Linux, and Unix
            else {
                // For Unix-based systems, we can use ANSI escape codes
                System.out.print("\033[H\033[2J");
                System.out.flush();
                
                // Alternative approach using "clear" command on Unix systems
                // new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // If the above methods fail, fall back to printing newlines
            System.out.println("Failed to clear screen using system commands. Falling back to newlines.");
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    /**
     * Alternative method that uses only ANSI escape codes
     * May work better in some terminals that support ANSI
     */
    public static void clearScreenAnsi() {
        // ANSI escape code to clear screen and move cursor to top-left
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    /**
     * Example usage
     */
    public static void main(String[] args) {
        System.out.println("This text will be cleared...");
        
        try {
            // Wait for 2 seconds
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Clear the screen
        clearScreen();
        
        System.out.println("Screen has been cleared!");
    }
}