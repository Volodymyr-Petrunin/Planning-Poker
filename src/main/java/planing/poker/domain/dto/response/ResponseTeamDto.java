package planing.poker.domain.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResponseTeamDto {

    @NotNull
    @Positive
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private ResponseUserDto leader;

    @NotNull
    private List<ResponseUserDto> members;

}
