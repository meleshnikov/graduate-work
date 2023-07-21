package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;

import java.io.IOException;
import java.util.Collection;

public interface AdService {

    Collection<AdsDto> getAllAds(String title);

    AdsDto createAd(CreateAdsDto createAdsDTO, MultipartFile image, Authentication authentication);

    FullAdsDto getFullAd(Long adId);

    void deleteAd(Long adId);

    AdsDto updateAd(CreateAdsDto createAdsDTO, Long adId);

    Collection<AdsDto> getUserAllAds();

    String updateImage(Long adId,MultipartFile image) throws IOException;

    byte[] getAdImage(Long adId);
}
