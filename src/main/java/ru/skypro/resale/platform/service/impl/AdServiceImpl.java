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
import ru.skypro.resale.platform.repository.AdRepository;
import ru.skypro.resale.platform.service.AdService;
import ru.skypro.resale.platform.service.FileService;
import ru.skypro.resale.platform.service.UserService;

import java.nio.file.Path;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdMapper adMapper;
    private final AdRepository adRepository;
    private final UserService userService;
    private final FileService fileService;
    @Value("${ad.images.dir}")
    private String imagesDirPath;

    @Override
    public Ad getAdById(Integer id) {
        return adRepository.findById(id).orElseThrow(AdNotFoundException::new);
    }

    @Override
    public AdsDto addAd(CreateAdsDto source, MultipartFile photo, String username) {
        var ad = adMapper.createAdsDtoToAd(source);
        var author = userService.getUserByEmail(username);
        ad.setAuthor(author);
        adRepository.save(ad);
        ad.setImage(fileService.saveFile(ad.getId().toString(), imagesDirPath, photo));
        adRepository.save(ad);
        return adMapper.toAdsDto(ad);
    }

    @Override
    public FullAdsDto getFullAdsById(Integer id) {
        return adMapper.toFullAdsDto(getAdById(id));
    }

    @Override
    public ResponseWrapperAdsDto getMyAds(String username) {
        var author = userService.getUserByEmail(username);
        return adMapper.toResponseWrapperAdsDto(author.getAds());
    }

    @Override
    public ResponseWrapperAdsDto getAllAds() {
        return adMapper.toResponseWrapperAdsDto(adRepository.findAll());
    }

    @Override
    public void removeAd(Integer id) {
        var ad = getAdById(id);
        Optional.ofNullable(ad.getImage()).ifPresent(fileService::removeFile);
        adRepository.delete(ad);
    }

    @Override
    public byte[] getImageAsBytes(String fileName) {
        return fileService.getBytes(Path.of(imagesDirPath, fileName));
    }

    @Override
    public void uploadImage(Integer id, MultipartFile image) {
        var ad = getAdById(id);
        ad.setImage(fileService.saveFile(ad.getId().toString(), imagesDirPath, image));
        adRepository.save(ad);
    }

    @Override
    public AdsDto updateAd(Integer id, CreateAdsDto createAdsDto) {
        var ad = getAdById(id);
        ad.setTitle(createAdsDto.getTitle());
        ad.setPrice(createAdsDto.getPrice());
        ad.setDescription(createAdsDto.getDescription());
        return adMapper.toAdsDto(adRepository.save(ad));
    }
}
