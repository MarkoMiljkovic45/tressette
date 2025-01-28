package hr.fer.projekt.jmbag0036534519;

import java.util.ArrayList;
import java.util.List;

public class State {
    private static final int FIRST_PLAYER = 0;
    private static final int LAST_PLAYER = 3;

    private final int depth;
    private final int score;
    private boolean isMaximizer;
    private State minimaxSuccessor;
    private final List<List<Card>> hands;
    private final List<Card> pot;
    private List<State> successors;

    public State(List<List<Card>> hands, List<Card> pot, int score, boolean isMaximizer, int depth) {
        this.hands = hands;
        this.pot = pot;
        this.score = score;
        this.depth = depth;
        this.isMaximizer = isMaximizer;
        this.minimaxSuccessor = null;
        this.successors = null;
    }

    public boolean isTerminal(int maxDepth) {
        if (depth >= maxDepth) {
            return true;
        }
        return hands.stream().allMatch(List::isEmpty);
    }

    public int getScore() {
        return score;
    }
    
    public List<Card> getPot() {
        return pot;
    }

    public boolean isMaximizer() {
        return isMaximizer;
    }

    public boolean isMinimizer() {
        return !isMaximizer;
    }

    public void setMaximizer(boolean maximizer) {
        isMaximizer = maximizer;
    }
    
    public State getMinimaxSuccessor() {
        return minimaxSuccessor;
    }

    public void setMinimaxSuccessor(State minimaxSuccessor) {
        this.minimaxSuccessor = minimaxSuccessor;
    }

    public List<State> getSuccessors() {
        if (successors == null || successors.isEmpty()) {
            calculateSuccessors();
        }
        return successors;
    }

    private void calculateSuccessors() {
        successors = new ArrayList<>(10);
        int currPlayer = pot.size() % 4;
        int newDepth = depth + 1;

        for (Card playedCard : getPlayableCards()) {
            int newScore = score;
            boolean isNextMaximizer = !isMaximizer;
            List<List<Card>> newHands = updateHands(hands, currPlayer, playedCard);

            List<Card> newPot = (currPlayer == FIRST_PLAYER)
                    ? new ArrayList<>(4)
                    : new ArrayList<>(pot);
            newPot.add(playedCard);

            if (currPlayer == LAST_PLAYER) {
                int potTakerIndex = getPotTakerIndex(newPot);
                int potVal = newPot.stream().mapToInt(Card::getValue).sum();

                isNextMaximizer = (potTakerIndex % 2 == 0) != isMaximizer;
                newScore += isNextMaximizer ? potVal : -potVal;

                List<List<Card>> part1 = new ArrayList<>(newHands.subList(potTakerIndex, newHands.size()));
                List<List<Card>> part2 = new ArrayList<>(newHands.subList(0, potTakerIndex));

                newHands.clear();
                newHands.addAll(part1);
                newHands.addAll(part2);
            }

            successors.add(new State(newHands, newPot, newScore, isNextMaximizer, newDepth));
        }
    }

    public static int getPotTakerIndex(List<Card> pot) {
        int potSize = pot.size();

        if (potSize == 0) {
            return -1;
        }

        Suit dominantSuit = pot.getFirst().getSuit();
        int strongestCardPower = -1;
        int strongestCardIndex = 0;
        for (int i = 0; i < potSize; i++) {
            Card card = pot.get(i);
            int cardPower = card.getSuit().equals(dominantSuit) ? card.getPower() : -1;
            if (cardPower > strongestCardPower) {
                strongestCardPower = cardPower;
                strongestCardIndex = i;
            }
        }

        return strongestCardIndex;
    }

    private List<Card> getPlayableCards() {
        int curr_player = pot.size() % 4;
        List<Card> hand = hands.get(curr_player);
        if (curr_player == FIRST_PLAYER) {
            return hand;
        }
        Suit dominantSuit = pot.getFirst().getSuit();
        List<Card> cardsInSuit = hand.stream()
                .filter(card -> card.getSuit() == dominantSuit)
                .toList();
        return cardsInSuit.isEmpty() ? hand : cardsInSuit;
    }

    private static List<List<Card>> updateHands(List<List<Card>> hands, int playerIndex, Card playedCard) {
        List<List<Card>> copiedHands = new ArrayList<>(hands);
        List<Card> updatedHand = new ArrayList<>(copiedHands.get(playerIndex));
        updatedHand.remove(playedCard);
        copiedHands.set(playerIndex, updatedHand);
        return copiedHands;
    }
}
