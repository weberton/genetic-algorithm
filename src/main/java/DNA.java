import java.util.Random;

class DNA {
    private char[] genes;
    private double fitness;
    private static final Random random = new Random();

    // Constructor (makes a random DNA)
    public DNA(int num) {
        genes = new char[num];
        fitness = 0;
        for (int i = 0; i < num; i++) {
            genes[i] = newChar(); // Pick from range of chars
        }
    }

    // Generates a new random character
    private char newChar() {
        //return (char) ('a' + random.nextInt(26));
        int c = random.nextInt(60) + 63;
        if (c == 63) {
            c = 32;
        }
        if (c == 64) {
            c = 46;
        }
        return (char) c;
    }

    // Converts character array to a String
    public String getPhrase() {
        return new String(genes);
    }

    // Fitness function (returns floating point % of "correct" characters)
    public void calcFitness(String target) {
        int score = 0;
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == target.charAt(i)) {
                score++;
            }
        }
        fitness = (double) score / target.length();
    }

    // Crossover
    public DNA crossover(DNA partner) {
        // A new child
        DNA child = new DNA(genes.length);
        int midpoint = random.nextInt(genes.length); // Pick a midpoint

        // Half from one, half from the other
        for (int i = 0; i < genes.length; i++) {
            if (i > midpoint) {
                child.genes[i] = genes[i];
            } else {
                child.genes[i] = partner.genes[i];
            }
        }
        return child;
    }

    // Based on a mutation probability, picks a new random character
    public void mutate(double mutationRate) {
        for (int i = 0; i < genes.length; i++) {
            if (random.nextDouble() < mutationRate) {
                genes[i] = newChar();
            }
        }
    }

    public double getFitness() {
        return fitness;
    }

    public static void main(String[] args) {
        // Example usage
        DNA dna = new DNA(10);
        System.out.println("Initial Phrase: " + dna.getPhrase());

        String target = "hello world";
        dna.calcFitness(target);
        System.out.println("Fitness: " + dna.getFitness());

        DNA partner = new DNA(10);
        DNA child = dna.crossover(partner);
        System.out.println("Child Phrase: " + child.getPhrase());

        child.mutate(0.01);
        System.out.println("Mutated Child Phrase: " + child.getPhrase());
    }
}
