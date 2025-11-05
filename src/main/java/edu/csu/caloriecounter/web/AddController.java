package edu.csu.caloriecounter.web;

import edu.csu.caloriecounter.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddController {
    private final LogService service;
    public AddController(LogService service) { this.service = service; }

    @GetMapping("/add")
    public String add(Model model) { return "add"; }

    @PostMapping("/quick-add")
    public String quickAdd(@RequestParam String description,
                           @RequestParam int calories,
                           @RequestParam(defaultValue="0") int protein,
                           @RequestParam(defaultValue="0") int carbs,
                           @RequestParam(defaultValue="0") int fat,
                           @RequestParam(defaultValue="SNACKS") String mealType) {
        service.addQuick(description, calories, protein, carbs, fat, mealType);
        return "redirect:/dashboard";
    }
}
