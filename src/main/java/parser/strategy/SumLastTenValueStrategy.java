package parser.strategy;

import lombok.extern.slf4j.Slf4j;
import parser.dto.ParsedRow;
import utils.LimitedPriorityBlockingQueue;

import java.util.Comparator;

@Slf4j
public class SumLastTenValueStrategy implements CalculateStrategy {
    private final static Comparator<ParsedRow> comparator = Comparator.comparing(ParsedRow::getDate);

    private LimitedPriorityBlockingQueue<ParsedRow> priorityQueue;

    public SumLastTenValueStrategy() {
        priorityQueue = new LimitedPriorityBlockingQueue<>(10, comparator);
    }

    @Override
    public void calculate(ParsedRow row) {
        log.info("calculate result for: {}", row.getName());
        priorityQueue.add(row);
    }

    @Override
    public String getResult() {
        StringBuilder stringBuilder = new StringBuilder("\n10 last actual price:\n");
        priorityQueue.forEach(row -> stringBuilder.append(row).append("\n"));

        return stringBuilder.toString();
    }
}
