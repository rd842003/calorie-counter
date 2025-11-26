package edu.csu.caloriecounter.web;

import edu.csu.caloriecounter.domain.FoodItem;
import edu.csu.caloriecounter.domain.MealType;
import edu.csu.caloriecounter.service.FoodCatalogService;
import edu.csu.caloriecounter.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

/**
 * Controller responsible for the add/quick-add UI endpoints.
 *
 * Provides the add form view, including preset food selections sourced from the Excel catalog,
 * and handles quick-add POSTs that create an entry for today using the
 * {@link LogService#addQuick} helper.
 */
@Controller
public class AddController {
    private final LogService service;
    private final FoodCatalogService catalogService;

    public AddController(LogService service, FoodCatalogService catalogService) {
        this.service = service;
        this.catalogService = catalogService;
    }

    /**
     * Display the add view.
     *
     * @param model the MVC model used by the Thymeleaf view
     * @return the view name ("add")
     */
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("foods", catalogService.getCatalog());
        return "add";
    }

    /**
     * Handle quick add submissions from the add form and redirect back to the dashboard.
     *
     * @param description description of the food
     * @param calories calories value
     * @param protein protein grams (defaults to 0)
     * @param carbs carbs grams (defaults to 0)
     * @param fat fat grams (defaults to 0)
     * @param mealType MealType name (defaults to SNACKS)
     * @return redirect to the dashboard view
     */
    @PostMapping("/quick-add")
    public String quickAdd(@RequestParam String description,
                           @RequestParam int calories,
                           @RequestParam(defaultValue="0") int protein,
                           @RequestParam(defaultValue="0") int carbs,
                           @RequestParam(defaultValue="0") int fat,
                           @RequestParam(defaultValue="SNACKS") String mealType) {
        MealType resolvedMealType = resolveMealType(mealType);

        service.addQuick(description, calories, protein, carbs, fat, mealType);
        catalogService.addToCatalog(new FoodItem(description, calories, protein, carbs, fat, resolvedMealType));
        return "redirect:/dashboard";
    }

    private MealType resolveMealType(String mealType) {
        String normalized = mealType == null ? MealType.SNACKS.name() : mealType.trim().toUpperCase(Locale.ROOT);
        try {
            return MealType.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            return MealType.SNACKS;
        }
    }
}
