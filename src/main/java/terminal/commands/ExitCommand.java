package terminal.commands;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import terminal.Terminal;

@Slf4j
@AllArgsConstructor
public class ExitCommand implements Command {
    private Terminal terminal;

    @Override
    public void execute() {
        log.info("execute exit");

        terminal.setTerminalLaunched(false);
    }
}
