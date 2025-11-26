package com.tpx.shortenurl.service;

import com.tpx.shortenurl.dto.ShortenRequestDto;
import com.tpx.shortenurl.dto.UrlDto;
import com.tpx.shortenurl.entity.UrlEntity;
import com.tpx.shortenurl.mapper.UrlMapper;
import com.tpx.shortenurl.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrlServiceTest {

    UrlRepository urlRepository;
    UrlMapper urlMapper;
    UrlService urlService;

    @BeforeEach
    void setup() {
        urlRepository = mock(UrlRepository.class);
        urlMapper = mock(UrlMapper.class);
        urlService = new UrlService(urlRepository, urlMapper);
    }

    @Test
    void saveUrl_successfulSave_callsRepository() {
        UrlDto dto = UrlDto.builder().alias("abc").fullUrl("https://example.com").shortUrl("https://short/abc").build();
        UrlEntity entity = new UrlEntity();
        entity.setAlias("abc");

        when(urlMapper.toEntity(dto)).thenReturn(entity);
        when(urlRepository.save(entity)).thenReturn(entity);

        assertThatCode(() -> urlService.saveUrl(dto)).doesNotThrowAnyException();
        verify(urlRepository).save(entity);
    }

    @Test
    void saveUrl_nullAlias_throwsBadRequest() {
        UrlDto dto = UrlDto.builder().alias(null).fullUrl("https://example.com").shortUrl("https://short/abc").build();
        UrlEntity entity = new UrlEntity();

        when(urlMapper.toEntity(dto)).thenReturn(entity);
        when(urlRepository.save(entity)).thenReturn(entity);

        assertThatThrownBy(() -> urlService.saveUrl(dto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Invalid input or alias already taken");
    }

    @Test
    void listUrls_returnsDtoList() {
        UrlEntity entity = new UrlEntity();
        entity.setAlias("abc");
        entity.setFullUrl("https://example.com");
        entity.setShortUrl("https://short/abc");

        when(urlRepository.findAll()).thenReturn(List.of(entity));
        when(urlMapper.toDto(entity)).thenReturn(
                UrlDto.builder().alias("abc").fullUrl("https://example.com").shortUrl("https://short/abc").build()
        );

        List<UrlDto> result = urlService.listUrls();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAlias()).isEqualTo("abc");
    }

    @Test
    void getFullUrl_validAlias_returnsFullUrl() {
        UrlEntity entity = new UrlEntity();
        entity.setFullUrl("https://example.com");

        when(urlRepository.findByAlias("abc")).thenReturn(Optional.of(entity));

        String fullUrl = urlService.getFullUrl("abc");
        assertThat(fullUrl).isEqualTo("https://example.com");
    }

    @Test
    void getFullUrl_invalidAlias_throwsNotFound() {
        when(urlRepository.findByAlias("abc")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> urlService.getFullUrl("abc"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Alias not found");
    }

    @Test
    void deleteUrl_existingAlias_callsDelete() {
        UrlEntity entity = new UrlEntity();
        when(urlRepository.findByAlias("abc")).thenReturn(Optional.of(entity));

        urlService.deleteUrl("abc");

        verify(urlRepository).deleteByAlias("abc");
    }

    @Test
    void deleteUrl_nonExistingAlias_throwsNotFound() {
        when(urlRepository.findByAlias("abc")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> urlService.deleteUrl("abc"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Alias not found");
    }

    @Test
    void extractHost_validUrl_returnsHost() {
        String host = urlService.extractHost("https://example.com/some/path");
        assertThat(host).isEqualTo("https://example.com");
    }

    @Test
    void extractHost_invalidUrl_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> urlService.extractHost("invalid-url"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid URL format");
    }

    @Test
    void generateShortUrl_setsAliasAndSaves() {
        ShortenRequestDto request = new ShortenRequestDto("https://example.com", null);

        UrlEntity entity = new UrlEntity();
        entity.setAlias("example");

        when(urlMapper.toEntity(any(UrlDto.class))).thenReturn(entity);
        when(urlRepository.save(any(UrlEntity.class))).thenReturn(entity);

        String shortUrl = urlService.generateShortUrl(request);

        assertThat(shortUrl).startsWith("https://example.com/");
        assertThat(request.getCustomAlias()).isNotNull();
        verify(urlRepository).save(any(UrlEntity.class));
    }
}
