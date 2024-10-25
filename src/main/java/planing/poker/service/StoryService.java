package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.common.Messages;
import planing.poker.domain.Room;
import planing.poker.domain.Story;
import planing.poker.domain.dto.request.RequestStoryDto;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.domain.dto.response.ResponseStoryDto;
import planing.poker.event.story.StoryCreatedEvent;
import planing.poker.event.story.StoryDeletedEvent;
import planing.poker.event.story.StoryUpdatedEvent;
import planing.poker.mapper.StoryMapper;
import planing.poker.repository.RoomRepository;
import planing.poker.repository.StoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StoryService {

    private final StoryRepository storyRepository;

    private final StoryMapper storyMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final Messages messages;

    private final RoomRepository roomRepository;

    @Autowired
    public StoryService(final StoryRepository storyRepository, final StoryMapper storyMapper,
                        final ApplicationEventPublisher applicationEventPublisher, final Messages messages,
                        final RoomRepository roomRepository) {
        this.storyRepository = storyRepository;
        this.storyMapper = storyMapper;
        this.applicationEventPublisher = applicationEventPublisher;
        this.messages = messages;
        this.roomRepository = roomRepository;
    }

    public ResponseStoryDto createStory(final RequestStoryDto responseStoryDto) {
        final ResponseStoryDto createdStory = storyMapper.toDto(
                storyRepository.save(storyMapper.toEntity(responseStoryDto)));
        applicationEventPublisher.publishEvent(new StoryCreatedEvent(createdStory));
        return createdStory;
    }

    public List<ResponseStoryDto> createSeveralStory(final List<RequestStoryDto> storiesDto, final Long roomId) {
        final List<Story> stories = storiesDto.stream().map(storyMapper::toEntity).toList();

        final List<Story> savedStories = storyRepository.saveAll(stories);

        if (roomId != null) {
            associateStoriesWithRoom(stories, roomId);
        }

        final List<ResponseStoryDto> responseStories = savedStories.stream().map(storyMapper::toDto).toList();

        responseStories.forEach(story -> applicationEventPublisher.publishEvent(new StoryCreatedEvent(story)));

        return responseStories;
    }

    public List<ResponseStoryDto> getAllStories() {
        return storyRepository.findAll().stream().map(storyMapper::toDto).toList();
    }

    public List<ResponseStoryDto> getStoriesByRoom(final long roomId) {
        final Room room = findRoomEntityById(roomId);
        return room.getStories().stream().map(storyMapper::toDto).toList();
    }

    public ResponseStoryDto getStoryById(final Long id) {
        return storyMapper.toDto(storyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(messages.NO_FIND_MESSAGE())));
    }

    public ResponseStoryDto updateStory(final long id, final RequestStoryDto storyDto) {
        if (storyRepository.findById(id).isPresent()) {
            final Story story = storyMapper.toEntity(storyDto);
            story.setId(id);

            final ResponseStoryDto updatedStory = storyMapper.toDto(storyRepository.save(story));
            applicationEventPublisher.publishEvent(new StoryUpdatedEvent(updatedStory));
            return updatedStory;
        } else {
            throw new IllegalArgumentException(messages.NO_FIND_MESSAGE());
        }
    }

    public void deleteStory(final Long id) {
        if (storyRepository.findById(id).isPresent()) {
            storyRepository.deleteById(id);
            applicationEventPublisher.publishEvent(new StoryDeletedEvent(id));
        } else {
            throw new IllegalArgumentException(messages.NO_FIND_MESSAGE());
        }
    }

    private void associateStoriesWithRoom(final List<Story> stories, final Long roomId) {
        final Room room = findRoomEntityById(roomId);
        room.getStories().addAll(stories);

        roomRepository.save(room);
    }

    private Room findRoomEntityById(final long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException(messages.NO_FIND_MESSAGE()));
    }
}
