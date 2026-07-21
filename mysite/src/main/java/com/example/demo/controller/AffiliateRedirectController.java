package com.example.demo.controller;

import com.example.demo.service.AffiliateLinkService;
import java.net.URI;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AffiliateRedirectController {
    private final AffiliateLinkService affiliateLinks;

    public AffiliateRedirectController(AffiliateLinkService affiliateLinks) {
        this.affiliateLinks = affiliateLinks;
    }

    @GetMapping("/go/{slug}/{service}")
    ResponseEntity<Void> redirect(
            @PathVariable String slug,
            @PathVariable String service
    ) {
        String destination = affiliateLinks.resolveAndTrack(slug, service);
        return ResponseEntity.status(302)
                .header(HttpHeaders.LOCATION, URI.create(destination).toASCIIString())
                .header(HttpHeaders.CACHE_CONTROL, "no-store")
                .build();
    }
}
