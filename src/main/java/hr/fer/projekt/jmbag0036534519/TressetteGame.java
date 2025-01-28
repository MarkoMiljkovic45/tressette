package hr.fer.projekt.jmbag0036534519;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TressetteGame {
    private static final int MAX_SCORE = 35;
    private static final int MIN_SCORE = -35;

    public static List<Card> generateDeck() {
        List<Card> deck = new ArrayList<>(40);
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(Card.getInstance(rank, suit));
            }
        }
        return deck;
    }

    public static List<Card> generateRandomDeck() {
        List<Card> deck = generateDeck();
        Collections.shuffle(deck);
        return deck;
    }

    public static List<List<Card>> dealCards(List<Card> deck) {
        return List.of(
                deck.subList(0, 10),
                deck.subList(10, 20),
                deck.subList(20, 30),
                deck.subList(30, 40)
        );
    }

    public static void printHands(List<List<Card>> hands) {
        for (int i = 0; i < hands.size(); i++) {
            System.out.println("Player " + (i + 1));
            System.out.println(hands.get(i).stream().sorted(Card.SUIT_THEN_POWER_COMPARATOR).toList());
            System.out.println();
        }
    }

    public static int calculateBonusPoints(List<Card> hand) {
        int bonusPoints = 0;

        Map<Suit, List<Card>> cardsBySuit = hand.stream()
                .collect(Collectors.groupingBy(Card::getSuit));

        for (Suit suit : cardsBySuit.keySet()) {
            List<Card> cardsInSuit = cardsBySuit.get(suit);
            boolean has3 = cardsInSuit.stream().anyMatch(card -> card.getRank().equals(Rank.THREE));
            boolean has2 = cardsInSuit.stream().anyMatch(card -> card.getRank().equals(Rank.TWO));
            boolean has1 = cardsInSuit.stream().anyMatch(card -> card.getRank().equals(Rank.ACE));
            if (has3 && has2 && has1) {
                bonusPoints += 9;
            }
        }

        Map<Rank, Long> cardCounts = hand.stream()
                .filter(card -> card.getRank().equals(Rank.THREE) ||
                                     card.getRank().equals(Rank.TWO) ||
                                     card.getRank().equals(Rank.ACE))
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));

        for (Map.Entry<Rank, Long> entry : cardCounts.entrySet()) {
            if (entry.getValue() == 3) {
                bonusPoints += 9;
            }
            if (entry.getValue() == 4) {
                bonusPoints += 12;
            }
        }

        return bonusPoints;
    }

    public static State eval(State root, int maxDepth) {
        maxValue(root, MIN_SCORE, MAX_SCORE, maxDepth);
        return root;
    }

    public static State eval(List<List<Card>> hands, List<Card> pot, int maxDepth) {
        return eval(new State(hands, pot, 0, true, 0), maxDepth);
    }

    public static State eval(List<List<Card>> hands, int maxDepth) {
        return eval(hands, new ArrayList<>(4), maxDepth);
    }

    private static int maxValue(State state, int alpha, int beta, int maxDepth) {
        if (state.isTerminal(maxDepth)) {
            return state.getScore();
        }

        int m = alpha;
        for (State successor: state.getSuccessors()) {
            int successorValue = (successor.isMinimizer())
                ? minValue(successor, m, beta, maxDepth)
                : maxValue(successor, m, beta, maxDepth);

            if (successorValue > m) {
                m = successorValue;
                state.setMinimaxSuccessor(successor);
            }

            if (m >= beta && successor.isMinimizer()) {
                return beta;
            }
        }

        return m;
    }

    private static int minValue(State state, int alpha, int beta, int maxDepth) {
        if (state.isTerminal(maxDepth)) {
            return state.getScore();
        }

        int m = beta;
        for (State successor: state.getSuccessors()) {
            int successorValue = (successor.isMaximizer())
                    ? maxValue(successor, alpha, m, maxDepth)
                    : minValue(successor, alpha, m, maxDepth);

            if (successorValue < m) {
                m = successorValue;
                state.setMinimaxSuccessor(successor);
            }

            if (m <= alpha && successor.isMaximizer()) {
                return alpha;
            }
        }

        return m;
    }
}