package knapsack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import knapsack.csv.DiamondGroupGenerator;

public class Population {
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 1000;//Integer.MAX_VALUE;

    private final Knapsack knapsack;
    private final double mutationRate;
    private int populationSize;
    private ItemsGroupGenerator itemsGroupGenerator;
    private List<ItemsGroup> population;
    private ItemsGroup itemMaxScore = null;
    private int generations;

    public Population(final Knapsack knapsack,
                      final int populationSize,
                      final int quantityOfItemPerGroup,
                      final double mutationRate) {
        this.knapsack = knapsack;
        this.populationSize = populationSize;
        this.population = new ArrayList<>(populationSize);
        this.mutationRate = mutationRate;
        int minWeight = 1;
        int maxWeight = knapsack.getCapacity() * 2;
        this.itemsGroupGenerator = new ItemsGroupGenerator(quantityOfItemPerGroup, minWeight, maxWeight, MIN_VALUE, MAX_VALUE);
        this.generations = 0;

        generatePopulation();
        // calculateFitness();
    }

    public Population(final Knapsack knapsack,
                      final int populationSize,
                      final int quantityOfItemPerGroup,
                      final double mutationRate,
                      final String file) {
        this.knapsack = knapsack;
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.population = new ArrayList<>();
        generateGroups(file, quantityOfItemPerGroup);
    }

    public void calculateFitness() {
        double maxScore = itemMaxScore == null ? 0 : itemMaxScore.getScore();
        for (ItemsGroup itemsGroup : population) {
            itemsGroup.calculateScore(knapsack);
            if (itemsGroup.getScore() > maxScore) {
                maxScore = itemsGroup.getScore();
                itemMaxScore = itemsGroup;
            }
        }

        System.out.printf("Items with max value: %d%n", itemMaxScore.getScore());
        System.out.printf("Items weight: %s%n", itemMaxScore.getUsedItems().stream()
                                                            .map(dna -> String.valueOf(dna.getWeight()))
                                                            .collect(Collectors.joining(",")));
        System.out.printf("Items values: %s%n", itemMaxScore.getUsedItems().stream()
                                                            .map(dna -> String.valueOf(dna.getValue()))
                                                            .collect(Collectors.joining(",")));
        System.out.printf(
                "Unused space: %d%n",
                knapsack.getCapacity() - itemMaxScore.getUsedItems().stream().map(DNA::getWeight).reduce(0, Integer::sum));
    }

    public void generate() {
        System.out.printf("------Generating new population. Generations number %d ----%n", getGenerations());
        List<ItemsGroup> newPopulation = new ArrayList<>(this.population.size());
        for (int i = 0; i < population.size(); i++) {
            ItemsGroup father = selectParent();
            ItemsGroup mother = selectParent();
            ItemsGroup child = father.crossover(mother);
            child.mutate(mutationRate);
            newPopulation.add(child);
        }
        this.population = newPopulation;
        generations++;
    }

    private ItemsGroup selectParent() {
        Integer[] parentsIndex = Stream.generate(() -> new Random().ints(0, populationSize))
                                       .flatMap(IntStream::boxed)
                                       .distinct()
                                       .limit(2) // whatever limit you might need
                                       .toArray(Integer[]::new);
        int fatherCandidateIndex1 = parentsIndex[0];
        int fatherCandidateIndex2 = parentsIndex[1];

        ItemsGroup itemGroup1 = population.get(fatherCandidateIndex1);
        ItemsGroup itemGroup2 = population.get(fatherCandidateIndex2);

        if (itemGroup1.getScore() > itemGroup2.getScore()) {
            return itemGroup1;
        } else {
            return itemGroup2;
        }
    }

    public boolean isKnapsackFull() {
        return Objects.nonNull(itemMaxScore) && getKnapsackEmptySpace() == 0;
    }

    private double getKnapsackEmptySpace() {
        return knapsack.getCapacity() - itemMaxScore.getUsedItems().stream().map(DNA::getWeight).reduce(0, Integer::sum);
    }


    private void generatePopulation() {
        System.out.printf("Generating population of %d with %d item per group. %n", populationSize,
                          itemsGroupGenerator.getQuantityOfItemPerGroup());
        for (int i = 0; i < populationSize; i++) {
            population.add(itemsGroupGenerator.generateItemsGroup());
        }
    }

    private void generateGroups(String file, int quantityOfItemPerGroup) {
        DiamondGroupGenerator diamondGroupGenerator = new DiamondGroupGenerator();
        this.population = diamondGroupGenerator.generate(file, quantityOfItemPerGroup);
        this.populationSize = this.population.size();
    }


    public int getGenerations() {
        return generations;
    }

    public ItemsGroup getItemMaxScore() {
        return itemMaxScore;
    }

    public int getPopulationSize() {
        return populationSize;
    }
}
