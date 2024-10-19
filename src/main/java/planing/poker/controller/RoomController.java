package planing.poker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import planing.poker.domain.dto.request.RequestRoomDto;
import planing.poker.domain.dto.request.RequestUserDto;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.security.UserDetailsImpl;
import planing.poker.service.RoomService;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(RoomController.BASE_URL)
public class RoomController {

    public static final String BASE_URL = "/room";

    public static final String CREATE_ROOM_URL = "/create";

    public static final String SHOW_ROOM_URL = "/{roomCode}";

    public static final String CREATE_ROOM_PAGE = "/register/room/reg-room";

    public static final String SHOW_ROOM_PAGE = "/show/room/show-room";

    public static final String ROOM = "room";

    private final RoomService roomService;

    private final ObjectMapper objectMapper;

    @Autowired
    public RoomController(final RoomService roomService, final ObjectMapper objectMapper) {
        this.roomService = roomService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(SHOW_ROOM_URL)
    public String showRoomsPage(@PathVariable final String roomCode, final Model model) {
        final ResponseRoomDto room = roomService.getRoomByCode(roomCode);
        model.addAttribute(ROOM, room);
        return SHOW_ROOM_PAGE;
    }

    @GetMapping(CREATE_ROOM_URL)
    public String createRoom(@ModelAttribute(ROOM) final RequestRoomDto room, final Model model) {
        model.addAttribute(ROOM, room);
        model.addAttribute("user", new RequestUserDto());
        return CREATE_ROOM_PAGE;
    }

    @PostMapping(CREATE_ROOM_URL)
    public String createRoom(@ModelAttribute(ROOM) final RequestRoomDto room, final Model model,
                             final BindingResult bindingResult, @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(ROOM, room);
            return CREATE_ROOM_PAGE;
        }

        final ResponseRoomDto result = roomService.createRoom(room, userDetails.getUsername());
        return "redirect:/room/"+ result.getRoomCode();
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(List.class, "invitedUsers", new PropertyEditorSupport() {
            @Override
            public void setAsText(final String text) throws IllegalArgumentException {
                try {
                    List<ResponseUserDto> users = Arrays.asList(objectMapper.readValue(text, ResponseUserDto[].class));
                    setValue(users);
                } catch (final Exception e) {
                    throw new IllegalArgumentException("message.cant.convert.string", e);
                }
            }
        });
    }


}
