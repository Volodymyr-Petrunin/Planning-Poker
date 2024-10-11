package planing.poker.domain.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import planing.poker.common.validation.Password;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class RequestUserDto {

    @Size(min = 2, max = 50)
    private String firstName;

    @Size(min = 2, max = 50)
    private String lastName;

    @Size(min = 2, max = 100)
    private String nickname;

    private String email;

    @Password
    private String password;
}
