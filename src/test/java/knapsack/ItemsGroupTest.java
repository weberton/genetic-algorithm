package knapsack;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemsGroupTest {

    @Test
    void calculateScoreTest() {
        Knapsack knapsack = new Knapsack(10);
        DNA item1 = new DNA(2, 1);
        DNA item2 = new DNA(3, 4);
        DNA item3 = new DNA(6, 5);
        DNA item4 = new DNA(7, 6);
        List<DNA> items = List.of(item1, item2, item3, item4);

        ItemsGroup itemsGroup = new ItemsGroup(items);
        itemsGroup.calculateScore(knapsack);

        assertThat(itemsGroup.getScore()).isEqualTo(10);
        assertThat(itemsGroup.getUsedItemsDNA()).containsExactlyInAnyOrder(item2, item4);
    }
}
