package com.example.demo.service;

import java.text.Normalizer;
import java.util.Locale;

import org.springframework.stereotype.Service;

@Service
public class SlugService {

    public String make(String value) {

        if (value == null || value.isBlank()) {
            return "movie";
        }

        String slug = Normalizer
                .normalize(value, Normalizer.Form.NFKC)
                .toLowerCase(Locale.ROOT)
                .trim()
                .replaceAll("[^\\p{L}\\p{N}]+", "-")
                .replaceAll("(^-|-$)", "");

        return slug.isBlank() ? "movie" : slug;
    }
}