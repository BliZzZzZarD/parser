package parser.result;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Accessors(chain = true)
public class Adder {
    @Getter
    @Setter
    private BigDecimal amount = BigDecimal.ZERO;

    private int counter = 0;

    public void incrementCount() {
        counter++;
    }

    public BigDecimal getAverageValue() {
        return amount.divide(new BigDecimal(counter), RoundingMode.CEILING);
    }
}
