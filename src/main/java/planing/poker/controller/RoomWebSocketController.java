package planing.poker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.domain.dto.response.ResponseStoryDto;

@Controller
public class RoomWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public RoomWebSocketController(final SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendRoomUpdate(final ResponseRoomDto responseRoomDto) {
        messagingTemplate.convertAndSend("/topic/room", responseRoomDto);
    }

    public void sendCurrentStoryUpdate(final ResponseStoryDto responseStoryDto) {
        messagingTemplate.convertAndSend("/topic/currentStory", responseStoryDto);
    }
}
