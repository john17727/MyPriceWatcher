package cs4330.cs.utep.edu.mypricewatcher;

import java.util.Random;

/**
 * <h2>PriceFinder</h2>
 * Utilizes a range to generate a random price.
 * Uses the initial price as the max number for the range and
 * a user inputed number for the bottom of the range.
 * @author Juan Rincon
 */
public class PriceFinder {

    private double max; //initPrice
    private double min; //Hardcoded for now.

    public PriceFinder(double max, double min) {
        this.max = max;
        this.min = min;
    }

    /**
     * Generates a random number using the variables max and min
     * as a range.
     * @return A random generated number.
     */
    public double getSimulatedPrice() {
        Random r = new Random();
        int maxInt = (int) max;
        int minInt = (int) min;
        int randInt = r.nextInt((maxInt - minInt) + 1) + minInt;
        Double randDouble = new Double(randInt);
        return randDouble;
    }
}
