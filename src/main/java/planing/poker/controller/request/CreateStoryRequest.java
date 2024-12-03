package planing.poker.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
    @Valid
    private List<RequestStoryDto> stories;

    @NotNull
    @Positive
    private long roomId;

    @NotNull
    @NotBlank
    private String roomCode;
}
