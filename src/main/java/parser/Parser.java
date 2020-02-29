package parser;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import parser.dto.ParsedRow;
import parser.dto.ParsedRowMapper;
import parser.result.Result;
import parser.strategy.AverageValueInPeriodStrategy;
import parser.strategy.AverageValueStrategy;
import parser.strategy.SumLastTenValueStrategy;
import utils.DateAdapter;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
public class Parser {
    private final static String RESULT_TEMPLATE = "For %s result of calculation: %s";

    private boolean success = true;

    private final static String NAME1 = "INSTRUMENT1";
    private final static String NAME2 = "INSTRUMENT2";
    private final static String NAME3 = "INSTRUMENT3";

    private File file;

    @Getter
    private Map<String, Result> results;

    public Parser(File file) {
        this.file = file;

        fillResults();
    }

    private void fillResults() {
        results = new HashMap<>();
        results.put(NAME1, new Result(new AverageValueStrategy()));
        results.put(NAME2, new Result(new AverageValueInPeriodStrategy(DateAdapter.parseOrNull("01-Nov-2014"), DateAdapter.parseOrNull(("30-Nov-2014")))));
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
        } catch (Exception e) {
            handleError(e);
        }
    }

    private void parseAndAnalysisRow(String rowString) {
        ParsedRow row = ParsedRowMapper.getParserRow(rowString);

        Optional
                .ofNullable(results.get(row.getName()))
                .ifPresent(result -> result.calculate(row));
    }

    private void handleError(Exception e) {
        log.error("An error has occurred while reading the file. Error: {}", e.getMessage());

        success = false;
        System.out.print("An error has occurred while reading the file. Try again.\n");
    }

    private void showResults() {
        if (success)
            results.forEach(this::showResult);
    }

    private void showResult(String name, Result result) {
        System.out.println(String.format(RESULT_TEMPLATE, name, result.getResult()));
    }
}
