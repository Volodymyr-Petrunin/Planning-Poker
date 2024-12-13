package planing.poker.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import planing.poker.domain.dto.update.UpdateUserDto;
import planing.poker.repository.UserRepository;

@Component
public class UniqueEmailPatchValidator implements ConstraintValidator<UniqueEmailPatch, UpdateUserDto> {

    private final UserRepository userRepository;

    @Autowired
    public UniqueEmailPatchValidator(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(final UpdateUserDto dto, final ConstraintValidatorContext context) {
        if (dto == null || dto.getEmail() == null || dto.getId() == null) {
            return true;
        }

        final var user = userRepository.findById(dto.getId());
        if (user.isPresent() && user.get().getEmail().equals(dto.getEmail())) {
            return true;
        }

        return !userRepository.existsByEmail(dto.getEmail());
    }
}

