package ru.skypro.resale.platform.service.impl;

import lombok.RequiredArgsConstructor;
import org.openapitools.model.AdsDto;
import org.openapitools.model.CreateAdsDto;
import org.openapitools.model.FullAdsDto;
import org.openapitools.model.ResponseWrapperAdsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.resale.platform.entity.Ad;
import ru.skypro.resale.platform.exception.AdNotFoundException;
import ru.skypro.resale.platform.mapper.AdMapper;
import ru.skypro.resale.platform.repository.AdsRepository;
import ru.skypro.resale.platform.service.AdService;
import ru.skypro.resale.platform.service.FileService;
import ru.skypro.resale.platform.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdMapper adMapper;
    private final AdsRepository adsRepository;
    private final UserService userService;
    private final FileService fileService;
    @Value("${ad.images.dir}")
    private String imagesDirPath;

    @Override
    public Ad getAdById(Integer id) {
        return adsRepository.findById(id).orElseThrow(AdNotFoundException::new);
    }

    @Override
    public AdsDto addAd(CreateAdsDto source, MultipartFile photo, String username) {
        var ad = adMapper.createAdsDtoToAd(source);
        ad.setAuthor(userService.getUserByEmail(username));
        ad.setImage(fileService.saveFile(username + UUID.randomUUID(), imagesDirPath, photo));
        adsRepository.save(ad);
        return adMapper.toAdsDto(ad);
    }

    @Override
    public FullAdsDto getFullAdsById(Integer id) {
        var ad = adsRepository.findById(id).orElseThrow(AdNotFoundException::new);
        return adMapper.toFullAdsDto(ad);
    }

    @Override
    public ResponseWrapperAdsDto getMyAds(String username) {
        var author = userService.getUserByEmail(username);
        return adMapper.toResponseWrapperAdsDto(author.getAds());
    }

    @Override
    public ResponseWrapperAdsDto getAllAds() {
        return adMapper.toResponseWrapperAdsDto(adsRepository.findAll());
    }
}
