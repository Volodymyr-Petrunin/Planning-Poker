package planing.poker.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import planing.poker.domain.dto.response.ResponseUserDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RequestTeamDto {

    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    private ResponseUserDto leader;

    private List<ResponseUserDto> members;
}
