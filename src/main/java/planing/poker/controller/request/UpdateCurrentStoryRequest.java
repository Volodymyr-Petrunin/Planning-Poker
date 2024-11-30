package planing.poker.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import planing.poker.domain.dto.response.ResponseStoryDto;

@Getter
@Setter
public class UpdateCurrentStoryRequest {

    @NotNull
    private long storyId;

    @NotNull
    private ResponseStoryDto responseStoryDto;
}
