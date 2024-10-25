package planing.poker.controller.request;

import lombok.Getter;
import lombok.Setter;
import planing.poker.domain.dto.request.RequestStoryDto;

import java.util.List;

@Getter
@Setter
public class CreateStoryRequest {

    private List<RequestStoryDto> stories;

    private long roomId;
}
