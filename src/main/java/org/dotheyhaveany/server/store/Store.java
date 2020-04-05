package org.dotheyhaveany.server.store;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Store {

    @Id
    @GeneratedValue
    private final int id;

    private final String displayName;

    private final String address;

    protected Store() {
        this(-1, null, null);
    }

    public Store(final int id, final String displayName, final String address) {
        this.id = id;
        this.displayName = displayName;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAddress() {
        return address;
    }
}
