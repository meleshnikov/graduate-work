package ru.skypro.resale.platform.mapper;

import org.mapstruct.Mapper;
import org.openapitools.model.CreateAdsDto;
import ru.skypro.resale.platform.entity.Ads;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    Ads createAdsDtoToAds(CreateAdsDto createAdsDto);

}
