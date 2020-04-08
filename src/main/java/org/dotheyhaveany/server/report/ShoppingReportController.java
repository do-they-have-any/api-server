package org.dotheyhaveany.server.report;

import org.dotheyhaveany.server.store.ShoppingReportForm;
import org.dotheyhaveany.server.store.Store;
import org.dotheyhaveany.server.store.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Controller
@RequestMapping("/store/{storeId}/report")
public class ShoppingReportController {

    private final StoreService storeService;
    private final ShoppingReportService reportService;

    private static final int[] AGES_IN_HOURS = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };

    public ShoppingReportController(final StoreService storeService, final ShoppingReportService reportService) {
        this.storeService = storeService;
        this.reportService = reportService;
    }

    @GetMapping
    public ModelAndView get(@PathVariable final int storeId, final ModelMap model) {
        final Store store = storeService.findById(storeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.put("store", store);

        final ShoppingReportForm reportForm = new ShoppingReportForm();
        reportForm.setStoreId(storeId);

        model.put("reportForm", reportForm);

        return new ModelAndView("report", model);
    }

    @PostMapping
    public ModelAndView post(final ShoppingReportForm reportForm) {
        final Store store = storeService.findById(reportForm.getStoreId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        reportService.saveReport(store, reportForm.getPerspective(), reportForm.getObservations());

        return new ModelAndView("report-submitted", Collections.singletonMap("store", store));
    }

    @ModelAttribute("agesInHours")
    public int[] agesInHours() {
        return AGES_IN_HOURS;
    }

    @ModelAttribute("perspectives")
    public ShoppingReportPerspective[] perspectives() {
        return ShoppingReportPerspective.values();
    }

    @ModelAttribute("groceryItems")
    public GroceryItem[] groceryItems() {
        return GroceryItem.values();
    }

    @ModelAttribute("availabilities")
    public ItemAvailability[] availabilities() {
        return ItemAvailability.values();
    }
}
