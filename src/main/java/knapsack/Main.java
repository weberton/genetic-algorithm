package knapsack;

public class Main {

    public static void main(String[] args) {
        Knapsack knapsack = new Knapsack(50);
        int populationSize = 500;
        int quantityOfItemsPerGroup = 10;

        Population population = new Population(knapsack, populationSize, quantityOfItemsPerGroup);

        while (!population.isKnapsackFull()) {
            population.calculateFitness();
            //TODO check strategy name
            population.generate();
        }

        System.out.printf("Solution found with %s generations. %n", population.getGenerations());
    }
}
