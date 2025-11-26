package com.tpx.shortenurl.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UrlDto {
    String alias;

    String fullUrl;

    String shortUrl;
}
