package planing.poker.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import planing.poker.common.CustomBinderInitializer;
import planing.poker.controller.request.CreateStoryRequest;
import planing.poker.controller.request.DeleteStoryRequest;
import planing.poker.controller.request.UpdateStoryRequest;
import planing.poker.domain.dto.request.RequestStoryDto;
import planing.poker.service.StoryService;

@Controller
public class StoryController {

    private static final String CREATE_MESSAGE_MAPPING = "/createStory";

    private static final String UPDATE_MESSAGE_MAPPING = "/updateStory";

    private static final String DELETE_MESSAGE_MAPPING = "/deleteStory";

    private final StoryService storyService;

    private final CustomBinderInitializer customBinder;

    @Autowired
    public StoryController(final StoryService storyService, final CustomBinderInitializer customBinder) {
        this.storyService = storyService;
        this.customBinder = customBinder;
    }

    @MessageMapping(CREATE_MESSAGE_MAPPING)
    public void createStory(@Valid final CreateStoryRequest request) {
        storyService.createSeveralStory(request.getStories(), request.getRoomId());
    }

    @MessageMapping(UPDATE_MESSAGE_MAPPING)
    public void updateStory(@Valid final UpdateStoryRequest request) {
        storyService.updateStory(request.getStoryId(), request.getRequestStoryDto());
    }

    @MessageMapping(DELETE_MESSAGE_MAPPING)
    public void deleteStory(@Valid final DeleteStoryRequest request) {
        storyService.deleteStory(request.getStoryId());
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        customBinder.registerCustomEditor(binder, "stories", RequestStoryDto[].class);
    }
}

