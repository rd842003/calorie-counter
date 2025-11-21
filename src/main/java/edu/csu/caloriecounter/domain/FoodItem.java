package edu.csu.caloriecounter.domain;

/**
 * Simple value object representing a catalog food item loaded from an external source.
 *
 * <p>Each instance holds the nutritional information necessary to pre-fill the quick add
 * form: description, calories, protein, carbohydrates, fat, and the meal type the item
 * typically belongs to.</p>
 */
public class FoodItem {
    private final String description;
    private final int calories;
    private final int protein;
    private final int carbs;
    private final int fat;
    private final MealType mealType;

    /**
     * Create a new immutable food item.
     *
     * @param description human readable description of the food
     * @param calories calories contained in the item
     * @param protein grams of protein
     * @param carbs grams of carbohydrates
     * @param fat grams of fat
     * @param mealType default {@link MealType} classification
     */
    public FoodItem(String description, int calories, int protein, int carbs, int fat, MealType mealType) {
        this.description = description;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.mealType = mealType;
    }

    /** @return description of the food. */
    public String getDescription() { return description; }

    /** @return calorie amount for the food. */
    public int getCalories() { return calories; }

    /** @return protein grams for the food. */
    public int getProtein() { return protein; }

    /** @return carbohydrate grams for the food. */
    public int getCarbs() { return carbs; }

    /** @return fat grams for the food. */
    public int getFat() { return fat; }

    /** @return default {@link MealType} category. */
    public MealType getMealType() { return mealType; }
}
