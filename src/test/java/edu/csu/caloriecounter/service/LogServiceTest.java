package edu.csu.caloriecounter.service;

import edu.csu.caloriecounter.domain.LogEntry;
import edu.csu.caloriecounter.domain.MealType;
import edu.csu.caloriecounter.repo.LogEntryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LogServiceTest {
    private LogEntryRepository repo;
    private LogService service;

    @BeforeEach
    void setUp() {
        repo = mock(LogEntryRepository.class);
        service = new LogService(repo);
    }

    @Test
    void totalsForDateAggregatesAllNutrients() {
        LocalDate date = LocalDate.of(2024, 10, 1);
        when(repo.findByDate(date)).thenReturn(List.of(
            new LogEntry(date, "Eggs", 200, 18, 0, 12, MealType.BREAKFAST),
            new LogEntry(date, "Salad", 300, 12, 30, 10, MealType.LUNCH)
        ));

        Map<String, Integer> totals = service.totalsForDate(date);

        assertThat(totals)
            .containsEntry("calories", 500)
            .containsEntry("protein", 30)
            .containsEntry("carbs", 30)
            .containsEntry("fat", 22);
    }

    @Test
    void totalsForDateReturnsZerosWhenEmpty() {
        LocalDate date = LocalDate.of(2024, 10, 2);
        when(repo.findByDate(date)).thenReturn(List.of());

        Map<String, Integer> totals = service.totalsForDate(date);

        assertThat(totals)
            .containsEntry("calories", 0)
            .containsEntry("protein", 0)
            .containsEntry("carbs", 0)
            .containsEntry("fat", 0);
    }

    @Test
    void lastNDaysUsesAtLeastOneDay() {
        LocalDate today = LocalDate.now();
        when(repo.findByDateBetweenOrderByDateDesc(any(), any())).thenReturn(List.of());

        service.lastNDays(0);

        ArgumentCaptor<LocalDate> startCaptor = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<LocalDate> endCaptor = ArgumentCaptor.forClass(LocalDate.class);
        verify(repo).findByDateBetweenOrderByDateDesc(startCaptor.capture(), endCaptor.capture());

        assertThat(endCaptor.getValue()).isEqualTo(today);
        assertThat(startCaptor.getValue()).isEqualTo(today);
    }

    @Test
    void addQuickNormalizesValidMealType() {
        ArgumentCaptor<LogEntry> captor = ArgumentCaptor.forClass(LogEntry.class);

        service.addQuick("Toast", 150, 6, 20, 4, "breakfast");

        verify(repo).save(captor.capture());
        LogEntry saved = captor.getValue();
        assertThat(saved.getDescription()).isEqualTo("Toast");
        assertThat(saved.getMealType()).isEqualTo(MealType.BREAKFAST);
        assertThat(saved.getDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void addQuickFallsBackToSnacksOnInvalidMealType() {
        ArgumentCaptor<LogEntry> captor = ArgumentCaptor.forClass(LogEntry.class);

        service.addQuick("Chips", 220, 3, 24, 14, "invalid");

        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getMealType()).isEqualTo(MealType.SNACKS);
    }
}
