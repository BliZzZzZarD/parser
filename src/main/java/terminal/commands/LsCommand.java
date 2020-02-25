package terminal.commands;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import terminal.Terminal;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class LsCommand implements Command {
    private static final String FILE_NAME_TEMPLATE = "%s\t";

    private Terminal terminal;

    @Override
    public void execute() {
        log.info("execute ls");

        println("Directory ls:");

        Arrays
                .asList(Optional.ofNullable(terminal.getFolder().listFiles()).orElse(new File[] {}))
                .forEach(file -> print(String.format(FILE_NAME_TEMPLATE, file.getName())));
    }
}
