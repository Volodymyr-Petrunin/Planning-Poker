package planing.poker.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import planing.poker.domain.Story;
import planing.poker.domain.dto.request.RequestStoryDto;
import planing.poker.domain.dto.response.ResponseStoryDto;
import planing.poker.factory.utils.ExpectedEntityDtoUtils;
import planing.poker.factory.utils.ExpectedEntityUtils;
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
    private static final ResponseStoryDto EXPECTED_DTO = ExpectedEntityDtoUtils.getStory();
    private static final Story EXPECTED_ENTITY = ExpectedEntityUtils.getStory();

    @InjectMocks
    private StoryService storyService;

    @Mock
    private StoryRepository storyRepository;

    @Mock
    private StoryMapper storyMapper;

    @Test
    @DisplayName("Create Story: Should create story and return correct DTO")
    void testCreateStory_ShouldCreateStory_AndReturnCorrectDto() {
        final RequestStoryDto requestStoryDto = new RequestStoryDto();
        when(storyMapper.toEntity(requestStoryDto)).thenReturn(EXPECTED_ENTITY);
        when(storyRepository.save(EXPECTED_ENTITY)).thenReturn(EXPECTED_ENTITY);
        when(storyMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final ResponseStoryDto createdStory = storyService.createStory(requestStoryDto, any());

        assertNotNull(createdStory);

        verify(storyRepository, times(1)).save(EXPECTED_ENTITY);
        verify(storyMapper, times(1)).toEntity(requestStoryDto);
        verify(storyMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get All Stories: Should return all stories as a list of DTOs")
    void testGetAllStories_ShouldReturnAllStories() {
        when(storyRepository.findAll()).thenReturn(List.of(EXPECTED_ENTITY));
        when(storyMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final List<ResponseStoryDto> stories = storyService.getAllStories();

        assertNotNull(stories);
        assertEquals(1, stories.size());

        verify(storyRepository, times(1)).findAll();
        verify(storyMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get Story By ID: Should return the correct story DTO for given ID")
    void testGetStoryById_ShouldReturnStory() {
        when(storyRepository.findById(EXPECTED_DTO.getId())).thenReturn(Optional.of(EXPECTED_ENTITY));
        when(storyMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final ResponseStoryDto story = storyService.getStoryById(EXPECTED_DTO.getId());

        assertNotNull(story);
        assertEquals(EXPECTED_DTO.getId(), story.getId());

        verify(storyRepository, times(1)).findById(EXPECTED_DTO.getId());
        verify(storyMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get Story By ID: Should throw exception if story is not found")
    void testGetStoryById_ShouldThrowExceptionIfNotFound() {
        when(storyRepository.findById(EXPECTED_DTO.getId())).thenReturn(Optional.empty());

        final Exception exception = assertThrows(
                IllegalArgumentException.class, () -> storyService.getStoryById(EXPECTED_DTO.getId()));

        assertEquals("message.not.find.object", exception.getMessage());

        verify(storyRepository, times(1)).findById(EXPECTED_DTO.getId());
    }

    @Test
    @DisplayName("Update Story: Should update story and return the updated DTO")
    void testUpdateStory_ShouldUpdateStory_AndReturnUpdatedDto() {
        final RequestStoryDto requestStoryDto = new RequestStoryDto();
        when(storyMapper.toEntity(requestStoryDto)).thenReturn(EXPECTED_ENTITY);
        when(storyRepository.save(EXPECTED_ENTITY)).thenReturn(EXPECTED_ENTITY);
        when(storyMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final ResponseStoryDto updatedStory = storyService.updateStory(1 ,requestStoryDto, any());

        assertNotNull(updatedStory);

        verify(storyRepository, times(1)).save(EXPECTED_ENTITY);
        verify(storyMapper, times(1)).toEntity(requestStoryDto);
        verify(storyMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Delete Story: Should delete story by given ID")
    void testDeleteStory_ShouldDeleteStory() {
        doNothing().when(storyRepository).deleteById(EXPECTED_DTO.getId());

        storyService.deleteStory(EXPECTED_DTO.getId(), any());

        verify(storyRepository, times(1)).deleteById(EXPECTED_DTO.getId());
    }
}
