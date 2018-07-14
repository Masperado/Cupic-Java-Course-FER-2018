package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class represents help command. If used with no arguments it lists all available commands.
 * If used with command name as argument, it prints description of given command.
 */
public class HelpCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        Map<String, ShellCommand> commands = env.commands();
        if (arguments.length() == 0) {
            for (String command : commands.keySet()) {
                env.writeln(command);
            }
        } else {
            ShellCommand command = commands.get(arguments);

            if (command == null) {
                env.writeln("Unknown command!");
            } else {
                for (String s : command.getCommandDescription()) {
                    env.writeln(s);
                }
            }
        }

        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();

        description.add("Help command.");
        description.add("If used with no arguments it lists all available commands.");
        description.add("If used with command name as argument, it prints description of given command.");

        return description;
    }
}
