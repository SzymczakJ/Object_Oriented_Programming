package simulation;

import java.util.Random;

public class Genotype {
    final int[] genes;

    public Genotype() {
        Random random = new Random();
        int[] genes = new int[32];
        for (int i = 0; i < 32; i++) {
            genes[i] = random.nextInt(8);
        }
        this.genes = genes;
    }
}
