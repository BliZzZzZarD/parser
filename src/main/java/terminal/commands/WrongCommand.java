package terminal.commands;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class WrongCommand implements Command {
    @Override
    public void execute() {
        log.info("execute wrong");

        print("Unknown command. Try again.");
    }
}
