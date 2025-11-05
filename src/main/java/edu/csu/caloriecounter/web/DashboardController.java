package edu.csu.caloriecounter.web;

import edu.csu.caloriecounter.domain.LogEntry;
import edu.csu.caloriecounter.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {
    private final LogService service;
    public DashboardController(LogService service) { this.service = service; }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        Map<String,Integer> totals = service.todayTotals();
        List<LogEntry> entries = service.todayEntries();
        model.addAttribute("totals", totals);
        model.addAttribute("entries", entries);

        int goalCalories = 2200;
        model.addAttribute("goalCalories", goalCalories);
        model.addAttribute("goalProtein", 160);
        model.addAttribute("goalCarbs", 250);
        model.addAttribute("goalFat", 70);

        int cal = totals.getOrDefault("calories", 0);
        int percent = (int) Math.min(100, Math.round(100.0 * cal / Math.max(goalCalories, 1)));
        model.addAttribute("percent", percent);

        return "dashboard";
    }
}
