package edu.csu.caloriecounter.repo;

import edu.csu.caloriecounter.domain.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository (DAO) interface for {@link LogEntry} persistence operations.
 *
 * Extends Spring Data JPA's {@link JpaRepository} to inherit common CRUD operations and
 * defines a few query methods used by the application service layer.
 */
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
    /**
     * Find all LogEntry instances for a given date.
     *
     * @param date the LocalDate to filter by
     * @return list of entries matching the date (may be empty)
     */
    List<LogEntry> findByDate(LocalDate date);

    /**
     * Find log entries within a date range and return them ordered by date descending.
     *
     * @param start inclusive start date
     * @param end inclusive end date
     * @return list of LogEntry instances between start and end ordered newest-first
     */
    List<LogEntry> findByDateBetweenOrderByDateDesc(LocalDate start, LocalDate end);
}
