package org.dotheyhaveany.server.report;

import org.dotheyhaveany.server.store.Store;
import org.dotheyhaveany.server.store.StoreRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ShoppingReportRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ShoppingReportRepository reportRepository;

    @AfterEach
    void tearDown() {
        reportRepository.deleteAll();
        storeRepository.deleteAll();
    }

    @Test
    void getRecentForStore() {
        final Store store = storeRepository.save(new Store("Test store", "Test address"));

        final Instant freshReportTime = Instant.now();
        final Instant queryTime = freshReportTime.minus(Duration.ofDays(1));
        final Instant staleReportTime = queryTime.minus(Duration.ofDays(1));

        final ShoppingReport staleReport = new ShoppingReport(store, staleReportTime, ShoppingReportPerspective.SHOPPER);
        staleReport.setObservations(Collections.singleton(new Observation(staleReport, GroceryItem.FLOUR, ItemAvailability.OUT_OF_STOCK)));

        final ShoppingReport freshReport = new ShoppingReport(store, freshReportTime, ShoppingReportPerspective.SHOPPER);
        freshReport.setObservations(Collections.singleton(new Observation(freshReport, GroceryItem.FLOUR, ItemAvailability.IN_STOCK)));

        reportRepository.save(staleReport);
        reportRepository.save(freshReport);

        assertEquals(Collections.singletonList(freshReport), reportRepository.getRecentForStore(store, queryTime));
    }
}
