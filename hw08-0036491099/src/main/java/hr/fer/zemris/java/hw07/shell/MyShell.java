package hr.fer.zemris.java.hw07.shell;

/**
 * This class is used for representing MyShell. MyShell is used as a simple shell that support following commands:
 * "charsets, cat, ls, tree, copy, mkdir, hexdump, help, symbol,exit". MyShell is used for doing basic operation on
 * files and directories.
 */
public class MyShell {

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Environment environment = new ShellEnvironment();
        ShellStatus status = ShellStatus.CONTINUE;
        do {
            String l = environment.readLine();
            String commandName = ShellUtility.parseName(l);
            String arguments = l.substring(commandName.length()).trim();

            ShellCommand command = environment.commands().get(commandName);

            if (command == null) {
                environment.writeln("Unknown command!");
                environment.write(environment.getPromptSymbol() + " ");
            } else {
                status = command.executeCommand(environment, arguments);
            }
        } while (status != ShellStatus.TERMINATE);
    }

}
