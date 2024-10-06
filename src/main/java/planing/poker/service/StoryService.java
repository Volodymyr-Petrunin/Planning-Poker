package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planing.poker.domain.dto.StoryDto;
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

    public StoryDto createStory(final StoryDto storyDto) {
        return storyMapper.toDto(storyRepository.save(storyMapper.toEntity(storyDto)));
    }

    public List<StoryDto> getAllStories() {
        return storyRepository.findAll().stream().map(storyMapper::toDto).toList();
    }

    public StoryDto getStoryById(final Long id) {
        return storyMapper.toDto(storyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("message.not.find.object")));
    }

    public StoryDto updateStory(final StoryDto storyDto) {
        return storyMapper.toDto(storyRepository.save(storyMapper.toEntity(storyDto)));
    }

    public void deleteStory(final Long id) {
        storyRepository.deleteById(id);
    }
}
