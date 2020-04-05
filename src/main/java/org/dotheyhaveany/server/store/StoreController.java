package org.dotheyhaveany.server.store;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(final StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/{storeId}")
    public ModelAndView get(@PathVariable final int storeId) {
        final Map<String, Object> model = new HashMap<>();
        model.put("store", storeService.findById(storeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        return new ModelAndView("store", model);
    }
}
