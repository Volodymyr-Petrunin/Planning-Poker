package planing.poker.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import planing.poker.common.ExceptionMessages;
import planing.poker.domain.Room;
import planing.poker.domain.Story;
import planing.poker.domain.dto.request.RequestStoryDto;
import planing.poker.domain.dto.response.ResponseStoryDto;
import planing.poker.mapper.RoomMapper;
import planing.poker.mapper.StoryMapper;
import planing.poker.repository.StoryRepository;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@DisplayName("Story Service Tests")
class StoryServiceTest {

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    private static ResponseStoryDto expectedResponseDto;

    private static RequestStoryDto expectedRequestDto;

    private static Story expectedEntity;

    @InjectMocks
    private StoryService storyService;

    @Mock
    private RoomService roomService;

    @Mock
    private StoryRepository storyRepository;

    @Mock
    private StoryMapper storyMapper;

    @Mock
    private RoomMapper roomMapper;

    @Mock
    private ExceptionMessages exceptionMessages;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @BeforeAll
    static void setUp() {
        expectedResponseDto = fixtureMonkey.giveMeOne(ResponseStoryDto.class);
        expectedRequestDto  = fixtureMonkey.giveMeOne(RequestStoryDto.class);
        expectedEntity = fixtureMonkey.giveMeOne(Story.class);
    }

    @Test
    @DisplayName("Create Story: Should create story and return correct DTO")
    void testCreateStory_ShouldCreateStory_AndReturnCorrectDto() {
        when(storyMapper.toEntity(expectedRequestDto)).thenReturn(expectedEntity);
        when(storyRepository.save(expectedEntity)).thenReturn(expectedEntity);
        when(storyMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final ResponseStoryDto createdStory = storyService.createStory(expectedRequestDto, any());

        assertNotNull(createdStory);

        verify(storyRepository, times(1)).save(expectedEntity);
        verify(storyMapper, times(1)).toEntity(expectedRequestDto);
        verify(storyMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get All Stories: Should return all stories as a list of DTOs")
    void testGetAllStories_ShouldReturnAllStories() {
        when(storyRepository.findAll()).thenReturn(List.of(expectedEntity));
        when(storyMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final List<ResponseStoryDto> stories = storyService.getAllStories();

        assertNotNull(stories);
        assertEquals(1, stories.size());

        verify(storyRepository, times(1)).findAll();
        verify(storyMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get Story By ID: Should return the correct story DTO for given ID")
    void testGetStoryById_ShouldReturnStory() {
        when(storyRepository.findById(expectedResponseDto.getId())).thenReturn(Optional.of(expectedEntity));
        when(storyMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final ResponseStoryDto story = storyService.getStoryById(expectedResponseDto.getId());

        assertNotNull(story);
        assertEquals(expectedResponseDto.getId(), story.getId());

        verify(storyRepository, times(1)).findById(expectedResponseDto.getId());
        verify(storyMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get Story By ID: Should throw exception if story is not found")
    void testGetStoryById_ShouldThrowExceptionIfNotFound() {
        when(storyRepository.findById(expectedResponseDto.getId())).thenReturn(Optional.empty());
        when(exceptionMessages.NO_FIND_MESSAGE()).thenReturn("Error Message!");

        assertThrows(IllegalArgumentException.class, () -> storyService.getStoryById(expectedResponseDto.getId()));

        verify(storyRepository, times(1)).findById(expectedResponseDto.getId());
    }

    @Test
    @DisplayName("Update Story: Should update story and return the updated DTO")
    void testUpdateStory_ShouldUpdateStory_AndReturnUpdatedDto() {
        when(storyMapper.toEntity(expectedRequestDto)).thenReturn(expectedEntity);
        when(storyRepository.findById(1L)).thenReturn(Optional.of(expectedEntity));
        when(roomService.optionalRoomByCurrentStory(expectedEntity)).thenReturn(Optional.of(fixtureMonkey.giveMeOne(Room.class)));
        when(storyRepository.save(expectedEntity)).thenReturn(expectedEntity);
        when(storyMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final ResponseStoryDto updatedStory = storyService.updateStory(1 ,expectedRequestDto, any());

        assertNotNull(updatedStory);
        verify(storyRepository, times(1)).save(expectedEntity);
        verify(storyMapper, times(1)).toEntity(expectedRequestDto);
        verify(storyMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Delete Story: Should delete story by given ID")
    void testDeleteStory_ShouldDeleteStory() {
        doNothing().when(storyRepository).deleteById(expectedResponseDto.getId());
        when(storyRepository.findById(expectedResponseDto.getId())).thenReturn(Optional.of(expectedEntity));

        storyService.deleteStory(expectedResponseDto.getId(), any());

        verify(storyRepository, times(1)).deleteById(expectedResponseDto.getId());
    }
}
