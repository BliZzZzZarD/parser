import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import parser.Parser;
import terminal.Terminal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {
    private static final String PARSE = "parse";
    private static final String EXIT = "exit";
    private static final String HELP = "help";
    private static final String LS = "ls";
    private static final String CD = "cd";

    private final static String NAME1 = "INSTRUMENT1";
    private final static String NAME2 = "INSTRUMENT2";
    private final static String NAME3 = "INSTRUMENT3";

    @Test
    void testGetTerminal() {
        assertEquals(Terminal.getTerminal(), Terminal.getTerminal());
    }

    @ParameterizedTest
    @ValueSource(strings = { EXIT, PARSE, HELP, LS, CD, "Unknown" })
    void testTerminal(String commandName) {
        System.setIn(new ByteArrayInputStream(commandName.getBytes()));

        PrintStream consoleStream = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outputStream);
        System.setOut(stream);

        Terminal terminal = Terminal.getTerminal();

        Try
                .run(terminal::start)
                .andFinally(terminal::terminate);

        System.setOut(consoleStream);

        assertTrue(outputStream.toString().contains(commandName));
    }

    @Test
    void testParser() {
        Optional
                .ofNullable(getClass().getClassLoader().getResource("parse.txt"))
                .ifPresentOrElse(this::parserFile, this::logError);
    }

    private void parserFile(URL url) {
        File file = new File(url.getFile());
        Parser parser = new Parser(file);
        parser.start();

        assertTrue(parser.getResults().get(NAME1).getResult().contains("average value is 33.22"));
        assertTrue(parser.getResults().get(NAME2).getResult().contains("average value in the period with 01-Nov-2014 to 30-Nov-2014 is 32.87"));
        assertTrue(parser.getResults().get(NAME3).getResult().contains("INSTRUMENT3, 06-Nov-2014, 33.48"));
    }

    private void logError() {
        throw new RuntimeException("No file url exist");
    }
}
