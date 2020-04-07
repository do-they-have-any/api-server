package org.dotheyhaveany.server.report;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(ObservationId.class)
public class Observation {

    @Id
    @ManyToOne
    @JoinColumn(name = "shopping_report_id")
    private final ShoppingReport report;

    @Id
    @Enumerated(EnumType.STRING)
    private final GroceryItem groceryItem;

    @Enumerated(EnumType.STRING)
    private final ItemAvailability availability;

    protected Observation() {
        this(null, null, null);
    }

    public Observation(final ShoppingReport report, final GroceryItem groceryItem, final ItemAvailability availability) {
        this.report = report;
        this.groceryItem = groceryItem;
        this.availability = availability;
    }

    public ShoppingReport getReport() {
        return report;
    }

    public GroceryItem getGroceryItem() {
        return groceryItem;
    }

    public ItemAvailability getAvailability() {
        return availability;
    }

    @Override
    public String toString() {
        return "Observation{" +
                "groceryItem=" + groceryItem +
                ", availability=" + availability +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Observation that = (Observation) o;
        return groceryItem == that.groceryItem &&
                availability == that.availability;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groceryItem, availability);
    }
}
