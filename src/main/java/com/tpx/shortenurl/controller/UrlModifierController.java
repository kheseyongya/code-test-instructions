package com.tpx.shortenurl.controller;

import com.tpx.shortenurl.dto.ShortenRequestDto;
import com.tpx.shortenurl.dto.ShortenResponseDto;
import com.tpx.shortenurl.dto.UrlDto;
import com.tpx.shortenurl.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:5173")
public class UrlModifierController {
   private final UrlService urlService;

    public UrlModifierController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("shorten")
    public ResponseEntity<ShortenResponseDto> shortenUrl(@RequestBody ShortenRequestDto requestDto) {
        String shortenUrl = urlService.generateShortUrl(requestDto);
        ShortenResponseDto response = new ShortenResponseDto(shortenUrl);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("{alias}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String alias) {
        String originalUrl = urlService.getFullUrl(alias);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @DeleteMapping("{alias}")
    public ResponseEntity<Map<String, String>> deleteUrl(@PathVariable String alias) {
        urlService.deleteUrl(alias);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("urls")
    public ResponseEntity<List<UrlDto>> listUrls() {
        List<UrlDto> listUrls = urlService.listUrls();
        return new ResponseEntity<>(listUrls, HttpStatus.OK);
    }

}
