package parser.strategy;

import lombok.extern.slf4j.Slf4j;
import parser.dto.ParsedRow;
import parser.result.Adder;
import utils.DateAdapter;

import java.time.LocalDate;

@Slf4j
public class AverageValueInPeriodStrategy implements CalculateStrategy {
    private final static String RESULT_TEMPLATE = "average value in the period with %s to %s is %s";

    private Adder adder;

    private LocalDate dateFrom;
    private LocalDate dateTo;

    public AverageValueInPeriodStrategy(LocalDate dateFrom, LocalDate dateTo) {
        adder = new Adder();
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    public void calculate(ParsedRow row) {
        log.info("calculate result for: {}", row.getName());

        if (dateIsEqualsOrAfter(row.getDate(), dateFrom) && dateIsEqualsOrBefore(row.getDate(), dateTo)) {
            adder.addValue(row.getPrice());
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
        return String.format(RESULT_TEMPLATE, DateAdapter.format(dateFrom), DateAdapter.format(dateTo), adder.getAverageValue());
    }
}
