package planing.poker.common;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import planing.poker.common.exception.RoomNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ROOM_NOT_FIND_PAGE = "/error/room-not-found";

    @ExceptionHandler(RoomNotFoundException.class)
    public String handleRoomNotFound(final RoomNotFoundException ex, final Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return ROOM_NOT_FIND_PAGE;
    }
}
