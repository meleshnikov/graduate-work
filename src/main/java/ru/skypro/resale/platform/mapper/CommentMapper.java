package ru.skypro.resale.platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.CommentDto;
import org.openapitools.model.CreateCommentDto;
import org.openapitools.model.ResponseWrapperCommentDto;
import ru.skypro.resale.platform.entity.Ad;
import ru.skypro.resale.platform.entity.Comment;
import ru.skypro.resale.platform.entity.User;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = OffsetDateTime.class)
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ad", source = "ad")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "createdAt", expression = "java(OffsetDateTime.now())")
    Comment createComment(CreateCommentDto createCommentDto, Ad ad, User author);

    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "pk", source = "ad.id")
    @Mapping(target = "createdAt", expression = "java(toLong(source.getCreatedAt()))")
    CommentDto toCommentDto(Comment source);

    default long toLong(OffsetDateTime dateTime) {
        return dateTime.toInstant().toEpochMilli();
    }

    default ResponseWrapperCommentDto toResponseWrapperCommentDto(List<Comment> source) {
        return new ResponseWrapperCommentDto()
                .count(source.size())
                .results(source.stream()
                        .map(this::toCommentDto)
                        .collect(Collectors.toList()));
    }
}
