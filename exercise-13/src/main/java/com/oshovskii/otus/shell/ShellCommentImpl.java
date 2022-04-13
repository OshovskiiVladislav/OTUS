package com.oshovskii.otus.shell;

import com.oshovskii.otus.dto.CommentDto;
import com.oshovskii.otus.services.interfaces.CommentService;
import com.oshovskii.otus.shell.interfaces.ShellComment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommentImpl implements ShellComment {
    private final ShellLoginImpl shellLogin;
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @Override
    @ShellMethod(value = "Publish all comments", key = {"allComments", "allC"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public List<CommentDto> publishAllComment() {
        return commentService.findAllComments()
                .stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @ShellMethod(value = "Save comment", key = {"saveComment", "saveC", "sC"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String saveComment(String text) {
        commentService.save(text);
        String completedCommandSaveComment = "Save comment <"+ text + "> completed";
        return completedCommandSaveComment;
    }

    private Availability isPublishEventCommandAvailable() {
        return shellLogin.getCurrentUserName() == null
                ? Availability.unavailable("Сначала залогиньтесь") : Availability.available();
    }
}
