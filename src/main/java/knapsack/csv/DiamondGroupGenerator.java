package knapsack.csv;

import java.util.ArrayList;
import java.util.List;

import knapsack.DNA;
import knapsack.ItemsGroup;

public class DiamondGroupGenerator {

    public List<ItemsGroup> generate(String file, int quantityOfItemPerGroup) {
        List<ItemsGroup> population = new ArrayList<>();
        List<Diamond> diamonds = readCsv(file);


        int itemsGroup = 0;
        List<DNA> dnaList = new ArrayList<>();

        for (Diamond diamond : diamonds) {
            if (itemsGroup == quantityOfItemPerGroup) {
                population.add(new ItemsGroup(dnaList));
                dnaList = new ArrayList<>();
                itemsGroup = 0;
            }
            itemsGroup++;

            int weight = Math.max(1, Math.toIntExact(Math.round(diamond.getWeight())));
            int price = Math.max(1, Math.toIntExact(Math.round(diamond.getPrice())));

            DNA newDna = new DNA(weight, price);
            dnaList.add(newDna);
        }

        verifyMissingDnaItems(dnaList, population, quantityOfItemPerGroup);

        return population;
    }

    private void verifyMissingDnaItems(List<DNA> dnaList, List<ItemsGroup> population, int quantityOfItemPerGroup) {
        if (dnaList.isEmpty() || dnaList.size() == quantityOfItemPerGroup) {
            return;
        }

        int remainingItems = quantityOfItemPerGroup - dnaList.size();

        for (int i = 0; i < remainingItems; i++) {
            dnaList.add(DNA.generateRandom());
        }

        population.add(new ItemsGroup(dnaList));
    }


    private List<Diamond> readCsv(String file) {
        DiamondCsvReader diamondCsvReader = new DiamondCsvReader();
        return diamondCsvReader.read(file);
    }
}