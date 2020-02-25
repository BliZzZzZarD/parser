package terminal.commands;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import parser.Parser;
import terminal.Terminal;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class ParseCommand implements Command {
    private Terminal terminal;

    @Override
    public void execute() {
        log.info("execute parse");

        File folder = terminal.getFolder();
        String parseFileName = Try.of(() -> terminal.getSplitLine().get(1)).getOrElse(EMPTY);

        List<String> folderNameList = Arrays
                .stream(Optional.ofNullable(folder.listFiles()).orElse(new File[] {}))
                .map(File::getName)
                .collect(Collectors.toList());

        if (folderNameList.contains(parseFileName)) {
            processExistingFileName(folder, parseFileName);
        } else if (EMPTY.equals(parseFileName)){
            print("Specify file. Try again.");
        } else {
            print("Unknown file. Try again.");
        }
    }

    private void processExistingFileName(File folder, String parseFileName) {
        Optional<File> fileParseOptional = Arrays
                .stream(Optional.ofNullable(folder.listFiles()).orElse(new File[] {}))
                .filter(file -> parseFileName.equals(file.getName()))
                .findAny();

        if (fileParseOptional.isPresent()) {
            processFilteredFile(fileParseOptional.get());
        } else {
            print("Unknown directory. Try again.");
        }
    }

    private void processFilteredFile(File file) {
        if (file.isFile()) {
            processParsing(file);
        } else {
            print("It's not a file. Try again.");
        }
    }

    private void processParsing(File file) {
        if (isFormatChecked(file)) {
            print("Only *.txt file parse.Try again.");
        } else {
            Parser parser = new Parser(file);
            parser.start();
        }
    }

    private boolean isFormatChecked(File file) {
        return file.getName().endsWith(".txt");
    }
}
