package hr.fer.projekt.jmbag0036534519;

import java.util.List;

public class Util {
    private static final String ARROW = " -> ";
    
    public static String readMinimaxPath(State root) {
        StringBuilder builder = new StringBuilder();
        State currState = root;

        while(currState != null) {
            String card = currState.getPot().isEmpty() ? "|" : currState.getPot().getLast().toString();
            builder.append(card).append(ARROW);
            currState = currState.getMinimaxSuccessor();
        }

        if (builder.length() > ARROW.length()) {
            int start = builder.length() - ARROW.length();
            int end = builder.length();
            builder.delete(start, end);
        }

        return builder.toString();
    }

    public static int getMinimaxScore(State root) {
        State currState = root;
        while(currState.getMinimaxSuccessor() != null) {
            currState = currState.getMinimaxSuccessor();
        }
        return currState.getScore();
    }

    public static String getMinmaxPots(State root) {
        StringBuilder builder = new StringBuilder();

        State currState = root;

        while(currState != null) {
            List<Card> pot = currState.getPot();
            if (pot.size() == 4) {
                builder.append(pot).append("\n");
            }
            currState = currState.getMinimaxSuccessor();
        }

        return builder.toString();
    }

    public static String readMemoryUsage(Runtime runtime) {
        return runtime.totalMemory() / (1024 * 1024) +
                " MB / " +
                runtime.maxMemory() / (1024 * 1024) +
                " MB";
    }
}
