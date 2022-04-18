package com.oshovskii.otus.shell;

import com.oshovskii.otus.services.interfaces.CommentService;
import com.oshovskii.otus.shell.interfaces.ShellComment;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommentImpl implements ShellComment {
    private final ShellLoginImpl shellLogin;
    private final CommentService commentService;

    @Override
    @ShellMethod(value = "Publish count comments command", key = {"countComment", "cC"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public long publishCountComments() {
        return commentService.countComments();
    }

    @Override
    @ShellMethod(value = "Publish comment by id", key = {"getCommentById", "getC"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishCommentByID(Long commentId) {
        return commentService.findCommentById(commentId).toString();
    }

    @Override
    @ShellMethod(value = "Publish all comments", key = {"allComments", "allC"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishAllComment() {
        return commentService.findAllComments().toString();
    }

    @Override
    @ShellMethod(value = "Publish comment by title", key = {"findCommentByText", "fct"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishCommentByText(String text) {
        return commentService.findCommentByText(text).toString();
    }

    @Override
    @ShellMethod(value = "Save comment", key = {"saveComment", "saveC", "sC"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String saveComment(String text) {
        commentService.saveComment(text);
        String completedCommandSaveComment = "Save comment <" + text + "> completed";
        return completedCommandSaveComment;
    }

    @Override
    @ShellMethod(value = "Delete comment by id", key = {"deleteComment", "deleteC", "delC"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String deleteCommentByID(Long commentId) {
        commentService.deleteCommentById(commentId);
        String completedDeleteByIdInfo = "Comment with id " + commentId + " deleted";
        return completedDeleteByIdInfo;
    }

    private Availability isPublishEventCommandAvailable() {
        return shellLogin.getCurrentUserName() == null ? Availability.unavailable("Сначала залогиньтесь") : Availability.available();
    }
}
