public class EvolvingShakespeare {
    private static String target;
    private static int popmax;
    private static double mutationRate;
    private static Population population;

    public static void setup() {
        target = "To be or not to be.";
        popmax = 200;
        mutationRate = 0.01;

        // Create a population with a target phrase, mutation rate, and population max
        population = new Population(target, mutationRate, popmax);
    }

    public static void draw() {
        // Generate mating pool
        population.naturalSelection();
        // Create next generation
        population.generate();
        // Calculate fitness
        population.calcFitness();
        // Evaluate the population
        population.evaluate();

        // If we found the target phrase, stop
        if (population.isFinished()) {
            System.out.println("Target found in " + population.getGenerations() + " generations.");
            displayInfo();
            System.exit(0); // Stop the program
        }

        displayInfo();
    }

    private static void displayInfo() {
        // Display current status of population
        String answer = population.getBest();
        System.out.println("Best phrase: " + answer);

        String statstext =
                "total generations:     " + population.getGenerations() + "\n";
        statstext +=
                "average fitness:       " + population.getAverageFitness() + "\n";
        statstext += "total population:      " + popmax + "\n";
        statstext += "mutation rate:         " + (int) (mutationRate * 100) + "%\n";

        System.out.println(statstext);
        System.out.println("All phrases:\n" + population.allPhrases());
    }

    public static void main(String[] args) {
        setup();
        while (true) {
            draw();
            try {
                Thread.sleep(100); // Add a small delay to observe the process
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

