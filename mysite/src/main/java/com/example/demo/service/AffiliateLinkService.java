package com.example.demo.service;

import com.example.demo.entity.AffiliateClick;
import com.example.demo.entity.Movie;
import com.example.demo.repository.AffiliateClickRepository;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class AffiliateLinkService {
    private final AffiliateClickRepository clicks;
    private final MovieService movies;
    private final String amazonTag;

    public AffiliateLinkService(
            AffiliateClickRepository clicks,
            MovieService movies,
            @Value("${app.affiliate.amazon-tag:}") String amazonTag
    ) {
        this.clicks = clicks;
        this.movies = movies;
        this.amazonTag = amazonTag == null ? "" : amazonTag.trim();
    }

    @Transactional
    public String resolveAndTrack(String slug, String service) {
        Movie movie = movies.getPublishedBySlug(slug);
        String normalized = normalize(service);
        String destination = switch (normalized) {
            case "netflix" -> movie.getNetflixUrl();
            case "prime" -> withAmazonTag(movie.getAmazonPrimeUrl());
            case "disney" -> movie.getDisneyPlusUrl();
            case "unext" -> movie.getUnextUrl();
            case "hulu" -> movie.getHuluUrl();
            case "abema" -> movie.getAbemaUrl();
            case "other" -> movie.getOtherServiceUrl();
            default -> throw new NoSuchElementException("配信サービスが見つかりません");
        };

        if (!StringUtils.hasText(destination)) {
            throw new NoSuchElementException("配信リンクが登録されていません");
        }

        validateHttpUrl(destination);

        AffiliateClick click = new AffiliateClick();
        click.setMovieId(movie.getId());
        click.setServiceName(normalized);
        click.setDestinationUrl(destination);
        clicks.save(click);

        return destination;
    }

    private String normalize(String service) {
        return service == null ? "" : service.trim().toLowerCase(Locale.ROOT);
    }

    private String withAmazonTag(String url) {
        if (!StringUtils.hasText(url) || !StringUtils.hasText(amazonTag)) {
            return url;
        }

        if (!url.contains("amazon.")) {
            return url;
        }

        String encodedTag = URLEncoder.encode(amazonTag, StandardCharsets.UTF_8);
        if (url.matches(".*[?&]tag=[^&]*.*")) {
            return url.replaceAll("([?&])tag=[^&]*", "$1tag=" + encodedTag);
        }
        return url + (url.contains("?") ? "&" : "?") + "tag=" + encodedTag;
    }

    private void validateHttpUrl(String url) {
        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
                throw new IllegalArgumentException("HTTP/HTTPS以外のURLには遷移できません");
            }
        } catch (URISyntaxException exception) {
            throw new IllegalArgumentException("配信リンクのURLが正しくありません", exception);
        }
    }
}
