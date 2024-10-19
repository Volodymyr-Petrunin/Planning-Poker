package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planing.poker.domain.dto.request.RequestStoryDto;
import planing.poker.domain.dto.response.ResponseStoryDto;
import planing.poker.mapper.StoryMapper;
import planing.poker.repository.StoryRepository;

import java.util.List;

@Service
public class StoryService {

    private final StoryRepository storyRepository;

    private final StoryMapper storyMapper;

    @Autowired
    public StoryService(final StoryRepository storyRepository, final StoryMapper storyMapper) {
        this.storyRepository = storyRepository;
        this.storyMapper = storyMapper;
    }

    public ResponseStoryDto createStory(final RequestStoryDto responseStoryDto) {
        return storyMapper.toDto(storyRepository.save(storyMapper.toEntity(responseStoryDto)));
    }

    public List<ResponseStoryDto> getAllStories() {
        return storyRepository.findAll().stream().map(storyMapper::toDto).toList();
    }

    public ResponseStoryDto getStoryById(final Long id) {
        return storyMapper.toDto(storyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("message.not.find.object")));
    }

    public ResponseStoryDto updateStory(final RequestStoryDto responseStoryDto) {
        return storyMapper.toDto(storyRepository.save(storyMapper.toEntity(responseStoryDto)));
    }

    public void deleteStory(final Long id) {
        storyRepository.deleteById(id);
    }
}
