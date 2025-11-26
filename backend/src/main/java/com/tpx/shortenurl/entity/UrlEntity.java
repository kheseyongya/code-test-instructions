package com.tpx.shortenurl.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UrlEntity {
    @Id
    @Column(unique = true)
    String alias;

    String fullUrl;

    String shortUrl;
}
