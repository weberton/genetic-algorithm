package knapsack;

import knapsack.csv.DiamondGroupGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Population {
    private final Knapsack knapsack;
    private final double mutationRate;
    private int populationSize;
    private List<ItemsGroup> population;
    private ItemsGroup itemMaxScore = null;
    private int generations;

    public Population(final Knapsack knapsack,
                      final int quantityOfItemPerGroup,
                      final double mutationRate,
                      final String file) {
        this.knapsack = knapsack;
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

    }

    private ItemsGroup selectParent() {
        Integer[] parentsIndex = Stream.generate(() -> new Random().ints(0, populationSize))
                .flatMap(IntStream::boxed)
                .distinct()
                .limit(2)
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

    public void generate() {
        System.out.printf("------Generating new population with genetic algorithm. Generation %d.------%n", generations);
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

    public void generateRandomPopulation() {
        System.out.printf("------Generating new population with random population strategy. Generation %d.------%n", generations);
        List<ItemsGroup> bestPopulation = new ArrayList<>();
        // copia e ordena a populacao atual
        List<ItemsGroup> sortedPopulation = new ArrayList<>(this.population);
        sortedPopulation.sort((o1, o2) -> Double.compare(o2.getScore(), o1.getScore()));
        // agora fazemos a mutacao em uma parte da populacao. A diferenca 'e que sera uma parte aleatoria, entre 10 e 90%
        int mutationSize = new Random().nextInt(sortedPopulation.size() / 2) + sortedPopulation.size() / 4;
        for (int i = 0; i < sortedPopulation.size(); i++) {
            ItemsGroup itemsGroup = sortedPopulation.get(i);
            if (i < sortedPopulation.size() / mutationSize) {
                itemsGroup.mutate(mutationRate);
            }
            bestPopulation.add(itemsGroup);
        }

        this.population = bestPopulation;
        generations++;
    }

    /**
     *  Vamos fazer uma ordenação para pegar os melhores e depois fazer mutacao em 50% dos piores
     */
    public void generateBestPopulation() {
        System.out.printf("------Generating new population with best population strategy. Generation %d.------%n", generations);
        List<ItemsGroup> bestPopulation = new ArrayList<>();
        // copia e ordena a populacao atual
        List<ItemsGroup> sortedPopulation = new ArrayList<>(this.population);
        sortedPopulation.sort((o1, o2) -> Double.compare(o2.getScore(), o1.getScore()));
        // agora fazemos a mutacao na metade inferior
        for (int i = 0; i < sortedPopulation.size(); i++) {
            ItemsGroup itemsGroup = sortedPopulation.get(i);
            if (i < sortedPopulation.size() / 2) {
                itemsGroup.mutate(mutationRate);
            }
            bestPopulation.add(itemsGroup);
        }

        this.population = bestPopulation;
        generations++;
    }
}
