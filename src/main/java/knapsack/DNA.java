package knapsack;

import java.util.Random;

public class DNA {
    private int weight;
    private int value;

    public DNA(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public void mutate() {
        Random random = new Random();
        int newWeight = Math.abs(this.weight >> random.nextInt(1, 5));
        int newValue = Math.abs(this.value >> random.nextInt(1, 5));

        this.weight = newWeight;
        this.value = newValue;
    }

    public static DNA generateRandom() {
        Random random = new Random();
        int weight = random.nextInt(1, 100);
        int value = random.nextInt(1, 1000);
        return new DNA(weight, value);
    }
}
