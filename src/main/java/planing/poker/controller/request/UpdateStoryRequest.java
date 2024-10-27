package planing.poker.controller.request;

import lombok.Getter;
import lombok.Setter;
import planing.poker.domain.dto.request.RequestStoryDto;

@Getter
@Setter
public class UpdateStoryRequest {

    private long storyId;

    private RequestStoryDto requestStoryDto;
}
