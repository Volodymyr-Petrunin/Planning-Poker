package planing.poker.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import planing.poker.domain.dto.response.ResponseEventMessageDto;
import planing.poker.service.EventMessageService;

import java.util.Locale;

@Controller
@RequestMapping(MessageController.BASE_URL)
public class MessageController {

    public static final String BASE_URL = "/messages";

    private final EventMessageService eventMessageService;

    @Autowired
    public MessageController(final EventMessageService eventMessageService) {
        this.eventMessageService = eventMessageService;
    }

    @PostMapping("/localize")
    public ResponseEntity<String> localizeMessage(@Valid @RequestBody final ResponseEventMessageDto responseEventMessageDto,
                                                  @RequestHeader(value = "Accept-Language", defaultValue = "en") final Locale locale) {
        return ResponseEntity.ok(eventMessageService.getLocalizedMessage(responseEventMessageDto, locale));
    }
}
