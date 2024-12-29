package planing.poker.controller;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import planing.poker.common.CustomBinderInitializer;
import planing.poker.domain.dto.request.RequestRoomDto;
import planing.poker.domain.dto.request.RequestStoryDto;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.security.UserDetailsImpl;
import planing.poker.service.RoomService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomRegisterControllerTest {

    private static final String CREATE_ROOM_PAGE = "register/room/reg-room";

    private static final String ROOM_ATTRIBUTE = "room";

    private static final String ROOM_CODE = "12345";

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    private static RequestRoomDto expectedRequestRoomDto;

    @InjectMocks
    private RoomRegisterController roomRegisterController;

    @Mock
    private RoomService roomService;

    @Mock
    private CustomBinderInitializer customBinder;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserDetailsImpl userDetails;

    @BeforeAll
    static void setUp() {
        expectedRequestRoomDto = fixtureMonkey.giveMeOne(RequestRoomDto.class);
    }

    @Test
    @DisplayName("GET /room/create should return the create room page and set model attributes")
    void testCreateRoomGet() {
        final String viewName = roomRegisterController.createRoom(expectedRequestRoomDto, model);

        assertEquals(CREATE_ROOM_PAGE, viewName);
        verify(model).addAttribute(eq(ROOM_ATTRIBUTE), eq(expectedRequestRoomDto));
        verify(model).addAttribute(eq("user"), any());
    }

    @Test
    @DisplayName("POST /room/create with valid data should redirect to room page")
    void testCreateRoomPostValid() {
        final ResponseRoomDto responseRoomDto = fixtureMonkey.giveMeOne(ResponseRoomDto.class);
        responseRoomDto.setRoomCode(ROOM_CODE);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(roomService.createRoom(eq(expectedRequestRoomDto), eq("testUser"))).thenReturn(responseRoomDto);

        final String viewName = roomRegisterController.createRoom(expectedRequestRoomDto, bindingResult, model, userDetails);

        assertEquals("redirect:/room/" + ROOM_CODE, viewName);
        verify(roomService).createRoom(eq(expectedRequestRoomDto), eq("testUser"));
    }

    @Test
    @DisplayName("POST /room/create with validation errors should return the create room page")
    void testCreateRoomPostWithValidationErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        final String viewName = roomRegisterController.createRoom(expectedRequestRoomDto, bindingResult, model, userDetails);

        assertEquals(CREATE_ROOM_PAGE, viewName);
        verify(model).addAttribute(eq(ROOM_ATTRIBUTE), eq(expectedRequestRoomDto));
    }

    @Test
    @DisplayName("CustomBinderInitializer should register custom editors")
    void testInitBinder() {
        final WebDataBinder binder = mock(WebDataBinder.class);

        roomRegisterController.initBinder(binder);

        verify(customBinder).registerCustomEditor(eq(binder), eq("invitedUsers"), eq(ResponseUserDto[].class));
        verify(customBinder).registerCustomEditor(eq(binder), eq("stories"), eq(RequestStoryDto[].class));
    }
}
