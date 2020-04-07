package org.dotheyhaveany.server.report;

import org.dotheyhaveany.server.store.Store;

import java.time.Instant;
import java.util.Map;

public class InventoryReport {

    private final Store store;
    private final Instant lastUpdate;

    private final Map<GroceryItem, ItemAvailability> availabilityByGroceryItem;

    public InventoryReport(final Store store, final Instant lastUpdate, final Map<GroceryItem, ItemAvailability> availabilityByGroceryItem) {
        this.store = store;
        this.lastUpdate = lastUpdate;

        this.availabilityByGroceryItem = availabilityByGroceryItem;
    }

    public Store getStore() {
        return store;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public Map<GroceryItem, ItemAvailability> getAvailabilityByGroceryItem() {
        return availabilityByGroceryItem;
    }
}
