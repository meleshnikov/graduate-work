package ru.skypro.resale.platform.service;

import org.openapitools.model.AdsDto;
import org.openapitools.model.CreateAdsDto;
import org.openapitools.model.FullAdsDto;
import org.openapitools.model.ResponseWrapperAdsDto;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.resale.platform.entity.Ad;

public interface AdService {

    Ad getAdById(Integer id);

    AdsDto addAd(CreateAdsDto source, MultipartFile photo, String username);

    FullAdsDto getFullAdsById(Integer id);

    ResponseWrapperAdsDto getMyAds(String username);

    ResponseWrapperAdsDto getAllAds();

    void removeAd(Integer id);

    byte[] getImageAsBytes(String fileName);

    void uploadImage(Integer id, MultipartFile image);

    AdsDto updateAd(Integer id, CreateAdsDto createAdsDto);
}
