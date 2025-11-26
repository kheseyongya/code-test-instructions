package com.tpx.shortenurl.repository;

import com.tpx.shortenurl.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    Optional<UrlEntity> findByAlias(String alias);
    void deleteByAlias(String alias);
}
