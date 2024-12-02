package planing.poker.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteStoryRequest {
    @NotNull
    @Positive
    private long storyId;

    private String roomCode;
}
