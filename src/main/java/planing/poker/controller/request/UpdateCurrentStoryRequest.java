package planing.poker.controller.request;

import lombok.Getter;
import lombok.Setter;
import planing.poker.domain.dto.response.ResponseStoryDto;

@Getter
@Setter
public class UpdateCurrentStoryRequest {

    private long storyId;

    private ResponseStoryDto responseStoryDto;
}
