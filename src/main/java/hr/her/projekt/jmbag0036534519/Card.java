package hr.her.projekt.jmbag0036534519;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Card {
    private static final Map<Integer, Card> CACHE = new HashMap<>(40);
    public static final Comparator<Card> ORDER_COMPARATOR =
            Comparator.comparing(Card::getSuit).thenComparing(Card::getPriority);
    public static final Comparator<Card> POWER_COMPARATOR =
            Comparator.comparingInt(Card::getPriority);

    private static final int[] cardPriority = {
            11, // 0 doesn't exist
            3, // number 1 = priority 3
            2, // number 2 = priority 2
            1, // number 3 = priority 1
            10, // number 4 = priority 10
            9, // number 5 = priority 9
            8, // number 6 = priority 8
            7, // number 7 = priority 7
            11, // number 8 doesn't exist
            11, // number 9 doesn't exist
            11, // number 10 doesn't exist
            6, // number 11 = priority 6
            5, // number 12 = priority 5
            4  // number 13 = priority 4
    };

    private final int number;
    private final Suit suit;

    private Card(int number, Suit suit) {
        this.number = number;
        this.suit = suit;
    }

    public int getNumber() {
        return number;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        if (number == 1) {
            return 3;
        } else if (getPriority() <= 6) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getPriority() {
        if (number >= 1 && number <= 13) {
            return cardPriority[number];
        } else {
            throw new IllegalArgumentException("Invalid card number: " + number);
        }
    }

    public static Card getInstance(int number, Suit suit) {
        int key = Objects.hash(number, suit);
        return CACHE.computeIfAbsent(key, k -> new Card(number, suit));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return number == card.number && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, suit);
    }

    @Override
    public String toString() {
        return String.format("%2d %s", number, suit);
    }
}