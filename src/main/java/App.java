import lombok.extern.slf4j.Slf4j;
import terminal.Terminal;

@Slf4j
public class App {
    private static final String HEADER = "Welcome to doc-parser v0.0.1. Only unix system. For parsing doc choose file with command \'parse\'. For help input help.";

    public static void main(String[] args) {
        log.info("Application  has been started");
        System.out.println(HEADER);
        Terminal terminal = Terminal.getTerminal();
        terminal.start();
        log.info("Application has been terminated");
    }
}
