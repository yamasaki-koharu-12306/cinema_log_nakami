package com.example.demo.service;

import com.example.demo.controller.form.MovieForm;
import com.example.demo.entity.Movie;
import com.example.demo.entity.MovieStatus;
import com.example.demo.repository.MovieRepository;
import com.example.demo.specification.MovieSpecification;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MovieService {
    private static final int PUBLIC_PAGE_SIZE = 9;

    private final MovieRepository repo;
    private final SlugService slugs;

    public MovieService(MovieRepository repo, SlugService slugs) {
        this.repo = repo;
        this.slugs = slugs;
    }

    public List<Movie> latest() {
        return repo.findTop6ByStatusOrderByPublishedAtDesc(MovieStatus.PUBLISHED);
    }

    public List<Movie> popular() {
        return repo.findTop5ByStatusOrderByViewCountDesc(MovieStatus.PUBLISHED);
    }

    public Page<Movie> search(
            String keyword,
            String genre,
            Double minRating,
            Integer minRecommendation,
            Integer releaseYear,
            String service,
            String sort,
            int page
    ) {
        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                PUBLIC_PAGE_SIZE,
                resolveSort(sort)
        );

        return repo.findAll(
                MovieSpecification.publicSearch(
                        keyword,
                        genre,
                        minRating,
                        minRecommendation,
                        releaseYear,
                        service
                ),
                pageable
        );
    }

    public List<String> findGenres() {
        return repo.findAll().stream()
                .filter(movie -> movie.getStatus() == MovieStatus.PUBLISHED)
                .map(Movie::getGenre)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    public List<Integer> findReleaseYears() {
        return repo.findAll().stream()
                .filter(movie -> movie.getStatus() == MovieStatus.PUBLISHED)
                .map(Movie::getReleaseYear)
                .filter(Objects::nonNull)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    private Sort resolveSort(String sort) {
        return switch (sort == null ? "newest" : sort) {
            case "oldest" -> Sort.by(Sort.Direction.ASC, "publishedAt");
            case "rating-desc" -> Sort.by(Sort.Direction.DESC, "rating")
                    .and(Sort.by(Sort.Direction.DESC, "publishedAt"));
            case "recommendation-desc" -> Sort.by(Sort.Direction.DESC, "recommendation")
                    .and(Sort.by(Sort.Direction.DESC, "rating"));
            case "release-year-desc" -> Sort.by(Sort.Direction.DESC, "releaseYear")
                    .and(Sort.by(Sort.Direction.DESC, "publishedAt"));
            case "popular" -> Sort.by(Sort.Direction.DESC, "viewCount")
                    .and(Sort.by(Sort.Direction.DESC, "publishedAt"));
            default -> Sort.by(Sort.Direction.DESC, "publishedAt");
        };
    }

    public Movie getPublishedBySlug(String slug) {
        return repo.findBySlugAndStatus(slug, MovieStatus.PUBLISHED)
                .orElseThrow(() -> new NoSuchElementException("記事が見つかりません"));
    }

    public String publicVersion() {
        LocalDateTime latestUpdate = repo.findLatestPublishedUpdate();
        long epoch = latestUpdate == null
                ? 0L
                : latestUpdate.toInstant(ZoneOffset.UTC).toEpochMilli();
        return repo.countByStatus(MovieStatus.PUBLISHED) + "-" + epoch;
    }

    @Transactional
    public Movie detail(String slug) {
        Movie movie = getPublishedBySlug(slug);
        movie.setViewCount(movie.getViewCount() + 1);
        return movie;
    }

    public List<Movie> related(Movie movie) {
        return repo.findTop4ByStatusAndGenreIgnoreCaseAndIdNotOrderByRatingDesc(
                MovieStatus.PUBLISHED,
                movie.getGenre(),
                movie.getId()
        );
    }

    public Page<Movie> admin(int page) {
        return repo.findAll(PageRequest.of(
                Math.max(page, 0),
                15,
                Sort.by(Sort.Direction.DESC, "updatedAt")
        ));
    }

    public Movie get(Long id) {
        return repo.findById(id).orElseThrow();
    }

    @Transactional
    public Movie save(Long id, MovieForm form) {
        Movie movie = id == null ? new Movie() : get(id);
        String slug = (form.getSlug() == null || form.getSlug().isBlank())
                ? slugs.make(form.getTitle())
                : slugs.make(form.getSlug());

        if ((id == null && repo.existsBySlug(slug))
                || (id != null && repo.existsBySlugAndIdNot(slug, id))) {
            slug += "-" + System.currentTimeMillis();
        }

        movie.setTitle(form.getTitle());
        movie.setSlug(slug);
        movie.setOriginalTitle(form.getOriginalTitle());
        movie.setReleaseYear(form.getReleaseYear());
        movie.setGenre(form.getGenre());
        movie.setDirector(form.getDirector());
        movie.setCastMembers(form.getCastMembers());
        movie.setRating(form.getRating());
        movie.setRecommendation(form.getRecommendation());
        movie.setWatchedAt(form.getWatchedAt());
        movie.setPosterUrl(form.getPosterUrl());
        movie.setSummary(form.getSummary());
        movie.setReviewBody(form.getReviewBody());
        movie.setMemorablePoint(form.getMemorablePoint());
        movie.setStatus(form.getStatus());
        movie.setAmazonPrimeUrl(form.getAmazonPrimeUrl());
        movie.setNetflixUrl(form.getNetflixUrl());
        movie.setDisneyPlusUrl(form.getDisneyPlusUrl());
        movie.setUnextUrl(form.getUnextUrl());
        movie.setHuluUrl(form.getHuluUrl());
        movie.setAbemaUrl(form.getAbemaUrl());
        movie.setOtherServiceName(form.getOtherServiceName());
        movie.setOtherServiceUrl(form.getOtherServiceUrl());
        movie.setMetaTitle(form.getMetaTitle());
        movie.setMetaDescription(form.getMetaDescription());
        return repo.saveAndFlush(movie);
    }

    public MovieForm form(Movie movie) {
        MovieForm form = new MovieForm();
        form.setTitle(movie.getTitle());
        form.setSlug(movie.getSlug());
        form.setOriginalTitle(movie.getOriginalTitle());
        form.setReleaseYear(movie.getReleaseYear());
        form.setGenre(movie.getGenre());
        form.setDirector(movie.getDirector());
        form.setCastMembers(movie.getCastMembers());
        form.setRating(movie.getRating());
        form.setRecommendation(movie.getRecommendation());
        form.setWatchedAt(movie.getWatchedAt());
        form.setPosterUrl(movie.getPosterUrl());
        form.setSummary(movie.getSummary());
        form.setReviewBody(movie.getReviewBody());
        form.setMemorablePoint(movie.getMemorablePoint());
        form.setStatus(movie.getStatus());
        form.setAmazonPrimeUrl(movie.getAmazonPrimeUrl());
        form.setNetflixUrl(movie.getNetflixUrl());
        form.setDisneyPlusUrl(movie.getDisneyPlusUrl());
        form.setUnextUrl(movie.getUnextUrl());
        form.setHuluUrl(movie.getHuluUrl());
        form.setAbemaUrl(movie.getAbemaUrl());
        form.setOtherServiceName(movie.getOtherServiceName());
        form.setOtherServiceUrl(movie.getOtherServiceUrl());
        form.setMetaTitle(movie.getMetaTitle());
        form.setMetaDescription(movie.getMetaDescription());
        return form;
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
