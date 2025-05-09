import java.util.*;

public class Recipe {
    private String recipeId;
    private Map<Item, Integer> ingredients;
    private Food food;
    private boolean isUnlocked;
    private String unlockInfo;

    // konstruktor
    public Recipe(String recipeId, Map<Item, Integer> ingredients, Food food, boolean isUnlocked, String unlockInfo) {
        this.recipeId = recipeId;
        this.ingredients = ingredients;
        this.food = food;
        this.isUnlocked = isUnlocked;
        this.unlockInfo = unlockInfo;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public Map<Item, Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<Item, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean isUnlocked) {
        this.isUnlocked = isUnlocked;
    }

    public String getUnlockInfo() {
        return unlockInfo;
    }

    public void setUnlockInfo(String unlockInfo) {
        this.unlockInfo = unlockInfo;
    }

    public void printIngredients(Food food) {
        System.out.println("Berikut adalah bahan baku yang dibutuhkan untuk membuat " + food.getName());
        for (Map.Entry<Item, Integer> entry : ingredients.entrySet()) {
            System.out.println("- " + entry.getKey().getName() + " x" + entry.getValue());
        }
    }

    public void printRecipesList(List<Recipe> recipes) {
        System.out.println("---------------+---------------------------");
        System.out.printf("| %-12s | %-25s|\n", "recipe_id", "Nama Makanan");
        System.out.println("---------------+---------------------------");        

        for (Recipe recipe : recipes) {
            if (recipe.isUnlocked()) {
                System.out.printf("| %-12s | %-25s|\n", recipe.getRecipeId(), recipe.getFood().getName());
            }
        }
        System.out.println("---------------+---------------------------");
    }

    // public void printLockedRecipes() {
    //     System.out.println("Resep ini masih terkunci, silakan buka resep ini dengan cara: " + unlockInfo);
    // }
}
