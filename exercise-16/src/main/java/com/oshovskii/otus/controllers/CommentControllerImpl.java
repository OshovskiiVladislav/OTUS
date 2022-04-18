package com.oshovskii.otus.controllers;

import com.oshovskii.otus.controllers.interfaces.CommentController;
import com.oshovskii.otus.dto.CommentDto;
import com.oshovskii.otus.services.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentControllerImpl implements CommentController {
    private final CommentService commentService;

    @GetMapping("/comments")
    public String listPageComments(Model model) {
        List<CommentDto> commentDtoList = commentService.findAll();
        model.addAttribute("comments", commentDtoList);
        return "comments";
    }
}
