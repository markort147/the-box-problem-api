package xyz.veganslab.marcoromano.services;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.veganslab.marcoromano.models.ItemDto;
import xyz.veganslab.marcoromano.models.RequestDto;
import xyz.veganslab.marcoromano.utils.DoubleArrayUtils;
import xyz.veganslab.marcoromano.utils.WeightRescaler;

import java.util.List;

/**
 * Service responsible for constructing a combination matrix for item optimization.
 * Utilizes dynamic programming to build a matrix where each cell represents an optimal combination of items.
 */
@Log4j2
@Service
public class CombinationMatrixBuilder {

    private List<ItemDto> items;

    @Getter
    private CombinationMatrix matrix;

    private final WeightRescaler weightRescaler;

    /**
     * Constructor to initialize the builder with a weight rescaler.
     *
     * @param weightRescaler Component for rescaling item weights.
     */
    @Autowired
    public CombinationMatrixBuilder(WeightRescaler weightRescaler) {
        this.weightRescaler = weightRescaler;
    }

    /**
     * Initializes the builder with request data and prepares the matrix.
     *
     * @param requestDto Request data containing items and weight constraints.
     */
    public void init(RequestDto requestDto) {
        items = requestDto.getItems();
        matrix = new CombinationMatrix(items.size(), weightRescaler.rescaleAsInt(requestDto.getMaxWeight()));
    }

    /**
     * Builds the combination matrix using the items and the rescaled weights.
     */
    public void buildMatrix() {
        for (int eachNumOfItems = 0; eachNumOfItems < matrix.getCombinations().length; eachNumOfItems++) {
            tryToAddNewItem(eachNumOfItems);
        }
        log.info("CombinationMatrixBuilder().buildMatrix() last element=" + DoubleArrayUtils.getLastElement(matrix.getCombinations()));
    }

    private void tryToAddNewItem(int newNumOfItems) {
        for (int eachMaxWeight = 0; eachMaxWeight < matrix.getCombinations()[0].length; eachMaxWeight++) {
            if (newNumOfItems == 0 || eachMaxWeight == 0) {
                matrix.getCombinations()[newNumOfItems][eachMaxWeight] = new CombinationMatrix.Combination();
            } else {
                ItemDto newItem = items.get(newNumOfItems - 1);
                matrix.getCombinations()[newNumOfItems][eachMaxWeight] = getCombinationWithNewItemByIncreasingWeight(newItem, eachMaxWeight);
            }
            log.debug("CombinationMatrixBuilder().buildMatrix(). matrix.getCombinations()[" + newNumOfItems + "][" + eachMaxWeight + "]\t=\t" + matrix.getCombinations()[newNumOfItems][eachMaxWeight]);
        }
    }

    private CombinationMatrix.Combination getCombinationWithNewItemByIncreasingWeight(ItemDto newItem, int newMaxWeight) {
        CombinationMatrix.Combination combinationWithoutNewItemAtSameMaxWeight = matrix.getCombinations()[items.indexOf(newItem)][newMaxWeight];

        if (newItemWeightExceedsNewMaxWeight(newItem, newMaxWeight)) {
            return combinationWithoutNewItemAtSameMaxWeight;
        } else {
            CombinationMatrix.Combination combinationWithNewItem = getCombinationWithNewItem(newItem, newMaxWeight);

            if (newCombinationIsBetter(combinationWithoutNewItemAtSameMaxWeight, combinationWithNewItem)) {
                return combinationWithNewItem;
            } else {
                return combinationWithoutNewItemAtSameMaxWeight;
            }
        }
    }

    private CombinationMatrix.Combination getCombinationWithNewItem(ItemDto newItem, int newMaxWeight) {
        int newItemRescaledWeight = weightRescaler.rescaleAsInt(newItem.getWeight());
        CombinationMatrix.Combination combinationWithoutNewItem = matrix.getCombinations()[items.indexOf(newItem)][newMaxWeight - newItemRescaledWeight];
        return new CombinationMatrix.Combination(combinationWithoutNewItem.getPrice().add(newItem.getPrice()), combinationWithoutNewItem.getWeight() + newItemRescaledWeight);
    }

    private boolean newItemWeightExceedsNewMaxWeight(ItemDto newItem, int newMaxWeight) {
        return weightRescaler.rescaleAsInt(newItem.getWeight()) > newMaxWeight;
    }

    private boolean newCombinationIsBetter(CombinationMatrix.Combination previousElement, CombinationMatrix.Combination elementWithNewItem) {
        return priceIncrease(previousElement, elementWithNewItem) || priceIsTheSameButWeightDecrease(previousElement, elementWithNewItem);
    }

    private static boolean priceIncrease(CombinationMatrix.Combination previousElement, CombinationMatrix.Combination elementWithNewItem) {
        if (elementWithNewItem.getPrice().compareTo(previousElement.getPrice()) > 0) {
            log.trace("CombinationMatrixBuilder().buildMatrix(). **** new combination has higher price");
            return true;
        }
        return false;
    }

    private static boolean priceIsTheSameButWeightDecrease(CombinationMatrix.Combination previousElement, CombinationMatrix.Combination elementWithNewItem) {
        if (elementWithNewItem.getPrice().equals(previousElement.getPrice()) && elementWithNewItem.getWeight() < previousElement.getWeight()) {
            log.trace("CombinationMatrixBuilder().buildMatrix(). **** new combination has same price but less weight");
            return true;
        }
        return false;
    }
}
