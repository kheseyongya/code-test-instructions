package com.tpx.shortenurl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortenRequestDto {
    private String fullUrl;
    private String customAlias;
}
