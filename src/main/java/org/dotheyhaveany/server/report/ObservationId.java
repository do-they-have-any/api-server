package org.dotheyhaveany.server.report;

import java.io.Serializable;

class ObservationId implements Serializable {
    private final ShoppingReport report;
    private final GroceryItem groceryItem;

    protected ObservationId() {
        this(null, null);
    }

    public ObservationId(final ShoppingReport report, final GroceryItem groceryItem) {
        this.report = report;
        this.groceryItem = groceryItem;
    }
}
