package ru.skypro.resale.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.resale.platform.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
