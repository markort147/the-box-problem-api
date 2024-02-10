package xyz.veganslab.marcoromano.services;

import org.junit.jupiter.api.Test;
import xyz.veganslab.marcoromano.configs.DataFormatConfiguration;
import xyz.veganslab.marcoromano.mocks.DataFormatConfigurationMock;
import xyz.veganslab.marcoromano.models.RequestDto;
import xyz.veganslab.marcoromano.testhelper.TestDataFactory;
import xyz.veganslab.marcoromano.utils.WeightRescaler;

import java.util.*;

class BestCombinationFinderTest {

    private RequestDto request;
    private CombinationMatrix matrix;
    private Set<Integer> bestCombination;
    private WeightRescaler weightRescaler;

    private void setupLoadingDataFromFile(String fileName) {
        request = TestDataFactory.createRequestFromFile(fileName);
        weightRescaler = new WeightRescaler(new DataFormatConfigurationMock());
        CombinationMatrixBuilder combinationMatrixBuilder = new CombinationMatrixBuilder(weightRescaler);
        combinationMatrixBuilder.init(request);
        combinationMatrixBuilder.buildMatrix();
        matrix = combinationMatrixBuilder.getMatrix();
    }

    private void findBestCombination() {
        BestCombinationFinder bestCombinationFinder = new BestCombinationFinder(weightRescaler);
        bestCombinationFinder.setMatrix(matrix);
        bestCombinationFinder.setItems(request.getItems());
        bestCombination = bestCombinationFinder.getBestCombination();
    }

    private void assertCombinationIs(Integer... args) {
        assert (bestCombination.equals(new HashSet<>(Arrays.asList(args))));
    }

    @Test
    void test_example_1() {
        System.out.println("*** Start test_example_1");
        setupLoadingDataFromFile("example_1");
        findBestCombination();
        assertCombinationIs(2, 7);
        System.out.println("*** End test_example_1");
    }

    @Test
    void test_example_2() {
        System.out.println("*** Start test_example_2");
        setupLoadingDataFromFile("example_2");
        findBestCombination();
        assertCombinationIs(4);
        System.out.println("*** End test_example_2");
    }

    @Test
    void test_example_3() {
        System.out.println("*** Start test_example_3");
        setupLoadingDataFromFile("example_3");
        findBestCombination();
        assertCombinationIs();
        System.out.println("*** End test_example_3");
    }

    @Test
    void test_example_4() {
        System.out.println("*** Start test_example_4");
        setupLoadingDataFromFile("example_4");
        findBestCombination();
        assertCombinationIs(8, 9);
        System.out.println("*** End test_example_4");
    }

    @Test
    void test_input_1() {
        String name = "input_1";
        System.out.println("*** Start test_" + name);

        setupLoadingDataFromFile(name);
        findBestCombination();
        assertCombinationIs(2, 3);
        System.out.println("*** End test_example_4");
    }

    @Test
    void test_input_2() {
        String name = "input_1";
        System.out.println("*** Start test_" + name);

        setupLoadingDataFromFile(name);
        findBestCombination();
        assertCombinationIs(2, 3);
        System.out.println("*** End test_example_4");
    }

    @Test
    void test_input_3() {
        String name = "input_3";
        System.out.println("*** Start test_" + name);

        setupLoadingDataFromFile(name);
        findBestCombination();
        assertCombinationIs(1, 3);
        System.out.println("*** End test_example_4");
    }

    @Test
    void test_input_4() {
        String name = "input_4";
        System.out.println("*** Start test_" + name);

        setupLoadingDataFromFile(name);
        findBestCombination();
        assertCombinationIs();
        System.out.println("*** End test_example_4");
    }

    @Test
    void test_input_5() {
        String name = "input_5";
        System.out.println("*** Start test_" + name);

        setupLoadingDataFromFile(name);
        findBestCombination();
        assertCombinationIs();
        System.out.println("*** End test_example_4");
    }

    @Test
    void test_input_6() {
        String name = "input_6";
        System.out.println("*** Start test_" + name);

        setupLoadingDataFromFile(name);
        findBestCombination();
        assertCombinationIs(4, 2);
        System.out.println("*** End test_example_4");
    }
}
