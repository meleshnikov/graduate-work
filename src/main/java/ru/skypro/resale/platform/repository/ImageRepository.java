package ru.skypro.resale.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.resale.platform.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
}
