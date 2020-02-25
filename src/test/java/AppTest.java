import org.junit.jupiter.api.Test;
import terminal.Terminal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    @Test
    void testGetTerminal() {
        assertEquals(Terminal.getTerminal(), Terminal.getTerminal());
    }
}
