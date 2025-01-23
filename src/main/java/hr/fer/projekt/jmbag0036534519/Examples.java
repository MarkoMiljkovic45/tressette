package hr.fer.projekt.jmbag0036534519;

import java.util.List;
import java.util.function.Function;

import static hr.fer.projekt.jmbag0036534519.TressetteGame.*;
import static hr.fer.projekt.jmbag0036534519.Util.getMinimaxScore;
import static hr.fer.projekt.jmbag0036534519.Util.readMemoryUsage;

public class Examples {
    private static final long MIN_FREE_MEMORY = 300 * 1024 * 1024;

    public static void main(String[] args) {
        //basicExample(Util::readMinimaxPath);
        //basicExample(Util::getMinmaxPots);
        greedyExample(Util::getMinmaxPots);
    }

    public static void basicExample(Function<State, String> statePrinter) {
        Runtime runtime = Runtime.getRuntime();
        List<Card> deck = generateRandomDeck();
        List<List<Card>> hands = dealCards(deck);
        printHands(hands);

        State root = eval(hands, 0);
        System.out.println("Searching for optimal continuation...");
        for (int depth = 4; depth <= 16; depth += 1) {
            if (runtime.maxMemory() < MIN_FREE_MEMORY) {
                System.out.println("Low memory, aborting...");
                System.out.println("max depth reached: " + depth);
                return;
            }

            eval(root, depth);
            if (depth % 4 == 0) {
                System.out.println("memory: " + readMemoryUsage(runtime));
                System.out.println("depth: " + depth);
                System.out.println("minimax: " + getMinimaxScore(root));
                System.out.println(statePrinter.apply(root));
                System.out.println();
            }
        }
    }

    public static void greedyExample(Function<State, String> statePrinter) {
        Runtime runtime = Runtime.getRuntime();
        List<Card> deck = generateRandomDeck();
        List<List<Card>> hands = dealCards(deck);
        printHands(hands);

        State root = eval(hands, 0);
        State currRoot = root;
        System.out.println("Searching for continuation... (Greedy)");
        for (int i = 1; i <= 4; i++) {
            eval(currRoot, i * 12);

            System.out.println("memory: " + readMemoryUsage(runtime));
            System.out.println("depth: " + Integer.min(i * 12, 40));
            System.out.println("minimax: " + getMinimaxScore(root));
            System.out.println(statePrinter.apply(root));
            System.out.println();

            while (currRoot.getMinimaxSuccessor() != null) {
                currRoot = currRoot.getMinimaxSuccessor();
            }
        }
    }
}