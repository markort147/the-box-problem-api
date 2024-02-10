package xyz.veganslab.marcoromano.services;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Represents a matrix used in dynamic programming to track combinations of items with associated prices and weights.
 * Each cell in the matrix represents a unique combination in terms of price and weight.
 */
@Getter
@Setter
public class CombinationMatrix {

    private Combination[][] combinations;

    /**
     * Inner class to encapsulate the price and weight of an item combination.
     * Provides methods to initialize and compare different combinations.
     */
    @Getter
    @Setter
    static class Combination {
        private BigDecimal price = new BigDecimal("0.00");
        private int weight = 0;

        /**
         * Default constructor for initializing a combination with default values.
         */
        public Combination() {
        }

        /**
         * Constructor for initializing a combination with specific price and weight.
         *
         * @param price  Total price of the combination.
         * @param weight Total weight of the combination.
         */
        public Combination(BigDecimal price, int weight) {
            this.price = price;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "[price:" + price + ", weight:" + weight + "]";
        }

        @Override
        public boolean equals(Object object) {
            if (object == null) {
                return false;
            } else {
                if (object instanceof Combination combination) {
                    return combination.getPrice().equals(price)
                            && combination.getWeight() == weight;
                } else {
                    return false;
                }
            }
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + (price != null ? price.hashCode() : 0);
            result = 31 * result + weight;
            return result;
        }

    }

    /**
     * Constructor for creating a matrix of specified dimensions based on the number of items and the maximum weight.
     *
     * @param numberOfItems The total number of items to consider.
     * @param maxWeight     The maximum weight constraint for combinations.
     */
    CombinationMatrix(int numberOfItems, int maxWeight) {
        combinations = new Combination[numberOfItems + 1][maxWeight + 1];
    }

}
