package ru.skypro.resale.platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.openapitools.model.AdsDto;
import org.openapitools.model.CreateAdsDto;
import org.openapitools.model.FullAdsDto;
import org.openapitools.model.ResponseWrapperAdsDto;
import ru.skypro.resale.platform.entity.Ad;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AdMapper {
    Ad createAdsDtoToAd(CreateAdsDto source);

    void updateAd(@MappingTarget Ad target, CreateAdsDto source);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    AdsDto toAdsDto(Ad source);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "phone", source = "author.phone")
    FullAdsDto toFullAdsDto(Ad source);

    default ResponseWrapperAdsDto toResponseWrapperAdsDto(List<Ad> source) {
        return new ResponseWrapperAdsDto()
                .count(source.size())
                .results(source.stream()
                        .map(this::toAdsDto)
                        .collect(Collectors.toList()));
    }
}
