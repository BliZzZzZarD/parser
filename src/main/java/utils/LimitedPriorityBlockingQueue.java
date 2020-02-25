package utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.Consumer;

@Slf4j
public class LimitedPriorityBlockingQueue<T> {
    @Getter @Setter
    private int limitedCapacity;
    @Getter
    private Comparator<T> comparator;

    private PriorityQueue<T> priorityQueue;

    public LimitedPriorityBlockingQueue(int limitedCapacity, Comparator<T> comparator) {
        this.limitedCapacity = limitedCapacity;
        this.comparator = comparator;

        priorityQueue = new PriorityQueue<>(limitedCapacity, comparator);
    }

    public void add(T element) {
        if (priorityQueue.size() < limitedCapacity) {
            priorityQueue.add(element);
        } else {
            analysisQueue(priorityQueue.peek(), element);
        }
    }

    private void analysisQueue(T header, T element) {
        if (comparator.compare(header, element) < 0) {
            priorityQueue.remove(header);
            priorityQueue.add(element);
        }
    }

    public void forEach(Consumer<? super T> action) {
        priorityQueue.forEach(action);
    }
}
