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

public class Main {

    public static void main(String[] args) {
        Knapsack knapsack = new Knapsack(11);
        int populationSize = 500;
        int quantityOfItemsPerGroup = 2;
        double mutationRate = 0.01;

        //Population population = new Population(knapsack, populationSize, quantityOfItemsPerGroup, mutationRate);
        Population population = new Population(knapsack, quantityOfItemsPerGroup, mutationRate, "diamonds.csv");

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
            double score = population.getItemMaxScore().getScore();
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
            subtitle.setText("Population size %d. Items per Group %d".formatted(population.getPopulationSize(), quantityOfItemsPerGroup));
            chart.addSubtitle(subtitle);

            ChartPanel chartPanel = new ChartPanel(chart);
            frame.getContentPane().add(chartPanel, BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);

        }


        System.out.printf("Solution found with %s generations. %n", population.getGenerations());
    }
}
