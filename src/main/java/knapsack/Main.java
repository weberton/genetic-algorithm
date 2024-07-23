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

/**
 * Main class to run the knapsack problem
 * <p>
 * Populacao: Um conjunto de individuos que representam possiveis solucoes para um problema. No caso do problema da mochila, a populacao seria um conjunto de possiveis conjuntos de itens que podem ser colocados na mochila
 * Individuo: Uma possivel solucao para um problema. No caso do problema da mochila, um individuo seria um conjunto de itens que podem ser colocados na mochila
 * Gene: Um item individual em um cromossomo. No caso do problema da mochila, um gene seria um item que pode ser colocado na mochila
 * Cromossomo: Um conjunto de genes. No caso do problema da mochila, um cromossomo seria um conjunto de itens que podem ser colocados na mochila
 * Fitness: Uma funcao que nos diz quao boa e uma solucao. No caso do problema da mochila, o fitness seria o valor total dos itens na mochila
 * Mutacao: Um processo que altera um cromossomo de uma maneira aleatoria. No caso do problema da mochila, a mutacao seria a adicao ou remocao de um item da mochila
 * Selecao: Um processo que seleciona os individuos mais aptos para reproducao. No caso do problema da mochila, a selecao seria a escolha dos individuos com o maior valor total de itens na mochila
 * Cruzamento: Um processo que combina dois cromossomos para criar novos individuos. No caso do problema da mochila, o cruzamento seria a combinacao de dois conjuntos de itens para criar um novo conjunto de itens
 *
 */
public class Main {

    public static void main(String[] args) {
        // Inicializar a mochila com capacidade de 800
        Knapsack knapsack = new Knapsack(800);
        int populationSize = 500;
        int quantityOfItemsPerGroup = 30;
        double mutationRate = 0.01;

        // Inicializar a primeira população
        Population population = new Population(knapsack, populationSize, quantityOfItemsPerGroup, mutationRate);

        XYSeries series = new XYSeries("Score");
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFrame frame = new JFrame("Knapsack Score Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 650));

        while (!population.isKnapsackFull()) {
            population.calculateFitness();
            //TODO check strategy name
            population.generate();
            int generation = population.getGenerations();
            int score = population.getItemMaxScore().getScore();
            series.add(generation, score);

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
            subtitle.setText("Population size %d".formatted(populationSize));
            chart.addSubtitle(subtitle);

            ChartPanel chartPanel = new ChartPanel(chart);
            frame.getContentPane().add(chartPanel, BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);

        }


        System.out.printf("Solution found with %s generations. %n", population.getGenerations());
    }
}
