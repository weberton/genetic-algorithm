package knapsack.csv;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DiamondCsvReader {
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    public List<Diamond> read(String fileName) {

        Path path = getFilePath(fileName);
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(path.toFile(), CHARSET))
                .withSkipLines(1)
                .withCSVParser(parser)
                .build()) {
            CsvToBean<Diamond> csvToBean = new CsvToBeanBuilder<Diamond>(reader)
                    .withType(Diamond.class)
                    .withSkipLines(1)
                    .build();

            return csvToBean.parse();
        } catch (IOException e) {
            throw new IllegalStateException("Error reading the file", e);
        }

    }

    private Path getFilePath(String fileName) {
        try {
            return Paths.get(getClass().getClassLoader()
                                       .getResource(fileName).toURI());

        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("File not found.", e);
        }
    }

    public static void main(String[] args) {
        DiamondCsvReader diamondCsvReader = new DiamondCsvReader();
        List<Diamond> result = diamondCsvReader.read("diamonds.csv");
        System.out.println(result);
    }
}
