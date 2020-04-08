package org.dotheyhaveany.server.store;

import org.dotheyhaveany.server.report.GroceryItem;
import org.dotheyhaveany.server.report.ItemAvailability;
import org.dotheyhaveany.server.report.ShoppingReportPerspective;

import java.util.Map;

public class ShoppingReportForm {

    private int storeId;

    private int ageInHours;

    private ShoppingReportPerspective perspective;

    private Map<GroceryItem, ItemAvailability> observations;

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(final int storeId) {
        this.storeId = storeId;
    }

    public int getAgeInHours() {
        return ageInHours;
    }

    public void setAgeInHours(final int ageInHours) {
        this.ageInHours = ageInHours;
    }

    public ShoppingReportPerspective getPerspective() {
        return perspective;
    }

    public void setPerspective(final ShoppingReportPerspective perspective) {
        this.perspective = perspective;
    }

    public Map<GroceryItem, ItemAvailability> getObservations() {
        return observations;
    }

    public void setObservations(final Map<GroceryItem, ItemAvailability> observations) {
        this.observations = observations;
    }
}
