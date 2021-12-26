package simulation;

import java.util.Arrays;
import java.util.Random;

import static java.util.Arrays.sort;

public class Genotype {
    final int[] genes;

    public Genotype() {
        Random random = new Random();
        int[] genes = new int[32];
        for (int i = 0; i < 32; i++) {
            genes[i] = random.nextInt(8);
        }
        sort(genes);
        this.genes = genes;
    }

    public Genotype(int[] genes) {
        sort(genes);
        this.genes = genes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genotype genotype = (Genotype) o;
        return Arrays.equals(genes, genotype.genes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genes);
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }
}
