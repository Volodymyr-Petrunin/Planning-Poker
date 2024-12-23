package planing.poker.controller.integration;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import planing.poker.controller.MainController;
import planing.poker.controller.common.WithCustomAuthenticationPrincipal;
import planing.poker.domain.User;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.security.WebSecurityConfiguration;
import planing.poker.security.WebSocketConfig;
import planing.poker.service.RoomService;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
@Import({WebSocketConfig.class, WebSecurityConfiguration.class})
@DisplayName("Main Controller Integration Tests")
class MainControllerIntegrationTest {

    private static final String BASE_URL = "/";

    private static final String BASE_PAGE = "main/main-page";

    private static final String ROOMS_CREATED = "roomsCreated";

    private static final String ROOMS_INVITED = "roomsInvited";

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    private static User expectedUser;

    private static ResponseUserDto expectedResponseUserDto;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @BeforeAll
    static void setUp() {
        expectedUser = fixtureMonkey.giveMeOne(User.class);
        expectedResponseUserDto = fixtureMonkey.giveMeOne(ResponseUserDto.class);
    }

    @Test
    @WithCustomAuthenticationPrincipal
    @DirtiesContext
    void testMainPageWithRooms() throws Exception {
        final List<ResponseRoomDto> rooms = fixtureMonkey.giveMe(ResponseRoomDto.class, 3);
        rooms.forEach(room -> room.setCreator(expectedResponseUserDto));

        when(roomService.getRoomsRelatedToUser(any())).thenReturn(rooms);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(BASE_PAGE))
                .andExpect(model().attributeExists(ROOMS_CREATED))
                .andExpect(model().attributeExists(ROOMS_INVITED));
    }

    @Test
    @WithMockUser
    void testMainPageWithNoRooms() throws Exception {
        when(roomService.getRoomsRelatedToUser(expectedUser)).thenReturn(Collections.emptyList());

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(BASE_PAGE))
                .andExpect(model().attributeDoesNotExist(ROOMS_CREATED))
                .andExpect(model().attributeDoesNotExist(ROOMS_INVITED));
    }

    @Test
    @WithAnonymousUser
    void testMainPageForAnonymousUser() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(BASE_PAGE));
    }
}

