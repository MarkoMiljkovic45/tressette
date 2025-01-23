package hr.fer.projekt.jmbag0036534519;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Card implements Comparable<Card> {
    private static final Map<Integer, Card> CACHE = new HashMap<>(40);

    public static final Comparator<Card> POWER_COMPARATOR = Comparator.comparingInt(Card::getPower);
    public static final Comparator<Card> SUIT_THEN_POWER_COMPARATOR =
            Comparator.comparing(Card::getSuit).thenComparing(Card::getPower, Comparator.reverseOrder());

    private final Rank rank;
    private final Suit suit;

    private Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int getNumber() {
        return rank.getNumber();
    }

    public Suit getSuit() {
        return suit;
    }

    public int getPower() {
        return rank.getPower();
    }

    public int getValue() {
        return rank.getValue();
    }

    public static Card getInstance(int number, Suit suit) {
        Rank rank = Rank.fromNumber(number);
        return getInstance(rank, suit);
    }

    public static Card getInstance(Rank rank, Suit suit) {
        int key = Objects.hash(rank, suit);
        return CACHE.computeIfAbsent(key, k -> new Card(rank, suit));
    }

    @Override
    public int compareTo(Card other) {
        return POWER_COMPARATOR.compare(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

    @Override
    public String toString() {
        return rank.getNumber() + " " + suit;
    }
}
