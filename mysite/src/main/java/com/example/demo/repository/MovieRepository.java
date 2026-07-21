package com.example.demo.repository;

import com.example.demo.entity.Movie;
import com.example.demo.entity.MovieStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    Optional<Movie> findBySlugAndStatus(String slug, MovieStatus status);

    boolean existsBySlug(String slug);

    boolean existsBySlugAndIdNot(String slug, Long id);

    List<Movie> findTop6ByStatusOrderByPublishedAtDesc(MovieStatus status);

    List<Movie> findTop5ByStatusOrderByViewCountDesc(MovieStatus status);

    @Query("select coalesce(max(m.updatedAt), current_timestamp) from Movie m where m.status = com.example.demo.entity.MovieStatus.PUBLISHED")
    java.time.LocalDateTime findLatestPublishedUpdate();

    long countByStatus(MovieStatus status);

    List<Movie> findTop4ByStatusAndGenreIgnoreCaseAndIdNotOrderByRatingDesc(
            MovieStatus status,
            String genre,
            Long id
    );
}
