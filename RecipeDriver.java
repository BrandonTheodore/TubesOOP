import java.util.*;

public class RecipeDriver {
    public static void main(String[] args) {
        Fish anyFish = new Fish("Any Fish", 100, 50, ItemCategory.FISH, Rarity.REGULAR, 10);
        Fish salmon = new Fish("Salmon", 200, 80, ItemCategory.FISH, Rarity.REGULAR, 20);
        Food wheat = new Food("Wheat", 30, 15, ItemCategory.FOOD, 0);
        Food potato = new Food("Potato", 40, 20, ItemCategory.FOOD, 0);
        Food grape = new Food("Grape", 35, 18, ItemCategory.FOOD, 0);

        // Recipe 1 : Fish n' Chips (locked)
        Map<Item, Integer> bahanFishChips = new HashMap<>();
        bahanFishChips.put(anyFish, 2);
        bahanFishChips.put(wheat, 1);
        bahanFishChips.put(potato, 1);
        Food fishChips = new Food("Fish n’ Chips", 120, 60, ItemCategory.FOOD, 25);
        Recipe recipe1 = new Recipe("recipe_1", bahanFishChips, fishChips, false, "Beli di store");

        // Recipe 2 : Wine (unlocked)
        Map<Item, Integer> bahanWine = new HashMap<>();
        bahanWine.put(grape, 2);
        Food wine = new Food("Wine", 80, 40, ItemCategory.FOOD, 15);
        Recipe recipe2 = new Recipe("recipe_5", bahanWine, wine, true, "Default");

        // Recipe 3 : Sashimi (unlocked)
        Map<Item, Integer> bahanSashimi = new HashMap<>();
        bahanSashimi.put(salmon, 3);
        Food sashimi = new Food("Sashimi", 100, 50, ItemCategory.FOOD, 20);
        Recipe recipe3 = new Recipe("recipe_3", bahanSashimi, sashimi, true, "Setelah memancing 10 ikan");

        List<Recipe> unlockedRecipes = new ArrayList<>();
        unlockedRecipes.add(recipe2);
        unlockedRecipes.add(recipe3);

        System.out.println("Daftar Resep:");
        recipe1.printRecipesList(unlockedRecipes);

        System.out.println();

        System.out.println("Bahan membuat Sashimi:");
        recipe3.printIngredients(recipe3.getFood());

        System.out.println();

        // if (!recipe1.isUnlocked()) {
        //     System.out.println("Info resep terkunci:");
        //     recipe1.printLockedRecipes();
        // }
    }
}
