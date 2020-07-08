package terminal;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import terminal.commands.*;

import java.io.File;
import java.util.*;

@Slf4j
public class Terminal {
    private static final String SPACE = " ";
    private static final String PATH = "\n%s~$: ";
    private static final String PARSE = "parse";
    private static final String EXIT = "exit";
    private static final String HELP = "help";
    private static final String LS = "ls";
    private static final String CD = "cd";

    private static final Map<String, Command> commands = new HashMap<>();

    private final Command wrongCommand = new WrongCommand();
    private boolean terminalLaunched = false;

    @Setter @Getter
    private File folder = new File(new File("").getAbsolutePath());

    @Getter
    private List<String> splitLine;

    private static volatile Terminal terminal;

    private Scanner scanner;

    private Terminal() {
    }

    public static Terminal getTerminal() {
        Terminal tempTerminal = terminal;
        if (tempTerminal == null) {
            synchronized (Terminal.class) {
                tempTerminal = terminal;
                if (tempTerminal == null) {
                    log.info("initializing terminal");
                    terminal = tempTerminal = new Terminal();
                    initCommands();
                }
            }
        }

        return tempTerminal;
    }

    private static void initCommands() {
        log.info("initializing terminal commands");
        commands.put(EXIT, new ExitCommand(terminal));
        commands.put(HELP, new HelpCommand());
        commands.put(LS, new LsCommand(terminal));
        commands.put(CD, new CdCommand(terminal));
        commands.put(PARSE, new ParseCommand(terminal));
    }

    public void start() {
        log.info("terminal has been started");
        startPreparing();
        startProcessing();
        cleanTerminal();
        log.info("terminal has been terminated");
    }

    private void startPreparing() {
        terminalLaunched = true;
        scanner = new Scanner(System.in);
    }

    private void startProcessing() {
        while (terminalLaunched) {
            printCurrentDirectory();
            readLine();
            execute();
        }
    }

    private void printCurrentDirectory() {
        System.out.print(String.format(PATH, folder.getAbsolutePath()));
    }

    private void readLine() {
        splitLine = Arrays.asList(scanner.nextLine().split(SPACE));
    }

    private void execute() {
        commands.getOrDefault(splitLine.get(0), wrongCommand).execute();
    }

    private void cleanTerminal() {
        scanner.close();
        splitLine = null;
    }

    public void terminate() {
        terminalLaunched = false;
    }
}
