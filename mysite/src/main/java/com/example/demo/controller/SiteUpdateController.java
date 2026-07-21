package com.example.demo.controller;

import com.example.demo.service.MovieService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SiteUpdateController {
    private final MovieService movies;
    private final long refreshIntervalMs;

    public SiteUpdateController(
            MovieService movies,
            @Value("${app.public-refresh-interval-ms:15000}") long refreshIntervalMs
    ) {
        this.movies = movies;
        this.refreshIntervalMs = refreshIntervalMs;
    }

    @GetMapping("/api/site-version")
    ResponseEntity<Map<String, Object>> siteVersion() {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore())
                .body(Map.of(
                        "version", movies.publicVersion(),
                        "refreshIntervalMs", refreshIntervalMs
                ));
    }
}
