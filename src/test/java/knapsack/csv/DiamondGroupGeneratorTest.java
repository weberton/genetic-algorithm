package knapsack.csv;

import knapsack.ItemsGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DiamondGroupGeneratorTest {

    private DiamondGroupGenerator diamondGroupGenerator;

    @BeforeEach
    void setup() {
        this.diamondGroupGenerator = new DiamondGroupGenerator();
    }

    @Test
    void generate_whenItemPerGoupIsGreaterThanPopulation_generatesItemsRandomly() {
        String fileName = "diamonds_test.csv";
        List<ItemsGroup> result = diamondGroupGenerator.generate(fileName, 5);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getFirst().getItems().size()).isEqualTo(3);
    }

    @Test
    void generate_whenItemPerGoupIsGreaterThanPopulation_generatesItemsRandomly1() {
        String fileName = "diamonds_test.csv";
        List<ItemsGroup> result = diamondGroupGenerator.generate(fileName, 2);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.getFirst().getItems().size()).isEqualTo(2);
        assertThat(result.getLast().getItems().size()).isEqualTo(1);
    }
}
