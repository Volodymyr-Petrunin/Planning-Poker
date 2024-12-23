package planing.poker.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.ui.Model;
import planing.poker.domain.User;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.security.UserDetailsImpl;
import planing.poker.service.RoomService;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class MainControllerTest {

    private static final String BASE_PAGE = "main/main-page";

    private static final String ROOMS_CREATED = "roomsCreated";

    private static final String ROOMS_INVITED = "roomsInvited";

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    private static User expectedUser;

    @InjectMocks
    private MainController mainController;

    @Mock
    private RoomService roomService;

    @Mock
    private Model model;

    @Mock
    private UserDetailsImpl userDetails;

    @BeforeAll
    static void setUp() {
        expectedUser = fixtureMonkey.giveMeOne(User.class);
    }

    @Test
    void testIndexWithNoRooms() {
        when(userDetails.getUser(User.class)).thenReturn(expectedUser);
        when(roomService.getRoomsRelatedToUser(expectedUser)).thenReturn(Collections.emptyList());

        final String viewName = mainController.index(userDetails, model);

        assertEquals(BASE_PAGE, viewName);
        verify(model, never()).addAttribute(eq(ROOMS_CREATED), any());
        verify(model, never()).addAttribute(eq(ROOMS_INVITED), any());
    }

    @Test
    void testIndexWithRooms() {
        final List<ResponseRoomDto> rooms = fixtureMonkey.giveMe(ResponseRoomDto.class, 3);
        User user = fixtureMonkey.giveMeOne(User.class);
        when(userDetails.getUser(User.class)).thenReturn(user);
        when(roomService.getRoomsRelatedToUser(user)).thenReturn(rooms);

        final String viewName = mainController.index(userDetails, model);

        assertEquals(BASE_PAGE, viewName);
        verify(model).addAttribute(eq(ROOMS_CREATED), any());
        verify(model).addAttribute(eq(ROOMS_INVITED), any());
    }

    @Test
    void testIndexWithNoUser() {
        when(userDetails.getUser(User.class)).thenReturn(null);
        when(roomService.getRoomsRelatedToUser(null)).thenReturn(Collections.emptyList());

        final String viewName = mainController.index(userDetails, model);

        assertEquals(BASE_PAGE, viewName);
        verify(model, never()).addAttribute(eq(ROOMS_CREATED), any());
        verify(model, never()).addAttribute(eq(ROOMS_INVITED), any());
    }
}
