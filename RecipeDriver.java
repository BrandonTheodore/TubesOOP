import java.util.*;

public class RecipeDriver {
    public static void main(String[] args) {
        RecipeManager.initRecipes();

        List<Recipe> all = RecipeManager.getAllRecipes();

        System.out.println("\nResep yang sudah dibuka:");
        for (Recipe r : all) {
            if (r.isUnlocked()) {
                System.out.println("- " + r.getRecipeName());
            }
        }

        System.out.println("\nResep yang masih terkunci:");
        for (Recipe r : all) {
            if (!r.isUnlocked()) {
                System.out.println("- " + r.getRecipeName() + " (Unlock via: " + r.getUnlockInfo() + ")");
            }
        }

        Recipe pumpkinPie = RecipeManager.getRecipeByName("Fish Sandwich");
        if (pumpkinPie != null) {
            System.out.println("\nBahan untuk membuat " + pumpkinPie.getRecipeName() + ":");
            pumpkinPie.printIngredientsByName(pumpkinPie.getRecipeName());
        } else {
            System.out.println("Resep Pumpkin Pie tidak ditemukan.");
        }
    }
}
