package hr.her.projekt.jmbag0036534519;

import java.util.*;
import java.util.stream.Collectors;

public class TressetteGame {

    private final List<List<Card>> hands;

    public TressetteGame() {
        this.hands = new ArrayList<>(4);
        List<Card> deck = generateRandomDeck();
        hands.add(new ArrayList<>(deck.subList(0, 10)));
        hands.add(new ArrayList<>(deck.subList(10, 20)));
        hands.add(new ArrayList<>(deck.subList(20, 30)));
        hands.add(new ArrayList<>(deck.subList(30, 40)));
    }

    private static List<Card> generateDeck() {
        List<Card> deck = new ArrayList<>(40);
        for (Suit suit : Suit.values()) {
            for (int number = 1; number <= 7; number++) {
                deck.add(Card.getInstance(number, suit));
            }
            for (int number = 11; number <= 13; number++) {
                deck.add(Card.getInstance(number, suit));
            }
        }
        return deck;
    }

    public List<List<Card>> getHands() {
        return hands;
    }

    private static List<Card> generateRandomDeck() {
        List<Card> deck = generateDeck();
        Collections.shuffle(deck);
        return deck;
    }

    public static int calculateBonusPoints(List<Card> hand) {
        int bonusPoints = 0;

        Map<Suit, List<Card>> cardsBySuit = hand.stream()
                .collect(Collectors.groupingBy(Card::getSuit));

        for (Suit suit : cardsBySuit.keySet()) {
            List<Card> cardsInSuit = cardsBySuit.get(suit);
            boolean has3 = cardsInSuit.stream().anyMatch(card -> card.getNumber() == 3);
            boolean has2 = cardsInSuit.stream().anyMatch(card -> card.getNumber() == 2);
            boolean has1 = cardsInSuit.stream().anyMatch(card -> card.getNumber() == 1);
            if (has3 && has2 && has1) {
                bonusPoints += 9;
            }
        }

        Map<Integer, Long> cardCounts = hand.stream()
                .filter(card -> card.getNumber() == 3 || card.getNumber() == 2 || card.getNumber() == 1)
                .collect(Collectors.groupingBy(Card::getNumber, Collectors.counting()));

        for (Map.Entry<Integer, Long> entry : cardCounts.entrySet()) {
            if (entry.getValue() == 3) {
                bonusPoints += 9;
            }
            if (entry.getValue() == 4) {
                bonusPoints += 12;
            }
        }

        return bonusPoints;
    }


    public static Node evaluateGame(List<List<Card>> hands, int maxDepth) {
        Node root = new Node(hands);
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        int depth = 0;

        while (!queue.isEmpty() && depth < maxDepth) {
            Node node = queue.poll();

            List<Card> hand = node.getHands().get((node.getPreviousHandIndex() + 1) % 4);
            Suit dominantSuit = node.getDominantSuite();

            if (dominantSuit == null) {

            }

            depth++;
        }

        return root;
    }

    public static void main(String[] args) {
        TressetteGame game = new TressetteGame();

        for (List<Card> hand : game.getHands()) {
            System.out.println(hand.stream().sorted(Card.ORDER_COMPARATOR).toList());
            System.out.println("Bonus points: " + calculateBonusPoints(hand) / 3);
            System.out.println();
        }
    }

}