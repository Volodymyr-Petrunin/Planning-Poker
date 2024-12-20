package planing.poker.domain.dto.update;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import planing.poker.common.validation.UniqueEmailPatch;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@UniqueEmailPatch
public class UpdateUserDto {

    @NotNull
    @Positive
    private Long id;

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
    private String email;
}
