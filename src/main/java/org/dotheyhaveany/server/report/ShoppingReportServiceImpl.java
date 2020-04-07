package org.dotheyhaveany.server.report;

import org.dotheyhaveany.server.store.Store;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingReportServiceImpl implements ShoppingReportService {

    private final ShoppingReportRepository reportRepository;

    private static final Duration MAX_REPORT_AGE = Duration.ofDays(1);

    public ShoppingReportServiceImpl(final ShoppingReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public ShoppingReport saveReport(final Store store, final ShoppingReportPerspective perspective, final Map<GroceryItem, ItemAvailability> observations) {
        return saveReport(store, perspective, observations, Instant.now());
    }

    // Visible for testing
    ShoppingReport saveReport(final Store store, final ShoppingReportPerspective perspective, final Map<GroceryItem, ItemAvailability> observations, final Instant currentTime) {
        final ShoppingReport report = new ShoppingReport(store, currentTime, perspective);

        report.setObservations(observations.entrySet().stream()
                .map(entry -> new Observation(report, entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet()));

        return reportRepository.save(report);
    }

    @Override
    public Optional<InventoryReport> getInventoryReport(final Store store) {
        final Instant currentTime = Instant.now();

        final List<ShoppingReport> recentReports =
                reportRepository.getRecentForStore(store, currentTime.minus(MAX_REPORT_AGE));

        final Map<GroceryItem, Map<ItemAvailability, Double>> availabilityScores = new EnumMap<>(GroceryItem.class);

        recentReports.forEach(report -> report.getObservations().forEach(observation -> {
            availabilityScores.computeIfAbsent(observation.getGroceryItem(), (item) -> new EnumMap<>(ItemAvailability.class))
                    .merge(observation.getAvailability(), getObservationWeight(report.getTimestamp(), currentTime), Double::sum);
        }));

        final Map<GroceryItem, ItemAvailability> availabilityByGroceryItem = new EnumMap<>(GroceryItem.class);

        availabilityScores.forEach((groceryItem, scoresByAvailability) -> {
            availabilityByGroceryItem.put(groceryItem, getMostLikelyAvailability(scoresByAvailability));
        });

        return availabilityScores.isEmpty() ? Optional.empty() :
                Optional.of(new InventoryReport(store, recentReports.get(0).getTimestamp(), availabilityByGroceryItem));
    }

    static double getObservationWeight(final Instant observationTime, final Instant currentTime) {
        final Duration age = Duration.between(observationTime, currentTime);
        final long ageInHours = age.toHours();

        return 1.0 / (ageInHours + 1);
    }

    static ItemAvailability getMostLikelyAvailability(final Map<ItemAvailability, Double> weightByAvailability) {
        if (weightByAvailability.isEmpty()) {
            throw new IllegalArgumentException("Weight map must not be empty.");
        }

        ItemAvailability mostLikelyAvailability = null;
        double highestWeight = Double.MIN_VALUE;

        for (final Map.Entry<ItemAvailability, Double> entry : weightByAvailability.entrySet()) {
            if (entry.getValue() > highestWeight) {
                mostLikelyAvailability = entry.getKey();
                highestWeight = entry.getValue();
            }
        }

        assert mostLikelyAvailability != null;
        return mostLikelyAvailability;
    }
}
