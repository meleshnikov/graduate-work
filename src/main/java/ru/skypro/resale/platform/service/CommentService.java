package ru.skypro.resale.platform.service;

import org.openapitools.model.CommentDto;
import org.openapitools.model.CreateCommentDto;
import org.openapitools.model.ResponseWrapperCommentDto;
import ru.skypro.resale.platform.entity.Ad;
import ru.skypro.resale.platform.entity.Comment;

public interface CommentService {

    Comment getCommentById(Integer id);

    CommentDto addComment(Integer adId, CreateCommentDto comment, String username);

    void deleteComment(Integer commentId);

    ResponseWrapperCommentDto getCommentsByAdId(Integer adId);

    CommentDto updateComment(Integer commentId, CommentDto source);

}
