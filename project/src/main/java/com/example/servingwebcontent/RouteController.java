package com.example.servingwebcontent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RouteController {

    @GetMapping("/moviepage")
    public String moviePage(@RequestParam(name="movieId", required=false, defaultValue="1") String movieId, Model model) {
        model.addAttribute("name", movieId);
        return "movie";
    }

    @GetMapping("/search")
    public String searchPage(@RequestParam(name="title", required=false, defaultValue="Toy Story") String title, Model model) {
        model.addAttribute("title", title);
        return "search_page";
    }

    @GetMapping("/personalpage")
    public String personalPage(@RequestParam(name="userId", required=false, defaultValue="") String userId, Model model) {
        model.addAttribute("userid", userId);
        return "personal_page";
    }

    @GetMapping("/recappage")
    public String recapPage(@RequestParam(name="userId", required=false, defaultValue="1") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "recap_page";
    }
}