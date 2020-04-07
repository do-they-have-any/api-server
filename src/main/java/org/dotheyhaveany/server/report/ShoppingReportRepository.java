package org.dotheyhaveany.server.report;

import org.dotheyhaveany.server.store.Store;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

public interface ShoppingReportRepository extends CrudRepository<ShoppingReport, Long> {

    @Query("SELECT report FROM ShoppingReport report where report.store = ?1 AND report.timestamp >= ?2 ORDER BY report.timestamp DESC")
    List<ShoppingReport> getRecentForStore(Store store, Instant since);
}
