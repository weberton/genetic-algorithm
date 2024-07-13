import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Population {
    private DNA[] population; // Array to hold the current population
    private List<DNA> matingPool; // List which we will use for our "mating pool"
    private int generations; // Number of generations
    private boolean finished; // Are we finished evolving?
    private String target; // Target phrase
    private double mutationRate; // Mutation rate
    private double perfectScore = 1.0;
    private String best; // Best phrase
    private static final Random random = new Random();

    // Constructor
    public Population(String target, double mutationRate, int num) {
        this.target = target;
        this.mutationRate = mutationRate;
        this.generations = 0;
        this.finished = false;
        this.best = "";

        this.population = new DNA[num];
        for (int i = 0; i < num; i++) {
            this.population[i] = new DNA(target.length());
        }
        this.matingPool = new ArrayList<>();
        this.calcFitness();
    }

    // Fill our fitness array with a value for every member of the population
    public void calcFitness() {
        for (DNA dna : population) {
            dna.calcFitness(target);
        }
    }

    // Generate a mating pool
    public void naturalSelection() {
        // Clear the ArrayList
        matingPool.clear();

        double maxFitness = 0;
        for (DNA dna : population) {
            if (dna.getFitness() > maxFitness) {
                maxFitness = dna.getFitness();
            }
        }

        // Based on fitness, each member will get added to the mating pool a certain number of times
        // a higher fitness = more entries to mating pool = more likely to be picked as a parent
        // a lower fitness = fewer entries to mating pool = less likely to be picked as a parent
        for (DNA dna : population) {
            double fitness = map(dna.getFitness(), 0, maxFitness, 0, 1);
            int n = (int) (fitness * 100); // Arbitrary multiplier, we can also use monte carlo method
            for (int j = 0; j < n; j++) {
                matingPool.add(dna);
            }
        }
    }

    // Create a new generation
    public void generate() {
        // Refill the population with children from the mating pool
        for (int i = 0; i < population.length; i++) {
            int a = random.nextInt(matingPool.size());
            int b = random.nextInt(matingPool.size());
            DNA partnerA = matingPool.get(a);
            DNA partnerB = matingPool.get(b);
            DNA child = partnerA.crossover(partnerB);
            child.mutate(mutationRate);
            population[i] = child;
        }
        generations++;
    }

    public String getBest() {
        return best;
    }

    // Compute the current "most fit" member of the population
    public void evaluate() {
        double worldRecord = 0.0;
        int index = 0;
        for (int i = 0; i < population.length; i++) {
            if (population[i].getFitness() > worldRecord) {
                index = i;
                worldRecord = population[i].getFitness();
            }
        }

        best = population[index].getPhrase();
        if (worldRecord == perfectScore) {
            finished = true;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public int getGenerations() {
        return generations;
    }

    // Compute average fitness for the population
    public double getAverageFitness() {
        double total = 0;
        for (DNA dna : population) {
            total += dna.getFitness();
        }
        return total / population.length;
    }

    public String allPhrases() {
        StringBuilder everything = new StringBuilder();
        int displayLimit = Math.min(population.length, 50);

        for (int i = 0; i < displayLimit; i++) {
            everything.append(population[i].getPhrase()).append("\n");
        }
        return everything.toString();
    }

    private double map(double value, double start1, double stop1, double start2, double stop2) {
        return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
    }

    public static void main(String[] args) {
        // Example usage
        Population pop = new Population("to be or not to be", 0.01, 200);
        while (!pop.isFinished()) {
            pop.naturalSelection();
            pop.generate();
            pop.evaluate();
            System.out.println("Best phrase: " + pop.getBest());
            System.out.println("Average fitness: " + pop.getAverageFitness());
            System.out.println("Generations: " + pop.getGenerations());
        }
    }
}
