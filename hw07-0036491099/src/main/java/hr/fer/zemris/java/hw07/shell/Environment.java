package hr.fer.zemris.java.hw07.shell;

import java.util.SortedMap;

/**
 * This interface represents environment. Environment is used for communicating between {@link MyShell} and {@link ShellCommand}.
 * It also defines symbols important for {@link MyShell}.
 */
public interface Environment {

    /**
     * This method is used for reading line from environment.
     *
     * @return line
     * @throws ShellIOException If anything goes wrong, exception is thrown
     */
    String readLine() throws ShellIOException;

    /**
     * This method is used for writing text to environment.
     *
     * @param text Text that will be written
     * @throws ShellIOException If anything goes wrong, exception is thrown
     */
    void write(String text) throws ShellIOException;

    /**
     * This method is used for writing text to environment. After writing text it writes new line.
     *
     * @param text Text that will be written
     * @throws ShellIOException If anything goes wrong, exception is thrown
     */
    void writeln(String text) throws ShellIOException;

    /**
     * Getter for map of all commands that are available in environment.
     *
     * @return {@link SortedMap} of commands
     */
    SortedMap<String, ShellCommand> commands();

    /**
     * Getter for multiline symbol of environment.
     *
     * @return Multiline symbol
     */
    Character getMultilineSymbol();

    /**
     * Setter for multiline symbol of environment.
     *
     * @param symbol Multiline symbol
     */
    void setMultilineSymbol(Character symbol);

    /**
     * Getter for prompt symbol of environment.
     *
     * @return Prompt symbol
     */
    Character getPromptSymbol();

    /**
     * Setter for prompt symbol of environment.
     *
     * @param symbol Prompt symbol
     */
    void setPromptSymbol(Character symbol);

    /**
     * Getter for more lines symbol of environment.
     *
     * @return More lines symbol
     */
    Character getMorelinesSymbol();

    /**
     * Setter for more lines symbol of environment.
     *
     * @param symbol More lines symbol
     */
    void setMorelinesSymbol(Character symbol);
}
