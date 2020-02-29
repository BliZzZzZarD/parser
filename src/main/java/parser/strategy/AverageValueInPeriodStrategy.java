package parser.strategy;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import parser.aggregations.Adder;
import parser.dto.ParsedRow;
import utils.DateUtils;

import java.time.LocalDate;

@Slf4j
public class AverageValueInPeriodStrategy implements CalculateStrategy {
    private final static String RESULT_TEMPLATE_PERIOD = "average value in the period with %s to %s is %s";
    private final static String RESULT_TEMPLATE = "average value is %s";

    private Adder adder;

    private LocalDate dateFrom;
    private LocalDate dateTo;

    public AverageValueInPeriodStrategy() {
        adder = new Adder();
    }

    public AverageValueInPeriodStrategy(LocalDate dateFrom, LocalDate dateTo) {
        adder = new Adder();
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    public void calculate(ParsedRow row) {
        log.info("calculate result for: {}", row.getName());

        if (ObjectUtils.allNotNull(dateTo, dateFrom)) {
            calculateWithPeriod(row);
        } else {
            adder.addValue(row.getPrice());
        }
    }

    private void calculateWithPeriod(ParsedRow row) {
        if (DateUtils.dateIsEqualsOrAfter(row.getDate(), dateFrom) && DateUtils.dateIsEqualsOrBefore(row.getDate(), dateTo)) {
            adder.addValue(row.getPrice());
        }
    }

    @Override
    public String getResult() {
        if (ObjectUtils.allNotNull(dateTo, dateFrom)) {
            return String.format(RESULT_TEMPLATE_PERIOD, DateUtils.format(dateFrom), DateUtils.format(dateTo), adder.getAverageValue());
        } else {
            return String.format(RESULT_TEMPLATE, adder.getAverageValue());
        }
    }
}
