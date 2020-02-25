package terminal.commands;

public interface Command {
    String POINT = ".";
    String EMPTY = "";
    String SLASH = "/";

    void execute();

    default void print(String text) {
        System.out.print(text);
    }
}
