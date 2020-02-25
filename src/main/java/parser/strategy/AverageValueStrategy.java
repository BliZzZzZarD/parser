package parser.strategy;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import parser.result.Adder;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class AverageValueStrategy implements CalculateStrategy {
    private final static String RESULT_TEMPLATE = "average value is %s";

    private Adder adder;

    public AverageValueStrategy() {
        adder = new Adder();
    }

    @Override
    public void calculate(List<String> args) {
        log.info("calculate result for: {}", args.get(0));
        Try.run(() -> processCalculate(args.get(2))).onFailure(this::errorLog);
    }

    private void processCalculate(String value) {
        adder.setAmount(adder.getAmount().add(new BigDecimal(value)));
        adder.incrementCount();
    }

    private void errorLog(Throwable throwable) {
        log.error("calculate has finished with error: {}", throwable.getMessage());
    }

    @Override
    public String getResult() {
        if (BigDecimal.ZERO.compareTo(adder.getAmount()) >= 0) {
            return EMPTY_RESULT;
        }

        return String.format(RESULT_TEMPLATE, adder.getAverageValue());
    }
}
