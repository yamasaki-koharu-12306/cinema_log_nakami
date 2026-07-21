package com.example.demo.repository;

import com.example.demo.entity.AffiliateClick;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffiliateClickRepository extends JpaRepository<AffiliateClick, Long> {
    long countByMovieId(Long movieId);
    long countByServiceNameIgnoreCase(String serviceName);
}
