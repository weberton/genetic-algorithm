package knapsack;

import java.util.List;

public class KnapsackSolution {
    private final int score;
    private final List<Integer> usedItems;

    public KnapsackSolution(int score, List<Integer> usedItems) {
        this.score = score;
        this.usedItems = usedItems;
    }

    public int getScore() {
        return score;
    }

    public List<Integer> getUsedItems() {
        return usedItems;
    }
}
