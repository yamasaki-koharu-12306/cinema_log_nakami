package com.example.demo.controller.form;

import com.example.demo.entity.MovieStatus;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class MovieForm {
    @NotBlank
    @Size(max = 200)
    private String title;

    @Size(max = 220)
    private String slug;

    private String originalTitle;

    @NotNull
    @Min(1888)
    @Max(2100)
    private Integer releaseYear;

    @NotBlank
    private String genre;

    private String director;
    private String castMembers;

    @NotNull
    @DecimalMin("1.0")
    @DecimalMax("5.0")
    private Double rating = 3.0;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer recommendation = 3;

    private LocalDate watchedAt;
    private String posterUrl;

    @NotBlank
    @Size(max = 500)
    private String summary;

    @NotBlank
    private String reviewBody;

    private String memorablePoint;

    @NotNull
    private MovieStatus status = MovieStatus.DRAFT;

    private String amazonPrimeUrl;
    private String netflixUrl;
    private String disneyPlusUrl;
    private String unextUrl;
    private String huluUrl;
    private String abemaUrl;
    private String otherServiceName;
    private String otherServiceUrl;
    private String metaTitle;
    private String metaDescription;

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
}
