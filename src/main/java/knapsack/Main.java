package knapsack;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Main {

    public static void main(String[] args) {
        Knapsack knapsack = new Knapsack(5000); //alteração do peso da mochila para miligrama
        int populationSize = 50;
        int quantityOfItemsPerGroup = 50;
        double mutationRate = 0.05;

        // Inicializar a primeira população que será usada com algoritmo genético
        Population population = new Population(knapsack, populationSize, quantityOfItemsPerGroup, mutationRate, "diamonds.csv");

        // Inicializar a segunda população que será usada sem algoritmo genético, com geracao totalmente aleatória das novas populações
        Population populationRandomPopulation = new Population(knapsack, populationSize, quantityOfItemsPerGroup, mutationRate, "diamonds.csv");

        // Inicializar a terceira população que será usada sem algoritmo genético, com estratégia de força bruta
        Population populationBestSelection = new Population(knapsack, populationSize, quantityOfItemsPerGroup, mutationRate, "diamonds.csv");

        // Série para o método com algoritmo genético
        XYSeries series = new XYSeries("Genetic Algorithm");
        // Série para o método sem algoritmo genético
        XYSeries seriesRandomPopulation = new XYSeries("Random Population");
        // Série para o método sem algoritmo genético e com um valor de mutação diferente
        XYSeries seriesBestPopulation = new XYSeries("Best Population Selection");

        XYSeriesCollection dataset = new XYSeriesCollection(series);
        dataset.addSeries(seriesRandomPopulation);
        dataset.addSeries(seriesBestPopulation);

        JFrame frame = new JFrame("Knapsack Score Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 650));

        int count = 0;

        while ( count < 1000) { //!population.isKnapsackFull()
            // Calcula o fitness da população com algoritmo genético
            population.calculateFitness();
            //TODO check strategy names
            population.generate();
            int generation = population.getGenerations();
            double score = population.getItemMaxScore().getScore();
            System.out.println("------Score Genetic Algorithm: " + score);
            System.out.println("-");
            series.add(generation, score);

            // Calcula o fitness da população sem algoritmo genético e com geracao totalmente aleatória das novas populações
            populationRandomPopulation.calculateFitness();
            // Gera uma nova população sem algoritmo genético
            populationRandomPopulation.generateRandomPopulation();
            double scoreRandomPopulation = populationRandomPopulation.getItemMaxScore().getScore();
            System.out.println("------Score Random Population: " + scoreRandomPopulation);
            System.out.println("-");
            seriesRandomPopulation.add(generation, scoreRandomPopulation);

            // Calcula o fitness da população sem algoritmo genético e com um valor de mutação diferente
            populationBestSelection.calculateFitness();
            // Gera uma nova população sem algoritmo genético e com um valor de mutação diferente
            populationBestSelection.generateBestPopulation();
            double scoreBestPopulation = populationBestSelection.getItemMaxScore().getScore();
            System.out.println("------Score Best Population: " + scoreBestPopulation);
            System.out.println("-");
            seriesBestPopulation.add(generation, scoreBestPopulation);

            JFreeChart chart = ChartFactory.createXYLineChart(
                    "Knapsack Score Chart",
                    "Generation",
                    "Score",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            TextTitle subtitle = new TextTitle();
            subtitle.setText("Population size %d. Items per Group %d".formatted(population.getPopulationSize(), quantityOfItemsPerGroup));
            chart.addSubtitle(subtitle);

            ChartPanel chartPanel = new ChartPanel(chart);
            frame.getContentPane().add(chartPanel, BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);

            count++;

        }


        System.out.printf("Solution found with %s generations. %n", population.getGenerations());
    }
}
