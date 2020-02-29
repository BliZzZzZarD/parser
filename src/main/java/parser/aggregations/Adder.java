package parser.aggregations;

import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Accessors(chain = true)
public class Adder {
    private BigDecimal sum = BigDecimal.ZERO;
    private int counter = 0;

    public void addValue(BigDecimal value) {
        sum = sum.add(value);
        counter++;
    }

    public BigDecimal getAverageValue() {
        return counter != 0  ? sum.divide(new BigDecimal(counter), RoundingMode.CEILING) : BigDecimal.ZERO;
    }
}
