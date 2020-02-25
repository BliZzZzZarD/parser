package parser.strategy;

import lombok.extern.slf4j.Slf4j;
import utils.DateAdapter;
import utils.LimitedPriorityBlockingQueue;

import java.util.Comparator;
import java.util.List;

@Slf4j
public class SumLastTenValueStrategy implements CalculateStrategy {
    private final static Comparator<List<String>> comparator = Comparator.comparing((List<String> args) -> DateAdapter.parse(args.get(1)));

    private LimitedPriorityBlockingQueue<List<String>> priorityQueue;

    public SumLastTenValueStrategy() {
        priorityQueue = new LimitedPriorityBlockingQueue<>(10, comparator);
    }

    @Override
    public void calculate(List<String> args) {
        log.info("calculate result for: {}", args.get(0));
        priorityQueue.add(args);
    }

    @Override
    public String getResult() {
        StringBuilder stringBuilder = new StringBuilder("\n10 last actual price:\n");
        priorityQueue.forEach(strings -> stringBuilder.append(strings).append("\n"));
        return stringBuilder.toString();
    }
}
