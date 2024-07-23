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

        //Population population = new Population(knapsack, populationSize, quantityOfItemsPerGroup, mutationRate);
        Population population = new Population(knapsack, populationSize, quantityOfItemsPerGroup, mutationRate, "diamonds.csv");

        XYSeries series = new XYSeries("Score");
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFrame frame = new JFrame("Knapsack Score Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 650));

        int count = 0;

        while ( count < 1000) { //!population.isKnapsackFull()
            population.calculateFitness();
            //TODO check strategy names
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

            count++;

        }


        System.out.printf("Solution found with %s generations. %n", population.getGenerations());
    }
}
