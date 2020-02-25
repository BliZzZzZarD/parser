import org.junit.jupiter.api.Test;
import parser.Parser;
import terminal.Terminal;

import java.io.File;
import java.net.URL;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {
    private final static String NAME1 = "INSTRUMENT1";
    private final static String NAME2 = "INSTRUMENT2";
    private final static String NAME3 = "INSTRUMENT3";

    @Test
    void testGetTerminal() {
        assertEquals(Terminal.getTerminal(), Terminal.getTerminal());
    }

    @Test
    void testParser() {
        Optional<URL> urlOptional = Optional.ofNullable(getClass().getClassLoader().getResource("parse.txt"));

        if (urlOptional.isPresent()) {
            File file = new File(urlOptional.get().getFile());
            Parser parser = new Parser(file);
            parser.start();

            assertTrue(parser.getResults().get(NAME1).getResult().contains("average value is 33.22"));
            assertTrue(parser.getResults().get(NAME2).getResult().contains("average value in the period with 01-Nov-2014 to 30-Nov-2014 is 32.87"));
            assertTrue(parser.getResults().get(NAME3).getResult().contains("[INSTRUMENT3, 06-Nov-2014, 33.48]"));
        } else {
            throw new RuntimeException("No file url exist");
        }
    }
}
