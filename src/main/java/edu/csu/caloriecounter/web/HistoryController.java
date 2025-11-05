package edu.csu.caloriecounter.web;

import edu.csu.caloriecounter.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HistoryController {
    private final LogService service;
    public HistoryController(LogService service) { this.service = service; }

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("history", service.lastNDays(14));
        return "history";
    }
}
