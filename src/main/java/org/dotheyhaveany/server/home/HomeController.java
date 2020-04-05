package org.dotheyhaveany.server.home;

import org.dotheyhaveany.server.store.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {

    private final StoreService storeService;

    public HomeController(final StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public ModelAndView get() {
        final Map<String, Object> model = new HashMap<>();
        model.put("stores", storeService.findAll());

        return new ModelAndView("home", model);
    }
}
