import java.util.*;

public class RecipeManager {
    private static List<Recipe> allRecipes = new ArrayList<>();
    private static FoodManager foodm = new FoodManager();

    public static void initRecipes() {
        Recipe baguette = new Recipe("Baguette");
        baguette.setFood(foodm.getFoodByName("Baguette"));
        baguette.setUnlocked(true);
        baguette.setUnlockInfo("Default");
        baguette.addIngredient("Wheat", 3);

        Recipe wine = new Recipe("Wine");
        wine.setFood(foodm.getFoodByName("Wine"));
        wine.setUnlocked(true);
        wine.setUnlockInfo("Default");
        wine.addIngredient("Grape", 2);

        Recipe pumpkinPie = new Recipe("Pumpkin Pie");
        pumpkinPie.setFood(foodm.getFoodByName("Pumpkin Pie"));
        pumpkinPie.setUnlocked(true);
        pumpkinPie.setUnlockInfo("Default");
        pumpkinPie.addIngredient("Egg", 1);
        pumpkinPie.addIngredient("Wheat", 1);
        pumpkinPie.addIngredient("Pumpkin", 1);

        Recipe spakborSalad = new Recipe("Spakbor Salad");
        spakborSalad.setFood(foodm.getFoodByName("Spakbor Salad"));
        spakborSalad.setUnlocked(true);
        spakborSalad.setUnlockInfo("Default");
        spakborSalad.addIngredient("Melon", 1);
        spakborSalad.addIngredient("Cranberry", 1);
        spakborSalad.addIngredient("Blueberry", 1);
        spakborSalad.addIngredient("Tomato", 1);

        Recipe fishAndChips = new Recipe("Fish n' Chips");
        fishAndChips.setFood(foodm.getFoodByName("Fish n' Chips"));
        fishAndChips.setUnlocked(false);
        fishAndChips.setUnlockInfo("Beli di store");
        fishAndChips.addIngredient("Any Fish", 2);
        fishAndChips.addIngredient("Wheat", 1);
        fishAndChips.addIngredient("Potato", 1);

        Recipe sashimi = new Recipe("Sashimi");
        sashimi.setFood(foodm.getFoodByName("Sashimi"));
        sashimi.setUnlocked(false);
        sashimi.setUnlockInfo("Setelah memancing 10 ikan");
        sashimi.addIngredient("Salmon", 3);

        Recipe fugu = new Recipe("Fugu");
        fugu.setFood(foodm.getFoodByName("Fugu"));
        fugu.setUnlocked(false);
        fugu.setUnlockInfo("Memancing pufferfish");
        fugu.addIngredient("Pufferfish", 1);

        Recipe veggieSoup = new Recipe("Veggie Soup");
        veggieSoup.setFood(foodm.getFoodByName("Veggie Soup"));
        veggieSoup.setUnlocked(false);
        veggieSoup.setUnlockInfo("Memanen untuk pertama kalinya");
        veggieSoup.addIngredient("Cauliflower", 1);
        veggieSoup.addIngredient("Parsnip", 1);
        veggieSoup.addIngredient("Potato", 1);
        veggieSoup.addIngredient("Tomato", 1);

        Recipe fishStew = new Recipe("Fish Stew");
        fishStew.setFood(foodm.getFoodByName("Fish Stew"));
        fishStew.setUnlocked(false);
        fishStew.setUnlockInfo("Dapatkan Hot Pepper terlebih dahulu agar bisa membuka resepnya");
        fishStew.addIngredient("Any Fish", 2);
        fishStew.addIngredient("Hot Pepper", 1);
        fishStew.addIngredient("Cauliflower", 2);

        Recipe fishSandwich = new Recipe("Fish Sandwich");
        fishSandwich.setFood(foodm.getFoodByName("Fish Sandwich"));
        fishSandwich.setUnlocked(false);
        fishSandwich.setUnlockInfo("Beli di store");
        fishSandwich.addIngredient("Any Fish", 1);
        fishSandwich.addIngredient("Wheat", 2);
        fishSandwich.addIngredient("Tomato", 1);
        fishSandwich.addIngredient("Hot Pepper", 1);

        Recipe theLegendOfSpakbor = new Recipe("The Legend of Spakbor");
        theLegendOfSpakbor.setFood(foodm.getFoodByName("The Legend of Spakbor"));
        theLegendOfSpakbor.setUnlocked(false);
        theLegendOfSpakbor.setUnlockInfo("Memancing Legend");
        theLegendOfSpakbor.addIngredient("Legend", 1);
        theLegendOfSpakbor.addIngredient("Potato", 2);
        theLegendOfSpakbor.addIngredient("Parsnip", 1);
        theLegendOfSpakbor.addIngredient("Tomato", 1);
        theLegendOfSpakbor.addIngredient("Eggplant", 1);

        allRecipes.add(baguette);
        allRecipes.add(wine);
        allRecipes.add(pumpkinPie);
        allRecipes.add(spakborSalad);
        allRecipes.add(fishAndChips);
        allRecipes.add(sashimi);
        allRecipes.add(fugu);
        allRecipes.add(veggieSoup);
        allRecipes.add(fishStew);
        allRecipes.add(fishSandwich);
        allRecipes.add(theLegendOfSpakbor);       
    }

    public static List<Recipe> getAllRecipes() {
        return allRecipes;
    }

    public static Recipe getRecipeByName(String name) {
        for (Recipe recipe : allRecipes) {
            if (recipe.getRecipeName().equalsIgnoreCase(name)) {
                return recipe;
            }
        }
        return null;
    }
}
