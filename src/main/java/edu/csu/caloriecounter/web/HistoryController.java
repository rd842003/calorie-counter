package edu.csu.caloriecounter.web;

import edu.csu.caloriecounter.domain.LogEntry;
import edu.csu.caloriecounter.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

/**
 * Controller that provides a simple history view of recent log entries.
 */
@Controller
public class HistoryController {
    private final LogService service;
    public HistoryController(LogService service) { this.service = service; }

    /**
     * Show a paginated (via days parameter) history list. Defaults to the last 7 days.
     *
     * @param model MVC model provided to the Thymeleaf view
     * @param days number of days of history to display (defaults to 7)
     * @return the history view name
     */
    @GetMapping("/history")
    public String history(Model model, @RequestParam(defaultValue = "7") int days) {
        List<LogEntry> entries = service.lastNDays(days);
        model.addAttribute("entries", entries);
        model.addAttribute("days", days);
        return "history";
    }
}