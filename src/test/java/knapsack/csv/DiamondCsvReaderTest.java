package knapsack.csv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DiamondCsvReaderTest {

    private DiamondCsvReader diamondCsvReader;

    @BeforeEach
    void setup() {
        this.diamondCsvReader = new DiamondCsvReader();
    }

    @Test
    void read() {
        String fileName = "diamonds_test.csv";
        Diamond diamond1 = new Diamond(1, 0.23, 326);
        Diamond diamond2 = new Diamond(2, 0.21, 326);
        Diamond diamond3 = new Diamond(3, 0.23, 327);

        List<Diamond> result = this.diamondCsvReader.read(fileName);

        assertThat(result.size()).isEqualTo(3);
        assertThat(result.getFirst()).isEqualTo(diamond1);
        assertThat(result.get(1)).isEqualTo(diamond2);
        assertThat(result.getLast()).isEqualTo(diamond3);
    }
}
