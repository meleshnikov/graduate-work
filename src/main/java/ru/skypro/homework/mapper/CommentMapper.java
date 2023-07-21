package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateCommentDto;
import ru.skypro.homework.entity.Comment;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {


    @Mapping(source = "author.id", target = "author")
    @Mapping(target = "authorImage", expression = "java(image(comment))")
    @Mapping(source = "author.firstName", target = "authorFirstName")
    @Mapping(source = "id", target = "pk")
    CommentDto toCommentDTO(Comment comment);

    /**
     * The method creates in
     *
     * @param value Local Time Date
     * @return creates in
     */
    default Long createdAt(LocalDateTime value) {
        if (value == null) {
            return 0L;
        }
        return value.toInstant(ZoneOffset.ofHours(3)).toEpochMilli();
    }

    /**
     * Image method
     *
     * @param comment comment
     * @return image
     */
    default String image(Comment comment) {
        int id = comment.getAuthor().getId().intValue();
        return "/users/" + id + "/image";
    }

    Comment toComment(CreateCommentDto createComment);

    List<CommentDto> toCommentsListDto(Collection<Comment> commentCollection);

}