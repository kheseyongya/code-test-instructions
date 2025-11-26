package com.tpx.shortenurl.mapper;

import com.tpx.shortenurl.dto.UrlDto;
import com.tpx.shortenurl.entity.UrlEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlMapper {

    UrlDto toDto(UrlEntity urlEntity);

    UrlEntity toEntity(UrlDto urlDto);
}
