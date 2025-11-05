package edu.csu.caloriecounter.repo;

import edu.csu.caloriecounter.domain.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
    List<LogEntry> findByDate(LocalDate date);
    List<LogEntry> findByDateBetweenOrderByDateDesc(LocalDate start, LocalDate end);
}
