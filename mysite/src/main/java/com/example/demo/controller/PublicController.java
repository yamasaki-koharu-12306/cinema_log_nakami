package com.example.demo.controller;

import com.example.demo.entity.Movie;
import com.example.demo.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublicController {
    private final MovieService movies;

    public PublicController(MovieService movies) {
        this.movies = movies;
    }

    @GetMapping("/")
    String home(Model model) {
        model.addAttribute("latest", movies.latest());
        model.addAttribute("popular", movies.popular());
        return "home";
    }

    @GetMapping("/movies")
    String list(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "") String genre,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Integer minRecommendation,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(defaultValue = "") String service,
            @RequestParam(defaultValue = "newest") String sort,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        model.addAttribute(
                "movies",
                movies.search(q, genre, minRating, minRecommendation, releaseYear, service, sort, page)
        );
        model.addAttribute("q", q);
        model.addAttribute("genre", genre);
        model.addAttribute("minRating", minRating);
        model.addAttribute("minRecommendation", minRecommendation);
        model.addAttribute("releaseYear", releaseYear);
        model.addAttribute("service", service);
        model.addAttribute("sort", sort);
        model.addAttribute("genres", movies.findGenres());
        model.addAttribute("releaseYears", movies.findReleaseYears());
        return "movies/list";
    }

    @GetMapping("/movies/{slug}")
    String detail(@PathVariable String slug, Model model) {
        Movie movie = movies.detail(slug);
        model.addAttribute("movie", movie);
        model.addAttribute("related", movies.related(movie));
        return "movies/detail";
    }

    @GetMapping("/about")
    String about() {
        return "about";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }
}
