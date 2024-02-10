package xyz.veganslab.marcoromano.services;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import xyz.veganslab.marcoromano.configs.DataFormatConfiguration;
import xyz.veganslab.marcoromano.mocks.DataFormatConfigurationMock;
import xyz.veganslab.marcoromano.models.RequestDto;
import xyz.veganslab.marcoromano.testhelper.TestDataFactory;
import xyz.veganslab.marcoromano.utils.DoubleArrayUtils;
import xyz.veganslab.marcoromano.utils.WeightRescaler;

import java.math.BigDecimal;

@Log4j2
class CombinationMatrixBuilderTest {

    private RequestDto request;
    private CombinationMatrix matrix;
    private WeightRescaler weightRescaler;

    private void setupLoadingDataFromFile(String fileName) {
        weightRescaler = new WeightRescaler(new DataFormatConfigurationMock());
        request = TestDataFactory.createRequestFromFile(fileName);
    }

    private void buildMatrix() {
        CombinationMatrixBuilder combinationMatrixBuilder = new CombinationMatrixBuilder(weightRescaler);
        combinationMatrixBuilder.init(request);
        combinationMatrixBuilder.buildMatrix();
        matrix = combinationMatrixBuilder.getMatrix();
    }

    private void assertMatrixIsValidAndCheckLastElementIs(BigDecimal x) {
        assert (matrix.getCombinations().length == (request.getItems().size() + 1) && matrix.getCombinations()[0].length == (weightRescaler.rescaleAsInt(request.getMaxWeight()) + 1));
        assert (((CombinationMatrix.Combination) DoubleArrayUtils.getLastElement(matrix.getCombinations())).getPrice().equals(x));
    }

    private void assertMatrixIsValidAndCheckLastElementIncludingWeightIs(CombinationMatrix.Combination combination) {
        assert (matrix.getCombinations().length == (request.getItems().size() + 1) && matrix.getCombinations()[0].length == (weightRescaler.rescaleAsInt(request.getMaxWeight()) + 1));
        assert (DoubleArrayUtils.getLastElement(matrix.getCombinations()).equals(combination));
    }


    @Test
    void test_input_1() {
        String name = "input_1";
        System.out.println("*** Start test_" + name);
        setupLoadingDataFromFile(name);
        buildMatrix();
        assertMatrixIsValidAndCheckLastElementIs(new BigDecimal("8.00"));
    }

    @Test
    void test_input_2() {
        String name = "input_2";
        System.out.println("*** Start test_" + name);
        setupLoadingDataFromFile(name);
        buildMatrix();
        assertMatrixIsValidAndCheckLastElementIs(new BigDecimal("8.00"));
    }

    @Test
    void test_input_3() {
        String name = "input_3";
        System.out.println("*** Start test_" + name);
        setupLoadingDataFromFile(name);
        buildMatrix();
        assertMatrixIsValidAndCheckLastElementIs(new BigDecimal("6.20"));
    }

    @Test
    void test_input_4() {
        String name = "input_4";
        System.out.println("*** Start test_" + name);
        setupLoadingDataFromFile(name);
        buildMatrix();
        assertMatrixIsValidAndCheckLastElementIs(new BigDecimal("0.00"));
    }

    @Test
    void test_input_5() {
        String name = "input_5";
        System.out.println("*** Start test_" + name);
        setupLoadingDataFromFile(name);
        buildMatrix();
        assertMatrixIsValidAndCheckLastElementIs(new BigDecimal("0.00"));
    }

    @Test
    void test_input_6() {
        String name = "input_6";
        System.out.println("*** Start test_" + name);
        setupLoadingDataFromFile(name);
        buildMatrix();
        assertMatrixIsValidAndCheckLastElementIncludingWeightIs(new CombinationMatrix.Combination(new BigDecimal("8.00"), weightRescaler.rescaleAsInt(new BigDecimal("3.00"))));
    }

    @Test
    void test_example_1() {
        String name = "example_1";
        System.out.println("*** Start test_" + name);
        setupLoadingDataFromFile(name);
        buildMatrix();
        assertMatrixIsValidAndCheckLastElementIncludingWeightIs(new CombinationMatrix.Combination(new BigDecimal("148.00"), weightRescaler.rescaleAsInt(new BigDecimal("74.57"))));
    }

    @Test
    void test_example_2() {
        String name = "example_2";
        System.out.println("*** Start test_" + name);
        setupLoadingDataFromFile(name);
        buildMatrix();
        assertMatrixIsValidAndCheckLastElementIncludingWeightIs(new CombinationMatrix.Combination(new BigDecimal("76.00"), weightRescaler.rescaleAsInt(new BigDecimal("72.30"))));
    }

    @Test
    void test_example_3() {
        String name = "example_3";
        System.out.println("*** Start test_" + name);
        setupLoadingDataFromFile(name);
        buildMatrix();
        assertMatrixIsValidAndCheckLastElementIncludingWeightIs(new CombinationMatrix.Combination());
    }

    @Test
    void test_example_4() {
        String name = "example_4";
        System.out.println("*** Start test_" + name);
        setupLoadingDataFromFile(name);
        buildMatrix();
        assertMatrixIsValidAndCheckLastElementIncludingWeightIs(new CombinationMatrix.Combination(new BigDecimal("143.00"), weightRescaler.rescaleAsInt(new BigDecimal("26.12"))));
    }

}
