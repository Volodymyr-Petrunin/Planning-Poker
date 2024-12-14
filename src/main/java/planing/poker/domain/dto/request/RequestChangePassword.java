package planing.poker.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import planing.poker.common.validation.Password;

import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestChangePassword {

    @NotNull
    @Positive
    private Long userId;

    @Password
    private char[] password;

    @Password
    private char[] passwordConfirm;

    public void clearPasswords() {
        Arrays.fill(password, '\0');
        Arrays.fill(passwordConfirm, '\0');
    }
}

