package planing.poker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(MainController.BASE_URL)
public class MainController {

    public static final String BASE_URL = "/";

    private static final String BASE_PAGE = "main/main-page";

    @GetMapping(BASE_URL)
    public String index() {
        return BASE_PAGE;
    }
}
