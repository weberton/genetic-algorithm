package knapsack;

import java.util.ArrayList;
import java.util.List;

public class ItemsGroupGenerator {

    private final int itemsQuantity;
    private final DNAItemGenerator dnaItemGenerator;

    public ItemsGroupGenerator(final int quantityOfItemPerGroup,
                               final int minWeight,
                               final int maxWeight,
                               final int minValue,
                               final int maxValue) {
        dnaItemGenerator = new DNAItemGenerator(minWeight, maxWeight, minValue, maxValue);
        this.itemsQuantity = quantityOfItemPerGroup;
    }

    public ItemsGroup generateItemsGroup() {
        List<DNA> items = new ArrayList<>(itemsQuantity);
        for (int i = 0; i < itemsQuantity; i++) {
            items.add(dnaItemGenerator.generateRandomItem());
        }

        return new ItemsGroup(items);
    }
}
