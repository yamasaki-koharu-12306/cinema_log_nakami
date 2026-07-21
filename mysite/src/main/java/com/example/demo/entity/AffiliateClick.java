package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "affiliate_clicks")
public class AffiliateClick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_id", nullable = false)
    private Long movieId;

    @Column(name = "service_name", nullable = false, length = 50)
    private String serviceName;

    @Column(name = "destination_url", nullable = false, length = 2000)
    private String destinationUrl;

    @Column(name = "clicked_at", nullable = false)
    private LocalDateTime clickedAt;

    @PrePersist
    void create() {
        if (clickedAt == null) {
            clickedAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public String getDestinationUrl() { return destinationUrl; }
    public void setDestinationUrl(String destinationUrl) { this.destinationUrl = destinationUrl; }
    public LocalDateTime getClickedAt() { return clickedAt; }
    public void setClickedAt(LocalDateTime clickedAt) { this.clickedAt = clickedAt; }
}
