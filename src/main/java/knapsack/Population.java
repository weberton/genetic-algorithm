package knapsack;

import java.util.ArrayList;
import java.util.List;

public class Population {
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 100;

    private final Knapsack knapsack;
    private final List<ItemsGroup> population;
    private final int populationSize;
    private final ItemsGroupGenerator itemsGroupGenerator;

    public Population(final Knapsack knapsack,
                      final int populationSize,
                      final int quantityOfItemPerGroup) {
        this.knapsack = knapsack;
        this.populationSize = populationSize;
        this.population = new ArrayList<>(populationSize);
        int minWeight = knapsack.getCapacity() * 2;
        int maxWeight = knapsack.getCapacity() * 2;
        this.itemsGroupGenerator = new ItemsGroupGenerator(quantityOfItemPerGroup, minWeight, maxWeight, MIN_VALUE, MAX_VALUE);

        generatePopulation();
    }

    public void calculateFitness() {
        for (ItemsGroup itemsGroup : population) {
            itemsGroup.calculateScore(knapsack);
        }
    }

    private void generatePopulation() {
        for (int i = 0; i < populationSize; i++) {
            population.add(itemsGroupGenerator.generateItemsGroup());
        }
    }
}
