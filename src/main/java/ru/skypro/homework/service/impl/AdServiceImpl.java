package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.Exceptions.AdNotFoundException;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.security.MyUserDetails;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.util.Collection;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdServiceImpl implements AdService {

    private final AdsMapper adsMapper;
    private final AdRepository adRepository;
    private final ImageService imageService;
    private final UserService userService;
    private final CommentService commentService;
    private final MyUserDetails userDetails;


    @Override
    public Collection<AdsDto> getAllAds(String title) {
        log.info("Request to receive all ads");
        Collection<Ad> ads;
        if (!isEmpty(title)) {
            ads = adRepository.findByTitleLikeIgnoreCase(title);
        } else {
            ads = adRepository.findAll();
        }
        return adsMapper.adsToAdsListDto(ads);
    }

    @Override
    public AdsDto createAd(CreateAdsDto createAdsDTO, MultipartFile image, Authentication authentication) {
        if (createAdsDTO.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        Ad ad = adsMapper.adsDtoToAd(createAdsDTO);
        ad.setAuthor(userService.getAuthorizedUser(authentication));
        log.info("Request to create new ad");
        Image adImage;
        try {
            adImage = imageService.downloadImage(image);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить фото");
        }
        ad.setImage(adImage);
        adRepository.save(ad);
        log.info("Save new ad ID:" + ad.getId());

        return adsMapper.adToAdsDto(ad);
    }

    @Override
    public FullAdsDto getFullAd(Long adId) {
        log.info("Request to get full info about ad");
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        return adsMapper.adToFullAdsDto(ad);
    }

    @Transactional
    @Override
    public void deleteAd(Long adId) {
        log.info("Request to delete ad by id");
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        commentService.deleteAllByAdId(adId);
        imageService.deleteImage(ad.getImage().getId());
        adRepository.deleteById(adId);
    }

    @Override
    public AdsDto updateAd(CreateAdsDto createAdsDto, Long adId) {
        log.info("Request to update ad by id");
        if (createAdsDto.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        adsMapper.updateAds(createAdsDto, ad);
        adRepository.save(ad);

        return adsMapper.adToAdsDto(ad);
    }

    @Override
    public Collection<AdsDto> getUserAllAds() {
        log.info("Request to get all user ads");
        Collection<Ad> ads;
        log.info(userDetails.getIdUserDto() + "   " + userDetails.getAuthorities() + "   " + userDetails.getUsername());
        ads = adRepository.findAllAdsByAuthorId(userDetails.getIdUserDto());

        return adsMapper.adsToAdsListDto(ads);
    }

    @Transactional
    @Override
    public String updateImage(Long adId, MultipartFile image) throws IOException {
        log.info("Request to update image");
        Ad updateAd = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        long idImage = updateAd.getImage().getId();
        updateAd.setImage(imageService.downloadImage(image));
        imageService.deleteImage(idImage);
        adRepository.save(updateAd);
        return adsMapper.adToAdsDto(updateAd).getImage();
    }

    @Transactional
    @Override
    public byte[] getAdImage(Long adId) {
        log.info("Get image of an AD with a ID:" + adId);
        return imageService.getImage(adRepository.findById(adId).orElseThrow(AdNotFoundException::new).getImage().getId());
    }
}