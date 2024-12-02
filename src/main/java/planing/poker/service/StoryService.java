package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.common.ExceptionMessages;
import planing.poker.domain.Room;
import planing.poker.domain.Story;
import planing.poker.domain.dto.request.RequestStoryDto;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.domain.dto.response.ResponseStoryDto;
import planing.poker.event.room.RoomCurrentStoryEvent;
import planing.poker.event.story.StoryCreatedEvent;
import planing.poker.event.story.StoryDeletedEvent;
import planing.poker.event.story.StoryUpdatedEvent;
import planing.poker.mapper.RoomMapper;
import planing.poker.mapper.StoryMapper;
import planing.poker.repository.StoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StoryService {

    private final StoryRepository storyRepository;

    private final StoryMapper storyMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final ExceptionMessages exceptionMessages;

    private final RoomService roomService;

    private final RoomMapper roomMapper;

    @Autowired
    public StoryService(final StoryRepository storyRepository, final StoryMapper storyMapper,
                        final ApplicationEventPublisher applicationEventPublisher, final ExceptionMessages exceptionMessages,
                        final RoomService roomService, final RoomMapper roomMapper) {
        this.storyRepository = storyRepository;
        this.storyMapper = storyMapper;
        this.applicationEventPublisher = applicationEventPublisher;
        this.exceptionMessages = exceptionMessages;
        this.roomService = roomService;
        this.roomMapper = roomMapper;
    }

    public ResponseStoryDto createStory(final RequestStoryDto responseStoryDto, final String roomCode) {
        final ResponseStoryDto createdStory = storyMapper.toDto(
                storyRepository.save(storyMapper.toEntity(responseStoryDto)));
        applicationEventPublisher.publishEvent(new StoryCreatedEvent(createdStory, roomCode));
        return createdStory;
    }

    public List<ResponseStoryDto> createSeveralStory(final List<RequestStoryDto> storiesDto, final Long roomId,
                                                     final String roomCode) {
        final List<Story> stories = storiesDto.stream().map(storyMapper::toEntity).toList();

        final List<Story> savedStories = storyRepository.saveAll(stories);

        if (roomId != null) {
            roomService.associateStoriesWithRoom(stories, roomId);
        }

        final List<ResponseStoryDto> responseStories = savedStories.stream().map(storyMapper::toDto).toList();

        responseStories.forEach(story -> applicationEventPublisher.publishEvent(new StoryCreatedEvent(story, roomCode)));

        return responseStories;
    }

    public List<ResponseStoryDto> getAllStories() {
        return storyRepository.findAll().stream().map(storyMapper::toDto).toList();
    }

    public List<ResponseStoryDto> getStoriesByRoom(final long roomId) {
        final Room room = roomMapper.responseToEntity(roomService.getRoomById(roomId));
        return room.getStories().stream().map(storyMapper::toDto).toList();
    }

    public ResponseStoryDto getStoryById(final Long id) {
        return storyMapper.toDto(storyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE())));
    }

    public ResponseStoryDto updateStory(final long id, final RequestStoryDto storyDto, final String roomCode) {
        if (storyRepository.findById(id).isPresent()) {
            final Story story = storyMapper.toEntity(storyDto);
            story.setId(id);

            final ResponseStoryDto updatedStory = storyMapper.toDto(storyRepository.save(story));
            applicationEventPublisher.publishEvent(new StoryUpdatedEvent(updatedStory, roomCode));

            notifyIfCurrentStory(story, roomCode);

            return updatedStory;
        } else {
            throw new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE());
        }
    }

    public void deleteStory(final Long id, final String roomCode) {
        Optional<Story> storyOptional = storyRepository.findById(id);
        if (storyOptional.isPresent()) {
            final Optional<Room> roomOptional = roomService.optionalRoomByCurrentStory(storyOptional.get());

            roomOptional.ifPresent(room -> roomService.updateCurrentStory(room.getId(), null, roomCode));

            storyRepository.deleteById(id);
            applicationEventPublisher.publishEvent(new StoryDeletedEvent(id, roomCode));
        } else {
            throw new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE());
        }
    }

    private void notifyIfCurrentStory(final Story story, final String roomCode) {
        final Optional<Room> roomOptional = roomService.optionalRoomByCurrentStory(story);

        if (roomOptional.isPresent()) {
            ResponseRoomDto room = roomMapper.toDto(roomOptional.get());
            applicationEventPublisher.publishEvent(new RoomCurrentStoryEvent(room, roomCode));
        }
    }
}
