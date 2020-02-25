package terminal.commands;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class HelpCommand implements Command {
    @Override
    public void execute() {
        log.info("execute help");

        print("Commands:\n" +
                "help-show commands\n" +
                "parse %filename%-parse file\n" +
                "ls-show file list\n" +
                "cd-open directory(for lifting up use \'.\')");
    }
}
