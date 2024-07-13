package knapsack;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemsGroupGeneratorTest {

    @Test
    void generateItemsGroupTest() {
        int quantityOfItemPerGroup = 4;
        int minWeight = 1;
        int maxWeight = 10;
        int minValue = 10;
        int maxValue = 100;

        IntStream.range(0, 1000).forEach(i -> {
            ItemsGroup itemGroup = new ItemsGroupGenerator(quantityOfItemPerGroup, minWeight, maxWeight, minValue,
                                                           maxValue).generateItemsGroup();
            assertThat(itemGroup.getItems().size()).isEqualTo(4);

            itemGroup.getItems().forEach(item -> {
                assertThat(item.getWeight()).isGreaterThanOrEqualTo(minWeight);
                assertThat(item.getWeight()).isLessThan(maxWeight);
                assertThat(item.getValue()).isGreaterThanOrEqualTo(minValue);
                assertThat(item.getValue()).isLessThan(maxValue);
            });
        });

    }
}
