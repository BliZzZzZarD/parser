package parser.strategy;

import lombok.extern.slf4j.Slf4j;
import parser.dto.ParsedRow;
import parser.result.Adder;

@Slf4j
public class AverageValueStrategy implements CalculateStrategy {
    private final static String RESULT_TEMPLATE = "average value is %s";

    private Adder adder;

    public AverageValueStrategy() {
        adder = new Adder();
    }

    @Override
    public void calculate(ParsedRow row) {
        log.info("calculate result for: {}", row.getName());
        adder.addValue(row.getPrice());
    }

    @Override
    public String getResult() {
        return String.format(RESULT_TEMPLATE, adder.getAverageValue());
    }
}
