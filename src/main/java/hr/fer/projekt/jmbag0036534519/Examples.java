package hr.fer.projekt.jmbag0036534519;

import java.util.List;
import java.util.function.Function;

import static hr.fer.projekt.jmbag0036534519.TressetteGame.*;
import static hr.fer.projekt.jmbag0036534519.Util.getMinimaxScore;
import static hr.fer.projekt.jmbag0036534519.Util.readMemoryUsage;

public class Examples {

    public static void main(String[] args) {
        System.gc();
        //basicExample(Util::getMinmaxPots, 40);
        //deepExample(Util::getMinmaxPots, 28);
        //memoryExample(Util::getMinmaxPots);
        greedyExample(Util::getMinmaxPots, 20);
    }

    public static void basicExample(Function<State, String> statePrinter, int maxDepth) {
        Runtime runtime = Runtime.getRuntime();
        List<Card> deck = generateRandomDeck();
        List<List<Card>> hands = dealCards(deck);
        printHands(hands);

        State root = eval(hands, 0);
        System.out.println("Searching for optimal continuation...");
        for (int depth = 4; depth <= maxDepth; depth += 4) {
            eval(root, depth);
            System.out.println("memory: " + readMemoryUsage(runtime));
            System.out.println("depth: " + depth);
            System.out.println("minimax: " + getMinimaxScore(root));
            System.out.println(statePrinter.apply(root));
            System.out.println();
        }
    }

    public static void deepExample(Function<State, String> statePrinter, int maxDepth) {
        Runtime runtime = Runtime.getRuntime();
        List<Card> deck = generateRandomDeck();
        List<List<Card>> hands = dealCards(deck);
        printHands(hands);

        State root = eval(hands, 0);
        System.out.println("Searching for optimal continuation...");
        eval(root, maxDepth);
        System.out.println("memory: " + readMemoryUsage(runtime));
        System.out.println("depth: " + maxDepth);
        System.out.println("minimax: " + getMinimaxScore(root));
        System.out.println(statePrinter.apply(root));
    }

    public static void memoryExample(Function<State, String> statePrinter) {
        Runtime runtime = Runtime.getRuntime();
        List<Card> deck = generateRandomDeck();
        List<List<Card>> hands = dealCards(deck);
        printHands(hands);

        State root = eval(hands, 0);
        System.out.println("Searching for optimal continuation...");
        for (int depth = 4; depth <= 40; depth += 1) {
            if (runtime.maxMemory() < runtime.totalMemory()) {
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

    public static void greedyExample(Function<State, String> statePrinter, int jump) {
        Runtime runtime = Runtime.getRuntime();
        List<Card> deck = generateRandomDeck();
        List<List<Card>> hands = dealCards(deck);
        printHands(hands);

        State root = eval(hands, 0);
        State currRoot = root;
        System.out.println("Searching for continuation... (Greedy)");

        int maxJumps = (40 % jump == 0)
                ? 40 / jump
                : 40 / jump + 1;

        for (int i = 1; i <= maxJumps; i++) {
            int depth = i * jump;
            eval(currRoot, depth);

            System.out.println("memory: " + readMemoryUsage(runtime));
            System.out.println("depth: " + depth);
            System.out.println("minimax: " + getMinimaxScore(root));
            System.out.println(statePrinter.apply(root));
            System.out.println();

            while (currRoot.getMinimaxSuccessor() != null) {
                currRoot = currRoot.getMinimaxSuccessor();
            }
        }
    }
}