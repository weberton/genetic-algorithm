package knapsack;

import java.util.ArrayList;
import java.util.List;

public class KnapsackSolutionCalculator {

    public KnapsackSolution calculate(List<DNA> items, Knapsack knapsack) {
        int[][] matrix = new int[items.size() + 1][knapsack.getCapacity() + 1];

        for (int line = 1; line < matrix.length; line++) {
            DNA currentItem = items.get(line - 1);

            for (int currentCapacity = 0; currentCapacity < matrix[line].length; currentCapacity++) {
                if (currentCapacity < currentItem.getWeight()) {
                    matrix[line][currentCapacity] = matrix[line - 1][currentCapacity];
                    continue;
                }

                int remainingCapacity = currentCapacity - currentItem.getWeight();
                int valueSofar = matrix[line - 1][currentCapacity];
                int newItemValue = currentItem.getValue() + matrix[line - 1][remainingCapacity];
                matrix[line][currentCapacity] = Math.max(valueSofar, newItemValue);
            }
        }
        //System.out.println(Arrays.deepToString(matrix));

        //checking which items were used
        int line = matrix.length - 1;
        int col = matrix[matrix.length - 1].length - 1;
        int currentValue = matrix[line][col];

        int score = currentValue;
        List<Integer> usedItems = new ArrayList<>();

        while (currentValue > 0) {
            if (currentValue != matrix[line - 1][col]) {
                usedItems.add(line - 1);
                col = col - items.get(line - 1).getWeight();

            }
            line--;
            currentValue = matrix[line][col];
        }

        return new KnapsackSolution(score, usedItems);
    }

}
