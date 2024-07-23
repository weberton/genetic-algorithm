package knapsack;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class Comparison {
    public static void main(String[] args) {
        // Inicializar a mochila com capacidade de 800
        Knapsack knapsack = new Knapsack(800);
        int populationSize = 500;
        int quantityOfItemsPerGroup = 30;
        double mutationRate = 0.01;

        // Inicializar a primeira população que será usada com algoritmo genético
        Population populationGeneticAlgorithm = new Population(knapsack, populationSize, quantityOfItemsPerGroup, mutationRate);

        // Inicializar a segunda população que será usada sem algoritmo genético, com geracao totalmente aleatória das novas populações
        Population populationRandomPopulation = new Population(knapsack, populationSize, quantityOfItemsPerGroup, mutationRate);

        // Inicializar a terceira população que será usada sem algoritmo genético, com estratégia de força bruta
        Population populationBruteForce = new Population(knapsack, populationSize, quantityOfItemsPerGroup, 0.1);

        // Série para o método com algoritmo genético
        XYSeries seriesGeneticAlgorithm = new XYSeries("Genetic Algorithm");
        // Série para o método sem algoritmo genético
        XYSeries seriesRandomPopulation = new XYSeries("Random Population");
        // Série para o método sem algoritmo genético e com um valor de mutação diferente
        XYSeries seriesBruteForce = new XYSeries("Brute Force");

        XYSeriesCollection dataset = new XYSeriesCollection(seriesGeneticAlgorithm);
        dataset.addSeries(seriesRandomPopulation);
        dataset.addSeries(seriesBruteForce);

        JFrame frame = new JFrame("Knapsack Score Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 650));

        while (!populationGeneticAlgorithm.isKnapsackFull() && !populationRandomPopulation.isKnapsackFull() && !populationBruteForce.isKnapsackFull()) {
            // Calcula o fitness da população com algoritmo genético
            populationGeneticAlgorithm.calculateFitness();
            populationGeneticAlgorithm.generate();
            int generation = populationGeneticAlgorithm.getGenerations();
            int scoreGeneticAlgorithm = populationGeneticAlgorithm.getItemMaxScore().getScore();

            // Calcula o fitness da população sem algoritmo genético e com geracao totalmente aleatória das novas populações
            populationRandomPopulation.calculateFitness();
            // Gera uma nova população sem algoritmo genético
            populationRandomPopulation.generateRandomPopulation();
            int scoreRandomPopulation = populationRandomPopulation.getItemMaxScore().getScore();

            // Calcula o fitness da população sem algoritmo genético e com um valor de mutação diferente
            populationBruteForce.calculateFitness();
            // Gera uma nova população sem algoritmo genético e com um valor de mutação diferente
            populationBruteForce.generateBruteForcePopulation();
            int scorenBruteForce = populationBruteForce.getItemMaxScore().getScore();

            // Adiciona os valores de geração e score para cada método
            seriesGeneticAlgorithm.add(generation, scoreGeneticAlgorithm);
            seriesRandomPopulation .add(generation, scoreRandomPopulation);
            seriesBruteForce.add(generation, scorenBruteForce);

            // Cria um unico gráfico com os valores de geração e score para cada método
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

            // Adiciona um subtitulo ao gráfico
            TextTitle subtitle = new TextTitle();
            subtitle.setText("Population size %d".formatted(populationSize));
            chart.addSubtitle(subtitle);

            // Adiciona o gráfico ao frame
            ChartPanel chartPanel = new ChartPanel(chart);
            frame.getContentPane().add(chartPanel, BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);

        }


        // Imprime a quantidade de gerações necessárias para encontrar a solução de acordo com cada método
        if(populationGeneticAlgorithm.isKnapsackFull())
            System.out.printf("Solution found with %s generations with genetic algorithm. %n", populationGeneticAlgorithm.getGenerations());
        if(populationRandomPopulation.isKnapsackFull())
            System.out.printf("Solution found with %s generations with random population. %n", populationRandomPopulation.getGenerations());
        if(populationBruteForce.isKnapsackFull())
            System.out.printf("Solution found with %s generations with brute force. %n", populationBruteForce.getGenerations());

    }
}
