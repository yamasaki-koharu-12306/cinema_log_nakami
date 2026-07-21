package com.example.demo.specification;

import com.example.demo.entity.Movie;
import com.example.demo.entity.MovieStatus;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public final class MovieSpecification {
    private MovieSpecification() {
    }

    public static Specification<Movie> publicSearch(
            String keyword,
            String genre,
            Double minRating,
            Integer minRecommendation,
            Integer releaseYear,
            String service
    ) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("status"), MovieStatus.PUBLISHED));

            if (keyword != null && !keyword.isBlank()) {
                String value = "%" + keyword.trim().toLowerCase() + "%";
                predicates.add(builder.or(
                        builder.like(builder.lower(root.get("title")), value),
                        builder.like(builder.lower(root.get("originalTitle")), value),
                        builder.like(builder.lower(root.get("director")), value),
                        builder.like(builder.lower(root.get("castMembers")), value),
                        builder.like(builder.lower(root.get("summary")), value)
                ));
            }

            if (genre != null && !genre.isBlank()) {
                predicates.add(builder.equal(builder.lower(root.get("genre")), genre.trim().toLowerCase()));
            }

            if (minRating != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("rating"), minRating));
            }

            if (minRecommendation != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("recommendation"), minRecommendation));
            }

            if (releaseYear != null) {
                predicates.add(builder.equal(root.get("releaseYear"), releaseYear));
            }

            if (service != null && !service.isBlank()) {
                switch (service) {
                    case "netflix" -> predicates.add(builder.isNotNull(root.get("netflixUrl")));
                    case "prime" -> predicates.add(builder.isNotNull(root.get("amazonPrimeUrl")));
                    case "disney" -> predicates.add(builder.isNotNull(root.get("disneyPlusUrl")));
                    case "unext" -> predicates.add(builder.isNotNull(root.get("unextUrl")));
                    case "hulu" -> predicates.add(builder.isNotNull(root.get("huluUrl")));
                    case "abema" -> predicates.add(builder.isNotNull(root.get("abemaUrl")));
                    case "other" -> predicates.add(builder.isNotNull(root.get("otherServiceUrl")));
                    default -> {
                        // Unknown values are ignored so malformed query strings do not break the page.
                    }
                }
            }

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
