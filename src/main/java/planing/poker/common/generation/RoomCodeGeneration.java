package planing.poker.common.generation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    public RoomCodeGeneration(@Value("${room.code.numbers}") short numbers, @Value("${room.code.lowerCase}") short lowerCase,
                              @Value("${room.code.upperCase}") short upperCase, final GenerationRandomizer generationRandomizer,
                              @Qualifier("random") final Random random) {
        this.upperCase = upperCase;
        this.numbers = numbers;
        this.lowerCase = lowerCase;
        this.generationRandomizer = generationRandomizer;
        this.random = random;
    }

    public String generateCode() {
        if (numbers < 0 || lowerCase < 0 || upperCase < 0) {
            throw new IllegalArgumentException("message.invalid.code.configuration");
        }

        List<Character> code = new ArrayList<>();

        code.addAll(generationRandomizer.generateRandomChars('A', 'Z', upperCase));

        code.addAll(generationRandomizer.generateRandomChars('0', '9', numbers));

        code.addAll(generationRandomizer.generateRandomChars('a', 'z', lowerCase));

        Collections.shuffle(code, random);

        StringBuilder result = new StringBuilder();

        for (char character : code) {
            result.append(character);
        }

        return result.toString();
    }
}
