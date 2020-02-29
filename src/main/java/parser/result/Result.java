package parser.result;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import parser.dto.ParsedRow;
import parser.strategy.CalculateStrategy;

@Slf4j
@AllArgsConstructor
public class Result {
    CalculateStrategy calculateStrategy;

    public void calculate(ParsedRow row) {
        calculateStrategy.calculate(row);
    }

    public String getResult() {
        return calculateStrategy.getResult();
    }
}
