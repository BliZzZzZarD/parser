package parser.strategy;

import lombok.extern.slf4j.Slf4j;
import utils.DateAdapter;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

@Slf4j
public class SumLastTenValueStrategy implements CalculateStrategy {
    private final static Comparator<List<String>> comparator = Comparator.comparing((List<String> args) -> DateAdapter.parse(args.get(1)));

    private PriorityBlockingQueue<List<String>> priorityQueue;

    public SumLastTenValueStrategy() {
        priorityQueue = new PriorityBlockingQueue<>(10, comparator);
    }

    @Override
    public void calculate(List<String> args) {
        log.info("calculate result for: {}", args.get(0));
        if (priorityQueue.size() < 10) {
            priorityQueue.add(args);
        } else {
            analysisQueue(priorityQueue.peek(), args);
        }
    }

    private void analysisQueue(List<String> header, List<String> args) {
        if (DateAdapter.parse(header.get(1)).isBefore(DateAdapter.parse(args.get(1)))) {
            priorityQueue.remove(header);
            priorityQueue.add(args);
        }
    }

    @Override
    public String getResult() {
        StringBuilder stringBuilder = new StringBuilder("\n10 last actual price:\n");
        priorityQueue.forEach(strings -> stringBuilder.append(strings).append("\n"));
        return stringBuilder.toString();
    }
}
