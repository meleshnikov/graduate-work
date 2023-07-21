package ru.skypro.homework.mapper;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ad;

import java.util.Collection;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdsMapper {

    @Mapping(source = "id",target = "pk")
    @Mapping(source = "author.id",target = "author")
    @Mapping(target = "image",expression = "java(imageMapper(ad))")
    AdsDto adToAdsDto(Ad ad);

    Ad adsDtoToAd(CreateAdsDto createAdsDto);

    @Mapping(source = "id",target = "pk")
    @Mapping(source = "author.firstName",target = "authorFirstName")
    @Mapping(source = "author.lastName",target = "authorLastName")
    @Mapping(source = "author.email",target = "email")
    @Mapping(source = "author.phone",target = "phone")
    @Mapping(target = "image",expression = "java(imageMapper(ad))")
    FullAdsDto adToFullAdsDto(Ad ad);

    void updateAds(CreateAdsDto createAdsDto, @MappingTarget Ad ad);

    Collection<AdsDto> adsToAdsListDto(Collection<Ad> adsCollection);

    default String imageMapper(Ad ad){
        return "/ads/" + ad.getId() + "/image";
    }
}