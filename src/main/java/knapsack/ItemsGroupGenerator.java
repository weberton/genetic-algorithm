package knapsack;

import java.util.ArrayList;
import java.util.List;

public class ItemsGroupGenerator {

    private final int quantityOfItemPerGroup;
    private final DNAItemGenerator dnaItemGenerator;

    public ItemsGroupGenerator(final int quantityOfItemPerGroup,
                               final int minWeight,
                               final int maxWeight,
                               final int minValue,
                               final int maxValue) {
        dnaItemGenerator = new DNAItemGenerator(minWeight, maxWeight, minValue, maxValue);
        this.quantityOfItemPerGroup = quantityOfItemPerGroup;
    }

    public ItemsGroup generateItemsGroup() {
        List<DNA> items = new ArrayList<>(quantityOfItemPerGroup);
        for (int i = 0; i < quantityOfItemPerGroup; i++) {
            items.add(dnaItemGenerator.generateRandomItem());
        }

        return new ItemsGroup(items);
    }

    public int getQuantityOfItemPerGroup() {
        return quantityOfItemPerGroup;
    }
}
