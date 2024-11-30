package planing.poker.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import planing.poker.domain.dto.request.RequestStoryDto;

import java.util.List;

@Getter
@Setter
public class CreateStoryRequest {

    @NotNull
    private List<RequestStoryDto> stories;

    @NotNull
    @Positive
    private long roomId;
}
