package planing.poker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import planing.poker.common.CustomBinderInitializer;
import planing.poker.controller.request.CreateStoryRequest;
import planing.poker.domain.dto.request.RequestStoryDto;
import planing.poker.service.StoryService;

@Controller
public class StoryController {

    private static final String CREATE_MESSAGE_MAPPING = "/createStory";

    private static final String STORY = "story";

    private final StoryService storyService;

    private final CustomBinderInitializer customBinder;

    @Autowired
    public StoryController(final StoryService storyService, final CustomBinderInitializer customBinder) {
        this.storyService = storyService;
        this.customBinder = customBinder;
    }

    @MessageMapping(CREATE_MESSAGE_MAPPING)
    public void createStory(final CreateStoryRequest createStoryRequest) {
        storyService.createSeveralStory(createStoryRequest.getStories(), createStoryRequest.getRoomId());
    }

    @PostMapping("/update/{id}")
    public String updateStory(@PathVariable("id") final long id, @ModelAttribute(STORY) final RequestStoryDto storyDto) {
        storyService.updateStory(id, storyDto);

        return "redirect:/room/{roomCode}";
    }

    @PostMapping("/delete/{id}")
    public String deleteStory(@PathVariable("id") final long id) {
        storyService.deleteStory(id);

        return "redirect:/room/{roomCode}";
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        customBinder.registerCustomEditor(binder, "stories", RequestStoryDto[].class);
    }
}

