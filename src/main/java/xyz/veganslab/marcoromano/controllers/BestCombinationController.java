package xyz.veganslab.marcoromano.controllers;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.veganslab.marcoromano.models.RequestDto;
import xyz.veganslab.marcoromano.models.ResponseDto;
import xyz.veganslab.marcoromano.services.BestCombinationService;

/**
 * Controller class for handling API requests related to calculating the best combination of items.
 * It delegates the business logic to the BestCombinationService.
 */
@Log4j2
@RestController
@RequestMapping("/api/best-combination")
public class BestCombinationController {

    private final BestCombinationService bestCombinationService;

    /**
     * Initializes the controller with the required service dependency.
     *
     * @param bestCombinationService Service responsible for calculating the best combination.
     */
    @Autowired
    BestCombinationController(BestCombinationService bestCombinationService) {
        this.bestCombinationService = bestCombinationService;
    }

    /**
     * Endpoint to handle POST requests for the best combination calculation.
     * Validates the incoming request and returns the optimal combination.
     *
     * @param request Request containing the items and parameters for the combination calculation.
     * @return ResponseEntity with the calculated response.
     */
    @PostMapping
    public ResponseEntity<ResponseDto> bestCombinationPost(@RequestBody @Valid RequestDto request) {
        log.info("BestCombinationController.bestCombinationPost() /api/best-combination POST. request=" + request);
        ResponseDto response = bestCombinationService.process(request);
        log.info("BestCombinationController.bestCombinationPost() /api/best-combination POST. response=" + response);
        return ResponseEntity.ok(response);
    }

}
