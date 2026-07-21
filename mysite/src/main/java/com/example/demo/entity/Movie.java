package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, unique = true, length = 220)
    private String slug;

    private String originalTitle;
    private Integer releaseYear;
    private String genre;
    private String director;

    @Column(columnDefinition = "text")
    private String castMembers;

    @Column(nullable = false)
    private Double rating = 3.0;

    @Column(nullable = false)
    private Integer recommendation = 3;

    private LocalDate watchedAt;

    @Column(length = 1000)
    private String posterUrl;

    @Column(nullable = false, length = 500)
    private String summary;

    @Column(nullable = false, columnDefinition = "text")
    private String reviewBody;

    @Column(columnDefinition = "text")
    private String memorablePoint;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovieStatus status = MovieStatus.DRAFT;

    @Column(length = 1000)
    private String amazonPrimeUrl;

    @Column(length = 1000)
    private String netflixUrl;

    @Column(length = 1000)
    private String disneyPlusUrl;

    @Column(length = 1000)
    private String unextUrl;

    @Column(length = 1000)
    private String huluUrl;

    @Column(length = 1000)
    private String abemaUrl;

    @Column(length = 100)
    private String otherServiceName;

    @Column(length = 1000)
    private String otherServiceUrl;

    private String metaTitle;
    private String metaDescription;
    private Long viewCount = 0L;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void create() {
        createdAt = updatedAt = LocalDateTime.now();
        if (status == MovieStatus.PUBLISHED && publishedAt == null) {
            publishedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    void update() {
        updatedAt = LocalDateTime.now();
        if (status == MovieStatus.PUBLISHED && publishedAt == null) {
            publishedAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getOriginalTitle() { return originalTitle; }
    public void setOriginalTitle(String originalTitle) { this.originalTitle = originalTitle; }
    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
    public String getCastMembers() { return castMembers; }
    public void setCastMembers(String castMembers) { this.castMembers = castMembers; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public Integer getRecommendation() { return recommendation; }
    public void setRecommendation(Integer recommendation) { this.recommendation = recommendation; }
    public LocalDate getWatchedAt() { return watchedAt; }
    public void setWatchedAt(LocalDate watchedAt) { this.watchedAt = watchedAt; }
    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getReviewBody() { return reviewBody; }
    public void setReviewBody(String reviewBody) { this.reviewBody = reviewBody; }
    public String getMemorablePoint() { return memorablePoint; }
    public void setMemorablePoint(String memorablePoint) { this.memorablePoint = memorablePoint; }
    public MovieStatus getStatus() { return status; }
    public void setStatus(MovieStatus status) { this.status = status; }
    public String getAmazonPrimeUrl() { return amazonPrimeUrl; }
    public void setAmazonPrimeUrl(String amazonPrimeUrl) { this.amazonPrimeUrl = amazonPrimeUrl; }
    public String getNetflixUrl() { return netflixUrl; }
    public void setNetflixUrl(String netflixUrl) { this.netflixUrl = netflixUrl; }
    public String getDisneyPlusUrl() { return disneyPlusUrl; }
    public void setDisneyPlusUrl(String disneyPlusUrl) { this.disneyPlusUrl = disneyPlusUrl; }
    public String getUnextUrl() { return unextUrl; }
    public void setUnextUrl(String unextUrl) { this.unextUrl = unextUrl; }
    public String getHuluUrl() { return huluUrl; }
    public void setHuluUrl(String huluUrl) { this.huluUrl = huluUrl; }
    public String getAbemaUrl() { return abemaUrl; }
    public void setAbemaUrl(String abemaUrl) { this.abemaUrl = abemaUrl; }
    public String getOtherServiceName() { return otherServiceName; }
    public void setOtherServiceName(String otherServiceName) { this.otherServiceName = otherServiceName; }
    public String getOtherServiceUrl() { return otherServiceUrl; }
    public void setOtherServiceUrl(String otherServiceUrl) { this.otherServiceUrl = otherServiceUrl; }
    public String getMetaTitle() { return metaTitle; }
    public void setMetaTitle(String metaTitle) { this.metaTitle = metaTitle; }
    public String getMetaDescription() { return metaDescription; }
    public void setMetaDescription(String metaDescription) { this.metaDescription = metaDescription; }
    public Long getViewCount() { return viewCount; }
    public void setViewCount(Long viewCount) { this.viewCount = viewCount; }
    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
