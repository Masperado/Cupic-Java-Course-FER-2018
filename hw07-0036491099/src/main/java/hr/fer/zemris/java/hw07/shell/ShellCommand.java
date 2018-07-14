package hr.fer.zemris.java.hw07.shell;

import java.util.List;

/**
 * This interface represents ShellCommand. ShellCommand is used for representing commands in {@link MyShell}.
 */
public interface ShellCommand {

    /**
     * This method is used for executing command in given environment with given arguments.
     *
     * @param env       Environment
     * @param arguments Command arguments
     * @return {@link ShellStatus} as a result of command
     */
    ShellStatus executeCommand(Environment env, String arguments);

    /**
     * This method is used for getting command name.
     *
     * @return Command name
     */
    String getCommandName();

    /**
     * This method is used for getting command description.
     *
     * @return Command description
     */
    List<String> getCommandDescription();

}
