package com.tpx.shortenurl.service;

import com.tpx.shortenurl.dto.ShortenRequestDto;
import com.tpx.shortenurl.dto.UrlDto;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class UrlModifierService {
    UrlService urlService;

    public UrlModifierService (UrlService urlService) {
        this.urlService = urlService;
    }

    public String generateShortUrl(ShortenRequestDto request) {
        String host = extractHost(request.getFullUrl());
        String alias = request.getCustomAlias() != null ? request.getCustomAlias() : generateUniqueCode();
        request.setCustomAlias(alias);
        String shortenUrl = host + "/" + alias;

        UrlDto urlDto = UrlDto.builder()
                .shortUrl(shortenUrl)
                .fullUrl(request.getFullUrl())
                .alias(alias)
                .build();

        urlService.saveUrl(urlDto);
        return shortenUrl;
    }

    public String extractHost(String fullUrl) {
        if (fullUrl == null || fullUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty.");
        }

        try {
            URI uri = new URI(fullUrl);
            String scheme = uri.getScheme();
            String host = uri.getHost();
            if(host == null) {
                throw new IllegalArgumentException("Invalid URL format: Host component is missing.");
            }
            return scheme + "://" + host;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL format: Could not parse URL.", e);
        }
    }

    public String generateUniqueCode() {
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }
}
