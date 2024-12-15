package planing.poker.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import planing.poker.domain.dto.request.RequestRoomDto;

import java.time.LocalDateTime;

public class ValidDateTimeValidator implements ConstraintValidator<ValidDateTime, RequestRoomDto> {

    @Override
    public boolean isValid(final RequestRoomDto room, final ConstraintValidatorContext constraintValidatorContext) {
        if (room.getStartDate() == null || room.getStartTime() == null) {
            return true;
        }

        final LocalDateTime dateTime = LocalDateTime.of(room.getStartDate(), room.getStartTime());
        return dateTime.isAfter(LocalDateTime.now());
    }
}
