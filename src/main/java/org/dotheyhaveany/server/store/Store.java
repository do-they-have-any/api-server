package org.dotheyhaveany.server.store;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private final String displayName;

    private final String address;

    protected Store() {
        this(null, null);
    }

    public Store(final String displayName, final String address) {
        this.displayName = displayName;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", displayName='" + displayName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Store store = (Store) o;
        return displayName.equals(store.displayName) &&
                address.equals(store.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(displayName, address);
    }
}
