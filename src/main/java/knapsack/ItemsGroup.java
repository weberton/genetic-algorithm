package knapsack;

import java.util.List;

public class ItemsGroup {
    private final List<DNA> items;
    private final int[] usedItems;
    private int score;

    public ItemsGroup(final List<DNA> items) {
        this.items = items;
        this.usedItems = new int[items.size()];
    }

    public List<DNA> getItems() {
        return items;
    }

    public void calculateScore(final Knapsack knapsack) {
        KnapsackSolutionCalculator knapsackSolutionCalculator = new KnapsackSolutionCalculator();
        KnapsackSolution solution = knapsackSolutionCalculator.calculate(items, knapsack);
        if (solution.getScore() > 0) {
            this.score = solution.getScore();
            for (int i = 0; i < solution.getUsedItems().size(); i++) {
                this.usedItems[solution.getUsedItems().get(i)] = 1;
            }
        }
    }

    public int getScore() {
        return score;
    }

    public int[] getUsedItems() {
        return usedItems;
    }
}
