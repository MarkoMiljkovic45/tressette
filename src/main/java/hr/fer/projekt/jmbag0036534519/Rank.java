package hr.fer.projekt.jmbag0036534519;

import java.util.Arrays;

public enum Rank {
    THREE(3, 9, 1),
    TWO(2, 8, 1),
    ACE(1, 7, 3),
    KING(13, 6, 1),
    KNIGHT(12, 5, 1),
    KNAVE(11, 4, 1),
    SEVEN(7, 3, 0),
    SIX(6, 2, 0),
    FIVE(5, 1, 0),
    FOUR(4, 0, 0);

    private final int number;
    private final int power;
    private final int value;

    Rank(int number, int power, int value) {
        this.number = number;
        this.power = power;
        this.value = value;
    }

    public int getNumber() {
        return number;
    }

    public int getPower() {
        return power;
    }

    public int getValue() {
        return value;
    }

    public static Rank fromNumber(int number) {
        return Arrays.stream(values())
                .filter(rank -> rank.number == number)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid card number: " + number));
    }
}
