package planing.poker.controller.integration;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.WebDataBinder;
import planing.poker.common.CustomBinderInitializer;
import planing.poker.controller.RoomRegisterController;
import planing.poker.controller.common.WithCustomAuthenticationPrincipal;
import planing.poker.domain.dto.request.RequestRoomDto;
import planing.poker.domain.dto.request.RequestStoryDto;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.security.WebSecurityConfiguration;
import planing.poker.security.WebSocketConfig;
import planing.poker.service.RoomService;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomRegisterController.class)
@Import({WebSocketConfig.class, WebSecurityConfiguration.class})
@DisplayName("Room Register Controller Integration Test")
class RoomRegisterControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @MockBean
    private CustomBinderInitializer customBinder;

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    private static final String BASE_URL = RoomRegisterController.BASE_URL;

    private static final String CREATE_ROOM_URL = "/create";

    private static final String CREATE_ROOM_PAGE = "register/room/reg-room";

    private static final String ROOM_ATTRIBUTE = "room";

    @Test
    @DisplayName("GET /room/create should return the create room page with model attributes")
    @WithMockUser
    void testGetCreateRoomPage() throws Exception {
        mockMvc.perform(get(BASE_URL + CREATE_ROOM_URL))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(ROOM_ATTRIBUTE))
                .andExpect(view().name(CREATE_ROOM_PAGE));
    }

    @Test
    @DisplayName("POST /room/create with valid data should redirect to the room page")
    @WithCustomAuthenticationPrincipal(username = "testUser")
    void testPostCreateRoom_ValidRequest() throws Exception {
        final RequestRoomDto room = fixtureMonkey.giveMeOne(RequestRoomDto.class);
        final ResponseRoomDto responseRoomDto = fixtureMonkey.giveMeOne(ResponseRoomDto.class);
        responseRoomDto.setRoomCode("12345");

        when(roomService.createRoom(room, "testUser")).thenReturn(responseRoomDto);

        mockMvc.perform(post(BASE_URL + CREATE_ROOM_URL)
                        .with(csrf())
                        .flashAttr(ROOM_ATTRIBUTE, room))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(BASE_URL + "/12345"));
    }

    @Test
    @DisplayName("POST /room/create with validation errors should return the create room page")
    @WithCustomAuthenticationPrincipal
    void testPostCreateRoom_WithValidationErrors() throws Exception {
        final RequestRoomDto room = new RequestRoomDto();

        mockMvc.perform(post(BASE_URL + CREATE_ROOM_URL)
                        .with(csrf())
                        .flashAttr(ROOM_ATTRIBUTE, room))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(ROOM_ATTRIBUTE))
                .andExpect(view().name(CREATE_ROOM_PAGE));
    }

    @Test
    @DisplayName("CustomBinderInitializer should register custom editors correctly")
    void testInitBinder() {
        final WebDataBinder binder = mock(WebDataBinder.class);

        new RoomRegisterController(roomService, customBinder).initBinder(binder);

        verify(customBinder, times(1)).registerCustomEditor(eq(binder), eq("invitedUsers"), eq(ResponseUserDto[].class));
        verify(customBinder, times(1)).registerCustomEditor(eq(binder), eq("stories"), eq(RequestStoryDto[].class));
    }
}
