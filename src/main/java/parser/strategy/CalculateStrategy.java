package parser.strategy;

import java.util.List;

public interface CalculateStrategy {
    String EMPTY_RESULT = "EMPTY RESULT";

    void calculate(List<String> args);
    String getResult();
}
