package org.dotheyhaveany.server.report;

import org.dotheyhaveany.server.store.Store;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
public class ShoppingReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private final Store store;

    private final Instant timestamp;

    @Enumerated(EnumType.STRING)
    private final ShoppingReportPerspective perspective;

    @OneToMany(mappedBy = "report", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Observation> observations;

    protected ShoppingReport() {
        this(null, null, null);
    }

    public ShoppingReport(final Store store, final Instant timestamp, final ShoppingReportPerspective perspective) {
        this.store = store;
        this.timestamp = timestamp;
        this.perspective = perspective;
        this.observations = Collections.emptySet();
    }

    public Long getId() {
        return id;
    }

    public Store getStore() {
        return store;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public ShoppingReportPerspective getPerspective() {
        return perspective;
    }

    public Set<Observation> getObservations() {
        return observations;
    }

    public void setObservations(final Set<Observation> observations) {
        this.observations = observations;
    }

    @Override
    public String toString() {
        return "ShoppingReport{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", perspective=" + perspective +
                ", observations=" + observations +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ShoppingReport that = (ShoppingReport) o;
        return store.equals(that.store) &&
                timestamp.equals(that.timestamp) &&
                perspective == that.perspective &&
                observations.equals(that.observations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(store, timestamp, perspective, observations);
    }
}
