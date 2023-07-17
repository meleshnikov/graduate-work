package ru.skypro.resale.platform.service.impl;

import lombok.RequiredArgsConstructor;
import org.openapitools.model.CommentDto;
import org.openapitools.model.CreateCommentDto;
import org.openapitools.model.ResponseWrapperCommentDto;
import org.springframework.stereotype.Service;
import ru.skypro.resale.platform.exception.CommentNotFoundException;
import ru.skypro.resale.platform.mapper.CommentMapper;
import ru.skypro.resale.platform.repository.CommentRepository;
import ru.skypro.resale.platform.service.AdService;
import ru.skypro.resale.platform.service.CommentService;
import ru.skypro.resale.platform.service.UserService;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdService adService;
    private final UserService userService;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto addComment(Integer adId, CreateCommentDto comment, String username) {
        var ad = adService.getAdById(adId);
        var author = userService.getUserByEmail(username);
        return commentMapper.toCommentDto(commentRepository.save(commentMapper.createComment(comment, ad, author)));
    }

    @Override
    public void deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public ResponseWrapperCommentDto getCommentsByAdId(Integer adId) {
        return commentMapper.toResponseWrapperCommentDto(commentRepository.findAllByAdId(adId));
    }

    @Override
    public CommentDto updateComment(Integer commentId, CommentDto source) {
        var comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        comment.setText(source.getText());
        return commentMapper.toCommentDto(commentRepository.save(comment));
    }
}
