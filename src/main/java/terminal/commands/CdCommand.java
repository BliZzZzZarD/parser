package terminal.commands;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import terminal.Terminal;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class CdCommand implements Command {
    private Terminal terminal;

    @Override
    public void execute() {
        log.info("execute cd");

        String newDirectory = Try.of(() -> terminal.getSplitLine().get(1)).getOrElse(EMPTY);

        List<String> folderNameList = Arrays
                .stream(Optional.ofNullable(terminal.getFolder().listFiles()).orElse(new File[] {}))
                .map(File::getName)
                .collect(Collectors.toList());

        if (folderNameList.contains(newDirectory)) {
            processExistingDirectoryName(newDirectory);
        } else if (POINT.equals(newDirectory)) {
            processLiftingUp();
        } else if (EMPTY.equals(newDirectory)){
            print("Cannot cd. Specify directory. Try again.");
        } else {
            print("Cannot cd. Unknown directory. Try again.");
        }
    }

    private void processExistingDirectoryName(String newDirectory) {
        Optional<File> newFolderOptional = Arrays
                .stream(Optional.ofNullable(terminal.getFolder().listFiles()).orElse(new File[] {}))
                .filter(file -> newDirectory.equals(file.getName()))
                .findAny();

        if (newFolderOptional.isPresent()) {
            processFilteredDirectory(newFolderOptional.get());
        } else {
            print("Cannot cd. Unknown directory. Try again.");
        }
    }

    private void processFilteredDirectory(File file) {
        if (file.isDirectory()) {
            terminal.setFolder(file);
        } else {
            print("Cannot cd. It's not a directory. Try again.");
        }
    }

    private void processLiftingUp() {
        String folderPath = terminal.getFolder().getAbsolutePath();
        String path = folderPath.substring(0, folderPath.lastIndexOf(SLASH));
        terminal.setFolder(new File(StringUtils.isNoneBlank(path) ? path : SLASH));
    }
}
