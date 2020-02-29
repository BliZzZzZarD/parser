package parser.strategy;

import parser.dto.ParsedRow;

public interface CalculateStrategy {
    void calculate(ParsedRow row);

    String getResult();
}
