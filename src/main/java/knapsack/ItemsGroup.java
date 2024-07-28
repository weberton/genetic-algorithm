package knapsack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemsGroup {
    private final List<DNA> items;
    private final int[] usedItemsDNA;
    private int score;

    public ItemsGroup(final List<DNA> items) {
        this.items = items;
        this.usedItemsDNA = new int[items.size()];
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
                this.usedItemsDNA[solution.getUsedItems().get(i)] = 1;
            }
        }
    }

    public int getScore() {
        return score;
    }

    public int[] getUsedItemsDNA() {
        return usedItemsDNA;
    }

    public List<DNA> getUsedItems() {
        if (usedItemsDNA == null || usedItemsDNA.length == 0) {
            return new ArrayList<>();
        }

        List<DNA> pickedItems = new ArrayList<>();

        for (int i = 0; i < usedItemsDNA.length; i++) {
            if (usedItemsDNA[i] != 0) {
                pickedItems.add(items.get(i));
            }
        }

        return pickedItems;
    }

    public ItemsGroup crossover(ItemsGroup mother) {
        List<DNA> fatherUsedItems = getItems();
        List<DNA> motherUsedItems = mother.getItems();
        List<DNA> children = new ArrayList<>();

        for (int i = 0; i < fatherUsedItems.size(); i++) {
            DNA fatherGene = fatherUsedItems.get(i);
            DNA motherGene = motherUsedItems.get(i);
            children.add(generateChild(fatherGene, motherGene));
        }

        return new ItemsGroup(children);
    }

    private DNA generateChild(DNA fatherGene, DNA motherGene) {
        Random random = new Random();
        boolean useFatherGene = random.nextBoolean();
    
        int childWeight = useFatherGene ? fatherGene.getWeight() : motherGene.getWeight();
        int childValue = useFatherGene ? fatherGene.getValue() : motherGene.getValue();
    
        return new DNA(childWeight, childValue);
    }

    public void mutate(double rate) {
        Random random = new Random();
        for (int i = 0; i < items.size(); i++) {
            if (random.nextDouble() < rate) {
                items.get(i).mutate();
            }
        }
    }
}
