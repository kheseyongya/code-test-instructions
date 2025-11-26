package com.tpx.shortenurl.dto;

import lombok.Data;

@Data
public class ShortenRequestDto {
    private String fullUrl;
    private String customAlias;
}
