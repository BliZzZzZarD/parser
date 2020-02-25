package parser.strategy;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import parser.result.Adder;
import utils.DateAdapter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
public class AverageValueInPeriodStrategy implements CalculateStrategy {
    private final static String RESULT_TEMPLATE = "average value in the period with %s to %s is %s";

    private LocalDate valueDateFrom;
    private LocalDate valueDateTo;

    private Adder adder;

    public AverageValueInPeriodStrategy(String dateFrom, String dateTo) {
        valueDateFrom = DateAdapter.parse(dateFrom);
        valueDateTo = DateAdapter.parse(dateTo);
        adder = new Adder();
    }

    @Override
    public void calculate(List<String> args) {
        log.info("calculate result for: {}", args.get(0));
        Try.run(() -> processCalculate(args)).onFailure(this::errorLog);
    }

    private void processCalculate(List<String> args) {
        LocalDate parseDate = DateAdapter.parse(args.get(1));

        if (dateIsEqualsOrAfter(parseDate, valueDateFrom) && dateIsEqualsOrBefore(parseDate, valueDateTo)) {
            adder.setAmount(adder.getAmount().add(new BigDecimal(args.get(2))));
            adder.incrementCount();
        }
    }

    private boolean dateIsEqualsOrAfter(LocalDate date, LocalDate comparisonDate) {
        return date.isAfter(comparisonDate) || date.equals(comparisonDate);
    }

    private boolean dateIsEqualsOrBefore(LocalDate date, LocalDate comparisonDate) {
        return date.isBefore(comparisonDate) || date.equals(comparisonDate);
    }

    private void errorLog(Throwable throwable) {
        log.error("calculate has finished with error: {}", throwable.getMessage());
    }

    @Override
    public String getResult() {
        if (BigDecimal.ZERO.compareTo(adder.getAmount()) >= 0) {
            return EMPTY_RESULT;
        }

        return String.format(RESULT_TEMPLATE, DateAdapter.format(valueDateFrom), DateAdapter.format(valueDateTo), adder.getAverageValue());
    }
}
