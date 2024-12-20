package planing.poker.common.generation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class GenerationRandomizer {
    private final Random random;

    @Autowired
    public GenerationRandomizer(@Qualifier("random") final Random random) {
        this.random = random;
    }

    public List<Character> generateRandomChars(final char startChar, final char endChar, final int count) {
        final List<Character> charList = new ArrayList<>();

        for (int index = 0; index < count; index++) {
            final char currentChar = (char) (random.nextInt(endChar - startChar + 1) + startChar);
            charList.add(currentChar);
        }

        return charList;
    }
}
