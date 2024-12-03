package planing.poker.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RequestStoryDto {

    @NotNull
    private String title;

    @NotNull
    @URL
    private String storyLink;
}
