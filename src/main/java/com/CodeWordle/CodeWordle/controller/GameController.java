package com.CodeWordle.CodeWordle.controller;

import com.CodeWordle.CodeWordle.model.Topic;
import com.CodeWordle.CodeWordle.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * MVC Controller responsible for rendering the main game view.
 */
@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/")
    public String gamePage(Model model) {
        // Fetch all available topics to display them on the page
        List<Topic> topics = gameService.getAllTopics();
        model.addAttribute("topics", topics);
        return "game"; // This will resolve to /WEB-INF/game/game.jsp
    }
}