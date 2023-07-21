package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateCommentDto;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.entity.Comment;

public interface CommentService {
    ResponseWrapperComment getAllComments(int id);
    Comment getComment(long commentId);
    CommentDto addComment(int id, CreateCommentDto comment, Authentication authentication);
    void deleteComment(int adId, int commentId);
    void deleteAllByAdId(long adId);
    CommentDto updateComment(int adId, int commentId, CommentDto comment);
}
