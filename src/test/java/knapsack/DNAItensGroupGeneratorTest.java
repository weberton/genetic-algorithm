package knapsack;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DNAItensGroupGeneratorTest {

    @Test
    void generateRandomItem() {
        int minWeight = 1;
        int maxWeight = 10;
        int minValue = 1;
        int maxValue = 50;
        IntStream.range(0, 1000).forEach(i -> {
            DNA dna = new DNAItemGenerator(minWeight, maxWeight, minValue, maxValue).generateRandomItem();
            assertThat(dna.getValue()).isGreaterThanOrEqualTo(minValue);
            assertThat(dna.getValue()).isLessThan(maxValue);
            assertThat(dna.getWeight()).isGreaterThanOrEqualTo(minValue);
            assertThat(dna.getWeight()).isLessThan(maxValue);

        });

    }
}
