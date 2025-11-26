package com.tpx.shortenurl.service;

import com.tpx.shortenurl.dto.ShortenRequestDto;
import com.tpx.shortenurl.dto.UrlDto;
import com.tpx.shortenurl.entity.UrlEntity;
import com.tpx.shortenurl.mapper.UrlMapper;
import com.tpx.shortenurl.repository.UrlRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UrlService {
    UrlRepository urlRepository;
    UrlMapper urlMapper;

    public UrlService(UrlRepository urlRepository, UrlMapper urlMapper) {
        this.urlRepository = urlRepository;
        this.urlMapper = urlMapper;
    }

    public void saveUrl(UrlDto urlDto) {
        UrlEntity urlEntity = urlRepository.save(urlMapper.toEntity(urlDto));
        if(urlEntity.getAlias() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input or alias already taken");
        }
    }

    public List<UrlDto> listUrls() {
        List<UrlEntity> urlEntityList = urlRepository.findAll();
        return urlEntityList.stream().map(url -> urlMapper.toDto(url)).collect(Collectors.toList());
    }

    public String getFullUrl(String alias) {
        Optional<UrlEntity> urlEntity = urlRepository.findByAlias(alias);
        if(urlEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alias not found");
        }
        return urlEntity.get().getFullUrl();
    }

    @Transactional
    public void deleteUrl(String alias) {
        Optional<UrlEntity> urlEntity = urlRepository.findByAlias(alias);
        if (urlEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alias not found");
        }
        urlRepository.deleteByAlias(alias);
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

        saveUrl(urlDto);
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
