package planing.poker.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import planing.poker.domain.dto.request.RequestStoryDto;

@Getter
@Setter
public class UpdateStoryRequest {

    @NotNull
    @Positive
    private long storyId;

    @NotNull
    @Valid
    private RequestStoryDto requestStoryDto;

    @NotNull
    @NotBlank
    private String roomCode;
}
