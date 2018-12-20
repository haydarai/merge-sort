package utils;

import java.util.Random;

public class RandomInteger {

    private Random random;

    public RandomInteger() {
        random = new Random();
    }

    public int nextInt() {
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;

        return random.ints(min, max).findFirst().getAsInt();
    }
}
