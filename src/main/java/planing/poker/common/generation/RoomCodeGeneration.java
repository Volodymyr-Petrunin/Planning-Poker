package planing.poker.common.generation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import planing.poker.common.ExceptionMessages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class RoomCodeGeneration {

    private final short upperCase;

    private final short numbers;

    private final short lowerCase;

    private final GenerationRandomizer generationRandomizer;

    private final Random random;

    private final ExceptionMessages exceptionMessages;

    public RoomCodeGeneration(@Value("${room.code.numbers}") final short numbers,
                              @Value("${room.code.lowerCase}") final short lowerCase,
                              @Value("${room.code.upperCase}") final short upperCase,
                              final GenerationRandomizer generationRandomizer,
                              @Qualifier("random") final Random random,
                              final ExceptionMessages exceptionMessages) {
        this.upperCase = upperCase;
        this.numbers = numbers;
        this.lowerCase = lowerCase;
        this.generationRandomizer = generationRandomizer;
        this.random = random;
        this.exceptionMessages = exceptionMessages;
    }

    public String generateCode() {
        if (numbers < 0 || lowerCase < 0 || upperCase < 0) {
            throw new IllegalArgumentException(exceptionMessages.INVALID_CODE_CONFIGURATION());
        }

        final List<Character> code = new ArrayList<>();

        code.addAll(generationRandomizer.generateRandomChars('A', 'Z', upperCase));

        code.addAll(generationRandomizer.generateRandomChars('0', '9', numbers));

        code.addAll(generationRandomizer.generateRandomChars('a', 'z', lowerCase));

        Collections.shuffle(code, random);

        final StringBuilder result = new StringBuilder();

        for (final char character : code) {
            result.append(character);
        }

        return result.toString();
    }
}
