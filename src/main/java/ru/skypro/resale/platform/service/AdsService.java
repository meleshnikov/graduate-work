package ru.skypro.resale.platform.service;

import org.openapitools.model.AdsDto;
import org.openapitools.model.CreateAdsDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AdsService {

    AdsDto save(CreateAdsDto ads, Authentication authentication, MultipartFile photo) throws IOException;

}
