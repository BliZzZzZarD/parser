package parser.result;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import parser.strategy.CalculateStrategy;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class Result {
    CalculateStrategy calculateStrategy;

    public void calculate(List<String> args) {
        calculateStrategy.calculate(args);
    }

    public String getResult() {
        return calculateStrategy.getResult();
    }
}
