package org.dotheyhaveany.server.report;

import static org.dotheyhaveany.server.report.GroceryItemCategory.*;

public enum GroceryItem {
    FLOUR(FOOD),
    PASTA(FOOD),
    YEAST(FOOD),

    BLEACH(HOUSEHOLD),
    DISINFECTING_WIPES(HOUSEHOLD),
    HAND_SANITIZER(HOUSEHOLD),
    PAPER_TOWELS(HOUSEHOLD),
    TOILET_PAPER(HOUSEHOLD),

    ACETAMINOPHEN(MEDICAL),
    IBUPROFEN(MEDICAL),
    ISOPROPYL_ALCOHOL(MEDICAL);

    private final GroceryItemCategory category;

    GroceryItem(final GroceryItemCategory category) {
        this.category = category;
    }

    public GroceryItemCategory getCategory() {
        return category;
    }
}
