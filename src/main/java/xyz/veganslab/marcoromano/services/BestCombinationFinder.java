package xyz.veganslab.marcoromano.services;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.veganslab.marcoromano.models.ItemDto;
import xyz.veganslab.marcoromano.utils.DoubleArrayUtils;
import xyz.veganslab.marcoromano.utils.WeightRescaler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service to determine the optimal combination of items from a given combination matrix.
 * It employs a backward traversal of the matrix to identify items contributing to the optimal weight-value ratio.
 */
@Log4j2
@Service
@Setter
public class BestCombinationFinder {

    private CombinationMatrix matrix;
    private List<ItemDto> items;

    private final WeightRescaler weightRescaler;

    /**
     * Initializes the finder with a weight rescaler for item weight adjustments.
     *
     * @param weightRescaler A utility to rescale item weights.
     */
    @Autowired
    public BestCombinationFinder(WeightRescaler weightRescaler) {
        this.weightRescaler = weightRescaler;
    }

    /**
     * Computes the best combination of items from the matrix.
     * Traverses the matrix from the end to identify the included items in the optimal set.
     *
     * @return A set of integers representing item IDs in the best combination.
     */
    public Set<Integer> getBestCombination() {
        Set<Integer> bestCombination;
        if (bestCombinationNotExist()) {
            bestCombination = new HashSet<>();
        } else {
            bestCombination = getBestCombinationFromMatrix(items);
        }
        log.info("BestCombinationFinder.getBestCombination(). bestCombination=" + bestCombination);
        return bestCombination;
    }

    private boolean bestCombinationNotExist() {
        return DoubleArrayUtils.getLastElement(matrix.getCombinations()).equals(new CombinationMatrix.Combination());
    }

    private Set<Integer> getBestCombinationFromMatrix(List<ItemDto> items) {
        Set<Integer> bestCombination = new HashSet<>();
        int itemRow = matrix.getCombinations().length - 1;
        int weightColumn = matrix.getCombinations()[0].length - 1;
        while (itemRow > 0 && weightColumn > 0) {
            if (removingItemThePriceChanges(itemRow, weightColumn)) {
                ItemDto itemOfBestCombination = items.get(itemRow - 1);
                bestCombination.add(itemOfBestCombination.getId());
                weightColumn = weightColumn - weightRescaler.rescaleAsInt(itemOfBestCombination.getWeight());
            }
            itemRow--;
        }
        return bestCombination;
    }

    private boolean removingItemThePriceChanges(int itemRow, int weightColumn) {
        return !matrix.getCombinations()[itemRow][weightColumn].equals(matrix.getCombinations()[itemRow - 1][weightColumn]);
    }

}
