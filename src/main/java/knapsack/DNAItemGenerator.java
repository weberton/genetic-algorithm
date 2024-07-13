package knapsack;

import java.util.Random;

public class DNAItemGenerator {

    private final int minWeight;
    private final int maxWeight;
    private final int minValue;
    private final int maxValue;

    public DNAItemGenerator(final int minWeight,
                            final int maxWeight,
                            final int minValue,
                            final int maxValue) {

        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }


    public DNA generateRandomItem() {
        Random random = new Random();
        int weight = random.nextInt(minWeight, maxWeight);
        int value = random.nextInt(minValue, maxValue);
        return new DNA(weight, value);
    }
}
