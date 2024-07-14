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
        int newWeight = this.weight >> random.nextInt(1, 5);
        int newValue = this.value >> random.nextInt(1, 5);

        this.weight = newWeight < 0 ? newWeight * -1 : newWeight;
        this.value = newValue < 0 ? newValue * -1 : newValue;
    }
}
