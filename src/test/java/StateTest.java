import hr.fer.projekt.jmbag0036534519.Card;
import hr.fer.projekt.jmbag0036534519.State;
import hr.fer.projekt.jmbag0036534519.Suit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class StateTest {

    @Test
    void testGetPotTakerIndex1() {
        List<Card> basePot = List.of(
                Card.getInstance(4, Suit.BASTONI),
                Card.getInstance(5, Suit.BASTONI),
                Card.getInstance(6, Suit.BASTONI),
                Card.getInstance(7, Suit.BASTONI)
        );

        for (int expectedIndex = 0; expectedIndex < 4; expectedIndex++) {
            List<Card> pot = new ArrayList<>(basePot);
            pot.set(expectedIndex, Card.getInstance(3, Suit.BASTONI));
            int actualIndex = State.getPotTakerIndex(pot);
            assertEquals(expectedIndex, actualIndex, "The pot taker index does not match the expected value.");
        }
    }

    @Test
    void testGetPotTakerIndex2() {
        List<Card> basePot = List.of(
                Card.getInstance(4, Suit.BASTONI),
                Card.getInstance(5, Suit.SPADE),
                Card.getInstance(6, Suit.SPADE),
                Card.getInstance(7, Suit.SPADE)
        );

        for (int expectedIndex = 0; expectedIndex < 4; expectedIndex++) {
            List<Card> pot = new ArrayList<>(basePot);
            pot.set(expectedIndex, Card.getInstance(3, Suit.BASTONI));
            int actualIndex = State.getPotTakerIndex(pot);
            assertEquals(expectedIndex, actualIndex, "The pot taker index does not match the expected value.");
        }
    }
}