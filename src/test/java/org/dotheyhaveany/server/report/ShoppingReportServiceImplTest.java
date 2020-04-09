package org.dotheyhaveany.server.report;

import org.dotheyhaveany.server.store.Store;
import org.dotheyhaveany.server.store.StoreRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShoppingReportServiceImplTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ShoppingReportRepository reportRepository;

    @Autowired
    private ShoppingReportServiceImpl reportService;

    @AfterEach
    void tearDown() {
        reportRepository.deleteAll();
        storeRepository.deleteAll();
    }

    @Test
    void saveReport() {
        final Store store = storeRepository.save(new Store("Test store", "Test address"));

        final ShoppingReport savedReport = reportService.saveReport(store,
                Instant.now(),
                ShoppingReportPerspective.SHOPPER,
                Collections.singletonMap(GroceryItem.FLOUR, ItemAvailability.IN_STOCK));

        assertNotNull(savedReport.getId());

        final Optional<ShoppingReport> maybeReport = reportRepository.findById(savedReport.getId());

        assertTrue(maybeReport.isPresent());
        assertEquals(savedReport, maybeReport.get());
    }

    @Test
    void getInventoryReport() {
        final Store store = storeRepository.save(new Store("Test store", "Test address"));

        assertFalse(reportService.getInventoryReport(store).isPresent());

        final Instant currentReportTimestamp = Instant.now();
        final Instant staleReportTimestamp = currentReportTimestamp.minus(Duration.ofHours(3));

        {
            final Map<GroceryItem, ItemAvailability> observations = new EnumMap<>(GroceryItem.class);
            observations.put(GroceryItem.FLOUR, ItemAvailability.IN_STOCK);
            observations.put(GroceryItem.ISOPROPYL_ALCOHOL, ItemAvailability.OUT_OF_STOCK);
            observations.put(GroceryItem.TOILET_PAPER, ItemAvailability.UNKNOWN);

            reportService.saveReport(store, currentReportTimestamp, ShoppingReportPerspective.SHOPPER, observations);
        }

        {
            final Map<GroceryItem, ItemAvailability> observations = new EnumMap<>(GroceryItem.class);
            observations.put(GroceryItem.FLOUR, ItemAvailability.LIMITED);
            observations.put(GroceryItem.YEAST, ItemAvailability.IN_STOCK);
            observations.put(GroceryItem.ISOPROPYL_ALCOHOL, ItemAvailability.LIMITED);

            reportService.saveReport(store, staleReportTimestamp, ShoppingReportPerspective.SHOPPER, observations);
        }

        final Optional<InventoryReport> maybeReport = reportService.getInventoryReport(store);

        assertTrue(maybeReport.isPresent());

        final InventoryReport inventoryReport = maybeReport.get();

        assertEquals(currentReportTimestamp, inventoryReport.getLastUpdate());
        assertEquals(store, inventoryReport.getStore());
        assertEquals(ItemAvailability.IN_STOCK, inventoryReport.getAvailabilityByGroceryItem().get(GroceryItem.FLOUR));
        assertEquals(ItemAvailability.IN_STOCK, inventoryReport.getAvailabilityByGroceryItem().get(GroceryItem.YEAST));
        assertEquals(ItemAvailability.OUT_OF_STOCK, inventoryReport.getAvailabilityByGroceryItem().get(GroceryItem.ISOPROPYL_ALCOHOL));
        assertFalse(inventoryReport.getAvailabilityByGroceryItem().containsKey(GroceryItem.TOILET_PAPER));
    }

    @Test
    void getObservationWeight() {
        final Instant currentTime = Instant.now();

        assertDoesNotThrow(() -> ShoppingReportServiceImpl.getObservationWeight(currentTime, currentTime));

        final double recentWeight =
                ShoppingReportServiceImpl.getObservationWeight(currentTime.minus(Duration.ofHours(6)), currentTime);

        final double olderWeight =
                ShoppingReportServiceImpl.getObservationWeight(currentTime.minus(Duration.ofHours(12)), currentTime);

        assertTrue(recentWeight > 0 && recentWeight < 1);
        assertTrue(olderWeight > 0 && olderWeight < 1);
        assertTrue(recentWeight > olderWeight);
    }

    @Test
    void getMostLikelyAvailability() {
        assertThrows(IllegalArgumentException.class,
                () -> ShoppingReportServiceImpl.getMostLikelyAvailability(Collections.emptyMap()));

        {
            final Map<ItemAvailability, Double> weightsByAvailability = new EnumMap<>(ItemAvailability.class);
            weightsByAvailability.put(ItemAvailability.IN_STOCK, 0.9);
            weightsByAvailability.put(ItemAvailability.LIMITED, 0.8);
            weightsByAvailability.put(ItemAvailability.OUT_OF_STOCK, 0.7);

            assertEquals(ItemAvailability.IN_STOCK, ShoppingReportServiceImpl.getMostLikelyAvailability(weightsByAvailability));
        }
    }
}
