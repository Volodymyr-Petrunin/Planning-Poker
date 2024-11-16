package planing.poker.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import planing.poker.common.validation.UniqueEmail;
import planing.poker.common.validation.Password;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class RequestUserDto {

    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank
    @Size(min = 2, max = 100)
    private String nickname;

    @NotBlank
    @Email
    @UniqueEmail
    private String email;

    @NotBlank
    @Password
    private String password;
}
