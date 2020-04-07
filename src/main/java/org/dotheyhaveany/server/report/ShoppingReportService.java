package org.dotheyhaveany.server.report;

import org.dotheyhaveany.server.store.Store;

import java.util.Map;
import java.util.Optional;

public interface ShoppingReportService {

    ShoppingReport saveReport(Store store, ShoppingReportPerspective perspective, Map<GroceryItem, ItemAvailability> observations);

    Optional<InventoryReport> getInventoryReport(Store store);
}
