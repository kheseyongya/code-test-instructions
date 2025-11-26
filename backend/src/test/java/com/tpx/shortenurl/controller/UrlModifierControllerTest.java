package com.tpx.shortenurl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpx.shortenurl.dto.ShortenRequestDto;
import com.tpx.shortenurl.dto.UrlDto;
import com.tpx.shortenurl.service.UrlService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UrlModifierController.class)
class UrlModifierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UrlService urlService;

    @Test
    void shortenUrl_returnsCreatedAndResponseBody() throws Exception {
        ShortenRequestDto request = new ShortenRequestDto("https://example.com", "example");

        when(urlService.generateShortUrl(any()))
                .thenReturn("http://short/abc");

        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortUrl").value("http://short/abc"));

        ArgumentCaptor<ShortenRequestDto> captor = ArgumentCaptor.forClass(ShortenRequestDto.class);
        verify(urlService).generateShortUrl(captor.capture());
        assertThat(captor.getValue().getFullUrl()).isEqualTo("https://example.com");
    }

    @Test
    void getOriginalUrl_redirectsCorrectly() throws Exception {
        when(urlService.getFullUrl("abc")).thenReturn("https://example.com");

        mockMvc.perform(get("/abc"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://example.com"));

        verify(urlService).getFullUrl("abc");
    }

    @Test
    void deleteUrl_callsServiceAndReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/abc"))
                .andExpect(status().isNoContent());

        verify(urlService).deleteUrl("abc");
    }

    @Test
    void listUrls_returnsList() throws Exception {
        UrlDto dto = UrlDto.builder()
                .alias("abc")
                .fullUrl("https://example.com")
                .shortUrl("http://short/abc")
                .build();

        when(urlService.listUrls()).thenReturn(List.of(dto));

        mockMvc.perform(get("/urls")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].alias").value("abc"))
                .andExpect(jsonPath("$[0].fullUrl").value("https://example.com"))
                .andExpect(jsonPath("$[0].shortUrl").value("http://short/abc"));

        verify(urlService).listUrls();
    }
}
