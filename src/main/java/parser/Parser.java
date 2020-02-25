package parser;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import parser.result.Result;
import parser.strategy.AverageValueStrategy;
import parser.strategy.SumLastTenValueStrategy;
import parser.strategy.AverageValueInPeriodStrategy;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
public class Parser {
    private final static String RESULT_TEMPLATE = "For %s result of calculation: %s";

    private final static String NAME1 = "INSTRUMENT1";
    private final static String NAME2 = "INSTRUMENT2";
    private final static String NAME3 = "INSTRUMENT3";

    private File file;
    private Map<String, Result> results;

    public Parser(File file) {
        this.file = file;

        fillResults();
    }

    private void fillResults() {
        results = new HashMap<>();
        results.put(NAME1, new Result(new AverageValueStrategy()));
        results.put(NAME2, new Result(new AverageValueInPeriodStrategy("01-Nov-2014", "30-Nov-2014")));
        results.put(NAME3, new Result(new SumLastTenValueStrategy()));
    }

    public void start() {
        log.info("execute has been started");
        parseFile();
        showResults();
    }

    private void parseFile() {
        try (Stream<String> stream = Files.newBufferedReader(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8).lines()) {
            stream.forEach(this::parseAndAnalysisRow);
        } catch (IOException e) {
            printReadError(e);
        }
    }

    private void parseAndAnalysisRow(String row) {
        List<String> splitRow = Arrays.asList(row.split(","));
        Result result = Try.of(()-> results.get(splitRow.get(0))).getOrNull();
        if (ObjectUtils.allNotNull(result)) {
            results.get(splitRow.get(0)).calculate(splitRow);
        }
    }

    private void printReadError(IOException e) {
        log.error("An error has occurred while reading the file. Error: {}", e.getMessage());
        System.out.print("An error has occurred while reading the file. Try again.");
    }

    private void showResults() {
        results.forEach(this::showResult);
    }

    private void showResult(String name, Result result) {
        System.out.println(String.format(RESULT_TEMPLATE, name, result.getResult()));
    }
}
