package com.markort147.services;

import com.markort147.models.ItemDto;
import com.markort147.models.RequestDto;
import com.markort147.models.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Provides services to find the best combination of items from a given request.
 * Leverages a combination matrix and the best combination finder algorithm to
 * determine the most suitable set of items.
 */
@Service
public class BestCombinationService {

    private final CombinationMatrixBuilder combinationMatrixBuilder;
    private final BestCombinationFinder bestCombinationFinder;

    /**
     * Initializes the service with necessary dependencies.
     *
     * @param combinationMatrixBuilder A utility to build the combination matrix.
     * @param bestCombinationFinder    An algorithm to find the optimal combination of items.
     */
    @Autowired
    public BestCombinationService(CombinationMatrixBuilder combinationMatrixBuilder, BestCombinationFinder bestCombinationFinder) {
        this.combinationMatrixBuilder = combinationMatrixBuilder;
        this.bestCombinationFinder = bestCombinationFinder;
    }

    /**
     * Processes the request to find the best combination of items.
     *
     * @param requestDto The request containing the items and relevant parameters.
     * @return A response encapsulating the best combination found.
     */
    public ResponseDto process(RequestDto requestDto) {
        CombinationMatrix combinationMatrix = getMatrix(requestDto);
        Set<Integer> bestCombination = getBestCombination(requestDto.getItems(), combinationMatrix);
        return buildResponse(bestCombination);
    }

    private CombinationMatrix getMatrix(RequestDto requestDto) {
        combinationMatrixBuilder.init(requestDto);
        combinationMatrixBuilder.buildMatrix();
        return combinationMatrixBuilder.getMatrix();
    }

    private Set<Integer> getBestCombination(List<ItemDto> items, CombinationMatrix combinationMatrix) {
        bestCombinationFinder.setMatrix(combinationMatrix);
        bestCombinationFinder.setItems(items);
        return bestCombinationFinder.getBestCombination();
    }

    private static ResponseDto buildResponse(Set<Integer> bestCombination) {
        ResponseDto response = new ResponseDto();
        response.setItems(bestCombination);
        return response;
    }
}
