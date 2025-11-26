package com.tpx.shortenurl.mapper;

import com.tpx.shortenurl.dto.UrlDto;
import com.tpx.shortenurl.entity.UrlEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-26T12:44:38+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class UrlMapperImpl implements UrlMapper {

    @Override
    public UrlDto toDto(UrlEntity urlEntity) {
        if ( urlEntity == null ) {
            return null;
        }

        UrlDto.UrlDtoBuilder urlDto = UrlDto.builder();

        urlDto.alias( urlEntity.getAlias() );
        urlDto.fullUrl( urlEntity.getFullUrl() );
        urlDto.shortUrl( urlEntity.getShortUrl() );

        return urlDto.build();
    }

    @Override
    public UrlEntity toEntity(UrlDto urlDto) {
        if ( urlDto == null ) {
            return null;
        }

        UrlEntity urlEntity = new UrlEntity();

        urlEntity.setAlias( urlDto.getAlias() );
        urlEntity.setFullUrl( urlDto.getFullUrl() );
        urlEntity.setShortUrl( urlDto.getShortUrl() );

        return urlEntity;
    }
}
