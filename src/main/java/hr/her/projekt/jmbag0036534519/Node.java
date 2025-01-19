package hr.her.projekt.jmbag0036534519;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {

    final private Node parent;
    final private Map<Card, Node> children;
    final private int score;
    final private List<List<Card>> hands;
    final private int previousHandIndex;
    final private Card previousCard;
    final private Suit dominantSuite;

    private Node(Node parent, List<List<Card>> hands, int score, Map<Card, Node> children, int previousHandIndex, Card previousCard, Suit dominantSuite) {
        this.parent = parent;
        this.hands = hands;
        this.score = score;
        this.children = children;
        this.previousHandIndex = previousHandIndex;
        this.previousCard = previousCard;
        this.dominantSuite = dominantSuite;
    }

    public Node(Node parent, List<List<Card>> hands, int score, int previousHandIndex, Card previousCard, Suit dominantSuite) {
        this(parent, hands, score, new HashMap<>(6), previousHandIndex,previousCard, dominantSuite);
    }

    public Node(List<List<Card>> hands) {
        this(null, hands, 0, new HashMap<>(10), 3, null, null);
    }

    public Node getParent() {
        return parent;
    }

    public Map<Card, Node> getChildren() {
        return children;
    }

    public int getScore() {
        return score;
    }

    public List<List<Card>> getHands() {
        return hands;
    }

    public Card getPreviousCard() {
        return previousCard;
    }

    public Suit getDominantSuite() {
        return dominantSuite;
    }

    public int getPreviousHandIndex() {
        return previousHandIndex;
    }
}
